package com.jill.rpc.remoting.netty;

import com.jill.rpc.remoting.Codec;
import com.jill.rpc.remoting.Handler;
import com.jill.rpc.remoting.Server;
import com.jill.rpc.remoting.Transporter;
import com.sun.tools.javac.jvm.Code;

import java.net.URI;

public class Netty4Transporter implements Transporter {
    @Override
    public Server start(URI uri, Codec codec, Handler handler) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(uri, codec, handler);
        return nettyServer;
    }
}
