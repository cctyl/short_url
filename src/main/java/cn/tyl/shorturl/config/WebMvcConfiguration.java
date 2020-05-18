package cn.tyl.shorturl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 对springmvc进行一些增强配置
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 添加一个自定义的视图映射
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {


        registry.addViewController("/").setViewName("index");
    }
}
