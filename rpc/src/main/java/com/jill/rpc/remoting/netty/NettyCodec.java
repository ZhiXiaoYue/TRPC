package com.jill.rpc.remoting.netty;

import com.jill.rpc.remoting.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.List;

// 进行编解码【定义，不做具体的协议 】
// 接收端：【把请求中的网络数据转成对象】
// 发送端：在发起请求的时候把对象转换为字节
public class NettyCodec extends ChannelDuplexHandler {
    private Codec codec;
    NettyCodec(Codec codec){
        this.codec = codec;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws  Exception{
        //解码
        // 1. 读取数据
        ByteBuf data = (ByteBuf) msg;
        byte[] dataBytes = new byte[data.readableBytes()];
        data.readBytes(dataBytes);
        // 格式转
        List<Object> out = codec.decode(dataBytes);
        // 交给下一个处理器继续处理
        for (Object o : out) {
            ctx.fireChannelRead(o);
        }
        //System.out.println("内容"+msg);
    }

    // 出栈

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        byte[] encode = codec.encode(msg);
        super.write(ctx,Unpooled.wrappedBuffer(encode), promise);
    }
}
