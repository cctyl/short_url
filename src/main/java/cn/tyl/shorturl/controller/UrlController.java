package cn.tyl.shorturl.controller;

import cn.tyl.shorturl.bean.Msg;
import cn.tyl.shorturl.util.RedisUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UrlController {

    @Autowired
    RedisUtils redisUtils;

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 生成短连接
     *
     * @param url
     * @param remenber
     * @param request
     * @return
     */
    @PostMapping("/url")
    @ResponseBody
    public Msg getUrl(@RequestParam(value = "url", required = true) String url,
                      @RequestParam(value = "remenber", defaultValue = "false", required = false) boolean remenber,
                      HttpServletRequest request) {

        //为长连接生成一个名字
        String urlName = RandomStringUtils.randomAlphanumeric(5);
        //拿到本机的域名
        String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/u/";

        //生成短连接
        String shortUrl = basePath + urlName;

        //以 urlName作为键，长连接作为值存入
        redisUtils.set(urlName, url);

        //如果勾选了需要密码，那么就生成一个密码
        String accessPwd = "";
        if (remenber) {

            accessPwd = RandomStringUtils.randomAlphanumeric(4);
            //用urlName+pwd 作为键，值就是密码
            redisUtils.set(urlName + "pwd", accessPwd);

        }

        return Msg.success().add("shortUrl", shortUrl).add("accessPwd", accessPwd);
    }


    /**
     * 访问短连接
     *
     * @param urlName
     * @return
     */
    @RequestMapping(value = "/u/{urlName}")
    public String toPage(@PathVariable("urlName") String urlName, Model model) {
        String longUrl = redisUtils.get(urlName);
        if (longUrl == null) {
            //没有这个连接，那么就直接提示
            return "no-this-page";
        }

        String accessPwd = redisUtils.get(urlName + "pwd");
        //判断是否有密码存在
        if (accessPwd != null) {
            //有密码，输入密码才能访问
            model.addAttribute("urlName", urlName);
            return "accessCtrl";
        }

        //请求重定向即可。因为既然你知道了密码，那自然也知道了长连接，别人分享时多半也会把密码分享出去
        return "redirect:" + longUrl;

    }

    @RequestMapping("/verify")
    @ResponseBody
    public Msg verifyPwd(@RequestParam("accessPwd") String accessPwd,@RequestParam("urlName")String urlName){

        //取出密码
        String pwd = redisUtils.get(urlName + "pwd");

        if (accessPwd.trim().equals(pwd)){
            //相等就返回长连接
            String longUrl = redisUtils.get(urlName);
            return Msg.success().add("longUrl",longUrl);
        }


        return Msg.fail();
    }


}
