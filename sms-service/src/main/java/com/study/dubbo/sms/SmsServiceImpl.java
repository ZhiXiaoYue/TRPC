package com.study.dubbo.sms;

import com.jill.rpc.config.annotation.TRpcService;
import com.study.dubbo.sms.api.SmsService;

import java.util.UUID;


@TRpcService
public class SmsServiceImpl implements SmsService{
    public Object send(String phone, String content) {
        System.out.println("发送短信：" + phone + ":" + content);
        return "短信发送成功" + UUID.randomUUID().toString();
    }
}
