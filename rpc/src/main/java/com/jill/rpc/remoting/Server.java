package com.jill.rpc.remoting;

import java.net.URI;

public interface Server {
    void start(URI uri, Codec codec, Handler handler);
}
