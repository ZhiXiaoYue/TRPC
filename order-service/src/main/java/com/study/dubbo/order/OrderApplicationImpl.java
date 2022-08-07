package com.study.dubbo.order;

import org.springframework.stereotype.Service;
import com.study.dubbo.order.api.OrderService;
import com.study.dubbo.sms.api.SmsService;

import java.util.UUID;

@Service
public class OrderApplicationImpl implements OrderService {
    SmsService smsService;
    public void create(String orderContent) {
        System.out.println("订单创建成功：" + orderContent);
        Object smsResult = smsService.send("10086" + UUID.randomUUID().toString(), "订单创建成功");
        System.out.println("smsService调用结果：" + smsResult);
    }

}
