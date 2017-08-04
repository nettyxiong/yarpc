package org.sxiong.yarpc.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sxiong.yarpc.common.serialize.SerializeType;
import org.sxiong.yarpc.common.serialize.SerializerHessianImpl;
import org.sxiong.yarpc.common.serialize.SerializerOld;
import org.sxiong.yarpc.common.serialize.SerialzerFactory;

/**
 * Created by sxiong on 7/27/17.
 */
public class NettyEncoder extends MessageToByteEncoder{
    private static final Logger logger = LoggerFactory.getLogger(NettyEncoder.class);
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf out) throws Exception {
        logger.info("encoding start..."+msg);
//        byte[] bytes = SerialzerFactory.getSerializer(SerializeType.HESSIAN).serialize(msg);
        byte[] bytes = SerializerOld.serialize(msg);
        logger.info("--------------" + bytes.length + "----------------");

        out.writeInt(bytes.length);
        out.writeBytes(bytes);

        logger.info("msg encode length:"+bytes.length);
    }
}
