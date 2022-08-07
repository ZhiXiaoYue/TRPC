package com.jill.rpc.rpc;

/**
 * 消费者调用服务
 * service invoke ites method.
 *
 */
public interface Invoker {
    // 返回接口
    Class getInterface();
    /**
     * 发起调用【负载均衡，容错，重连】
     *
     */
    Object invoke(RpcInvocation  rpcInvocation) throws Exception;

}
