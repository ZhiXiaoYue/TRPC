package com.jill.rpc.common.serialize;

public interface Serialization {
    byte[] serialize(Object output) throws Exception;

    Object deserialize(byte[] input, Class clazz) throws Exception;
}
