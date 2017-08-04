package org.sxiong.yarpc.common.serialize;

import static org.sxiong.yarpc.common.serialize.SerializeType.HESSIAN;
import static org.sxiong.yarpc.common.serialize.SerializeType.JAVA;

/**
 * Created by sxiong on 7/27/17.
 */
public class SerialzerFactory {
    public static SerializeType type = null;

    private static SerializeType decodeType(byte[] bytes){
        int value = Integer.valueOf(new String(bytes));
        if (value==HESSIAN.getValue()){
            return HESSIAN;
        }else if (value==JAVA.getValue()){
            return JAVA;
        }else{
            throw new IllegalArgumentException("serialize type value decoded from byte is error");
        }
    }

    public static Serializer getSerializer(byte[] bytes){
        SerializeType type = decodeType(new byte[]{bytes[0]});
        return getSerializer(type);
    }

    public static Serializer getSerializer(SerializeType type){
        switch (type){
            case HESSIAN:
                return SerializerHessianImpl.getInstance();
            case JAVA:
                return SerializerJavaImpl.getInstance();
            default:
                throw new IllegalArgumentException("serialize type value decoded from byte is error");
        }
    }
}
