package cn.tyl.shorturl.bean;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 通用的返回类
 */
public class Msg {

    //状态码 100成功 200失败
    private  Integer code;

    //提示消息
    private String msg;

    //封装的数据，我也不知道为什么放在Map中，但据说能保持统一性
    private Map<String,Object>  extend = new HashMap<>();

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 通用的返回成功信息的方法
     *
     */
    public static  Msg success(){
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("请求成功");
      /*  result.getExtend().put(key, value);*/
        return result;
    }

    /**
     * 通用的返回失败信息的方法
     *
     */
    public static  Msg fail(){
        Msg result = new Msg();
        result.setCode(100);
        result.setMsg("请求失败");
        return result;
    }

    /**
     * 封装数据的方法
     */
    public Msg add(String key,Object value){

        this.getExtend().put(key,value);
        return this;

    }
}
