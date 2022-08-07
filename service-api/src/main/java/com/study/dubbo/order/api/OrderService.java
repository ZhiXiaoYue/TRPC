package com.study.dubbo.order.api;

/**
 * 订单
 */
public interface OrderService {
    /**
     * 创建订单
     */
    void create(String orderContent);

}
