package com.study.dubbo;



import com.jill.rpc.config.spring.annotation.EnableTRPC;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.testng.annotations.ITestOrConfiguration;

@Configuration
@ComponentScan("com.study.dubbo")
@EnableTRPC
public class OrderApplication {
    public static void main(String[] args) throws Exception{

    }
}
