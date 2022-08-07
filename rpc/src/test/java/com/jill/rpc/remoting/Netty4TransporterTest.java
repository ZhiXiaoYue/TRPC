package com.jill.rpc.remoting;


import com.jill.rpc.remoting.netty.Netty4Transporter;
import com.jill.rpc.remoting.Codec;
import com.jill.rpc.remoting.Handler;
import com.jill.rpc.remoting.TrpcChannel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Netty4TransporterTest {
    public  static void  main(String[] args) throws URISyntaxException {
        new Netty4Transporter().start(new URI("TRPC://127.0.0.1:8080"),
                new Codec() {

                    @Override
                    public byte[] encode(Object msg) throws Exception {
                        return new byte[0];
                    }

                    @Override
                    public List<Object> decode(byte[] message) throws Exception {
                        List<Object> obs = new ArrayList<Object>();
                        System.out.println("打印请求的内容：" + new String(message));
                        obs.add("1" + new String(message));
                        obs.add("2" + new String(message));
                        obs.add("3" + new String(message));
                        return obs;
                    }

                    @Override
                    public Codec createInstance() {
                        return null;
                    }
                },
                new Handler() {
                    @Override
                    public void onWrite(TrpcChannel trpcChannel, Object message) throws Exception {

                    }

                    @Override
                    public void onReceive(TrpcChannel trpcChannel, Object message) throws Exception {
                        System.out.println(message);
                    }
                }
        );
    }
}
