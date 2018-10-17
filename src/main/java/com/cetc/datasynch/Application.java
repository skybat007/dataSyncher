package com.cetc.datasynch;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashMap;


@SpringBootApplication
@ComponentScan
@Component
public class Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(Application.class);
    }

    public static void main(String[] args)
    {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {}
}


/**
 * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 */
//public class Application extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        // 注意这里要指向原先用main方法执行的Application启动类
//        return builder.sources(Application.class);
//    }
//}

