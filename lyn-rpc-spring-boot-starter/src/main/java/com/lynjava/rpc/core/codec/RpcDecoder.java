package com.lynjava.rpc.core.codec;

import com.lynjava.rpc.core.consts.MsgTypeEnum;
import com.lynjava.rpc.core.consts.RpcConstants;
import com.lynjava.rpc.core.consts.SerializerTypeEnum;
import com.lynjava.rpc.core.model.RpcRequest;
import com.lynjava.rpc.core.model.RpcResponse;
import com.lynjava.rpc.core.protocol.MessageHeader;
import com.lynjava.rpc.core.protocol.MessageProtocol;
import com.lynjava.rpc.core.serializer.ISerializer;
import com.lynjava.rpc.core.serializer.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.List;

/**
 *  解码器
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {

    /**
     *
     *  +---------------------------------------------------------------+
     *  | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte|
     *  +---------------------------------------------------------------+
     *  | 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
     *  +---------------------------------------------------------------+
     *  |                   数据内容 （长度不定）                         |
     *  +---------------------------------------------------------------+
     *
     *  decode 这个方法会被循环调用
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < RpcConstants.PROTOCOL.HEADER_TOTAL_LEN) {
            // 可读的数据小于请求头总的大小 直接丢弃
            return;
        }
        // 标记 ByteBuf 读指针位置
        in.markReaderIndex();

        // 魔数
        short magic = in.readShort();
        if (magic != RpcConstants.PROTOCOL.MAGIC) {
            throw new IllegalArgumentException("magic number is illegal, " + magic);
        }

        byte version = in.readByte();
        byte serializeType = in.readByte();
        byte msgType = in.readByte();
        byte status = in.readByte();
        CharSequence requestId = in.readCharSequence(RpcConstants.PROTOCOL.REQ_LEN, Charset.forName("UTF-8"));

        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            // 可读的数据长度小于 请求体长度 直接丢弃并重置 读指针位置
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        MsgTypeEnum msgTypeEnum = MsgTypeEnum.findByType(msgType);
        if (msgTypeEnum == null) {
            return;
        }

        MessageHeader header =  MessageHeader.builder()
                .magic(magic)
                .version(version)
                .serialization(serializeType)
                .status(status)
                .requestId(String.valueOf(requestId))
                .msgType(msgType)
                .msgLen(dataLength)
                .build();
        ISerializer rpcSerialization = SerializerFactory
                .getSerializer(SerializerTypeEnum.parseByType(serializeType));
        switch (msgTypeEnum) {
            case REQUEST:
                RpcRequest request = rpcSerialization.deserialize(data, RpcRequest.class);
                if (request != null) {
                    MessageProtocol<RpcRequest> protocol = new MessageProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(request);
                    out.add(protocol);
                }
                break;
            case RESPONSE:
                RpcResponse response = rpcSerialization.deserialize(data, RpcResponse.class);
                if (response != null) {
                    MessageProtocol<RpcResponse> protocol = new MessageProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(response);
                    out.add(protocol);
                }
                break;
            default:
                break;
        }
    }
}
