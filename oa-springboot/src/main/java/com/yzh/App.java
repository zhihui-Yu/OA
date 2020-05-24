package com.yzh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration
@MapperScan(basePackages = "com.yzh.oa.dao.mappers")
@ComponentScan("com.yzh.oa")
@EnableAspectJAutoProxy
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
        System.out.println("----- spring  start ------");
    }
}
