package com.cetc.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


//@EnableDiscoveryClient
//@EnableFeignClients
//@EnableCircuitBreaker
//@EnableHystrix
@EnableScheduling
@SpringBootApplication
public class DataSyncherApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(DataSyncherApplication.class);
    }

    public static void main(String[] args)
    {
        new SpringApplicationBuilder(DataSyncherApplication.class).web(true).run(args);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {}
}


