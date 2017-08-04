package org.sxiong.yarpc.common.serialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by sxiong on 7/27/17.
 */
public abstract class SerializerBase implements Serializer{
    SerializeType type = null;
    public SerializerBase(SerializeType type){
        this.type = type;
    }

    private byte[] encodeType(SerializeType serializeType){
        byte[] bytes = new byte[1];
        bytes = serializeType.toString().getBytes();
        return bytes;
    }

    private static byte[] byteMerge(byte[] byte_1, byte[] byte_2){
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    protected abstract byte[] serializeRealObject(Object object) throws IOException;

    protected abstract Object deserializeRealBytes(byte[] data) throws IOException;

    @Override
    public byte[] serialize(Object object) throws IOException {
        return byteMerge(encodeType(type),this.serializeRealObject(object));
    }

    @Override
    public Object deserialize(byte[] data) throws IOException {
        byte[] realData = new byte[data.length-1];
        System.arraycopy(data,1,realData,0,realData.length);

        return this.deserializeRealBytes(realData);
    }
}
