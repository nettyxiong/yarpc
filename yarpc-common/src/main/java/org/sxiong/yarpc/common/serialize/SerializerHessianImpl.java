package org.sxiong.yarpc.common.serialize;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Created by sxiong on 7/27/17.
 */
public class SerializerHessianImpl extends SerializerBase{
    private static final Logger logger = LoggerFactory.getLogger(SerializerHessianImpl.class);

    private static final SerializerHessianImpl instance = null;
    private SerializerHessianImpl(SerializeType type) {
        super(type);
    }

    public static final SerializerHessianImpl getInstance(){
        if (instance!=null){
            synchronized (SerializerHessianImpl.class){
                if (instance!=null){
                    return instance;
                }
            }
        }
        return new SerializerHessianImpl(SerializeType.HESSIAN);
    }

    @Override
    protected byte[] serializeRealObject(Object object) throws IOException {
        logger.info("started serialize Object by Hessian transport");
        if (object == null){
            throw new NullPointerException();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(byteArrayOutputStream);
        out.writeObject(object);
        out.close();
        logger.info("serialize Object by Hessian transport finished");
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    protected Object deserializeRealBytes(byte[] data) throws IOException {
        logger.info("started deserialize Object by Hessian transport");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        Hessian2Input in = new Hessian2Input(byteArrayInputStream);
        logger.info("deserialize Object by Hessian transport finished");
        return in.readObject();
    }
}
