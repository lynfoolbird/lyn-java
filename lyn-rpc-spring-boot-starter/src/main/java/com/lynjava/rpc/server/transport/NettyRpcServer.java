package com.lynjava.rpc.server.transport;

import com.lynjava.rpc.core.codec.RpcDecoder;
import com.lynjava.rpc.core.codec.RpcEncoder;
import com.lynjava.rpc.server.transport.handler.RpcRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * NettyRpcServer
 */
@Slf4j
public class NettyRpcServer implements RpcServer {
    private final int port;

    public NettyRpcServer(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            String serverAddress = InetAddress.getLocalHost().getHostAddress();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    // 协议编码
                                    .addLast(new RpcEncoder())
                                    // 协议解码
                                    .addLast(new RpcDecoder())
                                    // 请求处理器
                                    .addLast(new RpcRequestHandler());
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind(serverAddress, this.port).sync();
            log.info("server addr {} started on port {}", serverAddress, this.port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("NettyRpcServer start exception:", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
