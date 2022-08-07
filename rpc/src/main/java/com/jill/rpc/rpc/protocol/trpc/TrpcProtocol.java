package com.jill.rpc.rpc.protocol.trpc;

import com.jill.rpc.common.serialize.Serialization;
import com.jill.rpc.common.serialize.json.JsonSerialization;
import com.jill.rpc.common.tools.SpiUtils;
import com.jill.rpc.common.tools.URIUtils;
import com.jill.rpc.remoting.Transporter;
import com.jill.rpc.rpc.Invoker;
import com.jill.rpc.rpc.RpcInvocation;
import com.jill.rpc.rpc.protocol.Protocol;
import com.jill.rpc.rpc.protocol.trpc.codec.TrpcCodec;
import com.jill.rpc.rpc.protocol.trpc.handler.TrpcServerHandler;
import sun.rmi.transport.Transport;
import java.net.URI;

public class TrpcProtocol implements Protocol {

    @Override
    public void export(URI exportUri, Invoker invoker) {
// 找到序列化
        String serializationName = URIUtils.getParam(exportUri, "serialization");
        Serialization serialization = (Serialization) SpiUtils.getServiceImpl(serializationName, Serialization.class);


// 编解码器
        TrpcCodec trpcCodec = new TrpcCodec();
        trpcCodec.setDecodeType(RpcInvocation.class);
        trpcCodec.setSerialization(serialization);

// 处理器
        TrpcServerHandler trpcServerHandler = new TrpcServerHandler();
        trpcServerHandler.setInvoker(invoker);
        trpcServerHandler.setSerialization(serialization) ;
//底层网络
        String transportName =URIUtils.getParam(exportUri, "transporter");
        Transporter transport = (Transporter) SpiUtils.getServiceImpl(transportName, Transporter.class);
//启动服务
        transport.start(exportUri, trpcCodec, trpcServerHandler);
    }

    @Override
    public Invoker refer(URI consumerUri) {
        return null;
    }
}
