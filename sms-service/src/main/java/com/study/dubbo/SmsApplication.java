package com.study.dubbo;

import com.jill.rpc.config.spring.annotation.EnableTRPC;
import com.study.dubbo.sms.SmsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@Configuration
@ComponentScan("com.study.dubbo")
@PropertySource("classpath:/trpc.properties")
@EnableTRPC
public class SmsApplication {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SmsApplication.class);
        context.start();
        SmsServiceImpl smsService = context.getBean(SmsServiceImpl.class);
        System.out.println(smsService.send("10086","启动时测试一条短信"));

        // 阻塞不退出
        System.in.read();
        context.close();
    }
}
