package org.sxiong.yarpc.common.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by sxiong on 7/27/17.
 */
public class SerializerJavaImpl extends SerializerBase{
    private static final SerializerJavaImpl instance = null;
    private SerializerJavaImpl(SerializeType type) {
        super(type);
    }

    public static final SerializerJavaImpl getInstance(){
        if (instance!=null){
            synchronized (SerializerJavaImpl.class){
                if (instance!=null){
                    return instance;
                }
            }
        }
        return new SerializerJavaImpl(SerializeType.JAVA);
    }

    @Override
    protected byte[] serializeRealObject(Object object) throws IOException {
        if (object == null){
            throw new NullPointerException();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
        out.writeObject(object);
        out.close();
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    protected Object deserializeRealBytes(byte[] data) throws IOException {
        return null;
    }
}
