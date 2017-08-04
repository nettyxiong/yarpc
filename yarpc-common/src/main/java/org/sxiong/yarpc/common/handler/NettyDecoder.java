package org.sxiong.yarpc.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.common.serialize.SerializeType;
import org.sxiong.yarpc.common.serialize.SerializerOld;
import org.sxiong.yarpc.common.serialize.SerialzerFactory;

import java.util.List;


/**
 * Created by sxiong on 7/27/17.
 */
public class NettyDecoder extends ByteToMessageDecoder{
    private static final Logger logger = LoggerFactory.getLogger(NettyDecoder.class);

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        logger.info("try decode:"+in.toString() +"length is" + in.readableBytes());

        if (in.readableBytes() < 4){
            logger.info("no enough readble bytes");
            return;
        }

        int dataLength = in.readInt();
        if (dataLength < 0){
            channelHandlerContext.close();
        }

        logger.info("try decode data length: " + dataLength);

        if (in.readableBytes() < dataLength){
            in.resetReaderIndex();
        }

        logger.info("try decode doDecode");

        byte[] data = new byte[dataLength];
        in.readBytes(data);

//        Object deserializedObject = SerialzerFactory.getSerializer(SerializeType.HESSIAN).deserialize(data);
        Object deserializedObject = SerializerOld.deserialize(data);
        out.add(deserializedObject);
    }
}
