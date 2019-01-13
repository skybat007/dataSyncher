package com.cetc.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


//@EnableDiscoveryClient
//@EnableFeignClients
//@EnableCircuitBreaker
//@EnableHystrix
@EnableScheduling
@SpringBootApplication
@ComponentScan
@Component
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


