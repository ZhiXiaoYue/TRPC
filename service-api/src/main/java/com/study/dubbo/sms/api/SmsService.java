package com.study.dubbo.sms.api;

public interface SmsService {
    Object send(String phone, String content);
}
