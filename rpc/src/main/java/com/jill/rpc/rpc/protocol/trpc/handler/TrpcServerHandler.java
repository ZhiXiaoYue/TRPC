package com.jill.rpc.rpc.protocol.trpc.handler;

import com.jill.rpc.common.serialize.Serialization;
import com.jill.rpc.remoting.Handler;
import com.jill.rpc.remoting.TrpcChannel;
import com.jill.rpc.rpc.Invoker;
import com.jill.rpc.rpc.Response;
import com.jill.rpc.rpc.RpcInvocation;

public class TrpcServerHandler implements Handler {

    Invoker invoker;
    @Override
    public void onReceive(TrpcChannel trpcChannel, Object message) throws Exception {
        RpcInvocation rpcInvocation = (RpcInvocation) message;
        System.out.println("收到RPC信息" + rpcInvocation);
        Response response = new Response();
        try{

            Object result = getInvoker().invoke(rpcInvocation);
            System.out.println("end：" + result);
            response.setStatus(200);
            response.setContent(result);
        }catch (Throwable e){
            response.setStatus(99);
            response.setContent(e.getMessage());
        }
        // 编码response
//        byte[] responseBody = getSerialization().serialize(response);
//        trpcChannel.send(responseBody); // write方法
    }

    @Override
    public void onWrite(TrpcChannel trpcChannel, Object message) throws Exception {

    }

    public Invoker getInvoker() {
        return this.invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    Serialization serialization;

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }

    public Serialization getSerialization() {
        return this.serialization;
    }

}
