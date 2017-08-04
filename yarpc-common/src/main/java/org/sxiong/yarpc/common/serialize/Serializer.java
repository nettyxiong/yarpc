package org.sxiong.yarpc.common.serialize;

import java.io.IOException;

/**
 * Created by sxiong on 7/27/17.
 */
public interface Serializer {
    public  byte[] serialize(Object object) throws IOException;

    public Object deserialize(byte[] data) throws IOException;
}
