package com.lynjava.ddd.common.model;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 默认的ServletInputStream不支持reset，所以默认的流只能读取一次，但是如果我们要打印body就需要读取inputStream，
 * 这会导致Spring无法读取body体，所以通过HttpServletRequestWrapper包装重新定义getInputStream()，
 * 让输入流从我们读出来的body体中读取数据。
 */
public class MultiReadServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] bytes;
    public MultiReadServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 读取出来保存到body里
        try (InputStream in = new BufferedInputStream(request.getInputStream())) {
//            this.body = StreamUtils.copyToString(in, Charset.defaultCharset());
            this.bytes = StreamUtils.copyToByteArray(in);
        }
    }

//    private final String body;
//    public String getBody() {
//        return this.body;
//    }

    /**
     * 重写getInputStream方法，从body里读取
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
//        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) { }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
