package com.jill.rpc.remoting;

import java.net.URI;

//控制server的统一入口
public interface Transporter {
    Server start(URI uri, Codec codec, Handler handler);
}
