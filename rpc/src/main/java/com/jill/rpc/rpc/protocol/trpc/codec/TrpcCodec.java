package com.jill.rpc.rpc.protocol.trpc.codec;

import com.jill.rpc.common.serialize.Serialization;
import com.jill.rpc.common.tools.ByteUtil;
import com.jill.rpc.remoting.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

public class TrpcCodec implements Codec {
    public final static byte[] MAGIC = new byte[]{(byte) 0xda, (byte) 0xbb};

    /**
     * 协议头部长度
     */
    public final static int HEADER_LEN = 6;

    private Serialization serialization;

    // 用来临时保留没有处理过的请求报文
    ByteBuf tempMsg = Unpooled.buffer();

    /**
     * 服务端：
     * @param msg
     * @return
     * @throws Exception
     */

    @Override
    public byte[] encode(Object msg) throws Exception {
        byte[] sponseBody = (byte[]) msg;
        ByteBuf requestBuffer = Unpooled.buffer();
        requestBuffer.writeByte(0xda);
        requestBuffer.writeByte(0xbb);
        requestBuffer.writeBytes(ByteUtil.int2bytes(sponseBody.length));
        requestBuffer.writeBytes(sponseBody);

        byte[] result = new byte[requestBuffer.readableBytes()];
        requestBuffer.readBytes(result);
        return result;
    }

    @Override
    public List<Object> decode(byte[] data) throws Exception {
        List<Object> out = new ArrayList<>();
        ByteBuf message = Unpooled.buffer();
        int tmpMsgSize = tempMsg.readableBytes();
        if(tmpMsgSize > 0){
            message.writeBytes(tempMsg);
            message.writeBytes(data);
            System.out.println("合并：上一数据包余下的长度为：" + tmpMsgSize + ",合并后长度为:" + message.readableBytes());
        }else{
            message.writeBytes(data);
        }
        for(;;){
            if (HEADER_LEN >= message.readableBytes()) {
                tempMsg.clear();
                tempMsg.writeBytes(message);
                return out;
            }
            // 1.2 解析数据
            // 1.2.1 检查关键字
            byte[] magic = new byte[2];
            message.readBytes(magic);
            for (; ; ) {
                // 如果不符合关键字，则一直读取到有正常的关键字为止。
                if (magic[0] != MAGIC[0] || magic[1] != MAGIC[1]) {
                    if (message.readableBytes() == 0) {
                        // 所有数据读完都没发现正确的头，算了.. 等下次数据
                        tempMsg.clear();
                        tempMsg.writeByte(magic[1]);
                        return out;
                    }
                    magic[0] = magic[1];
                    magic[1] = message.readByte();
                } else {
                    break;
                }
            }
            byte[] lengthBytes = new byte[4];
            message.readBytes(lengthBytes);
            int length = ByteUtil.Bytes2Int_BE(lengthBytes);
            // 1.2.2 读取body
            // 如果body没传输完，先不处理
            if (message.readableBytes() < length) {
                tempMsg.clear();
                tempMsg.writeBytes(magic);
                tempMsg.writeBytes(lengthBytes);
                tempMsg.writeBytes(message);
                return out;
            }
            byte[] body = new byte[length];
            message.readBytes(body);
            // 序列化
            Object o = getSerialization().deserialize(body, decodeType);
            out.add(o);
        }
    }

    private Serialization getSerialization() {
        return this.serialization;
    }

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }
    @Override
    public Codec createInstance()
    {
        return null;
    }

    Class decodeType;

    public Class getDecodeType() {
        return decodeType;
    }

    public void setDecodeType(Class decodeType) {
        this.decodeType = decodeType;
    }
}
