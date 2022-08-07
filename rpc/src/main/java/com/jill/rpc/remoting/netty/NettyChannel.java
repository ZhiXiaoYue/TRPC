package com.jill.rpc.remoting.netty;

import com.jill.rpc.remoting.TrpcChannel;

import io.netty.channel.Channel;

public class NettyChannel implements TrpcChannel {

    Channel channel;

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(byte[] message) {
        channel.writeAndFlush(message);
    }
}
