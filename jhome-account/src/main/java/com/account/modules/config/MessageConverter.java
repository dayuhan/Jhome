package com.account.modules.config;

import org.apache.shiro.session.mgt.SimpleSession;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class MessageConverter extends AbstractHttpMessageConverter<SimpleSession> {


    public MessageConverter(){
        super(new MediaType("application", "test-converter", Charset.forName("UTF-8")));
    }
    @Override
    protected boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    protected SimpleSession readInternal(Class<? extends SimpleSession> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        String temp = StreamUtils.copyToString(httpInputMessage.getBody(), Charset.forName("UTF-8"));
        SimpleSession session = new SimpleSession();
        return session;
    }

    @Override
    protected void writeInternal(SimpleSession simpleSession, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        httpOutputMessage.getBody().write(simpleSession.toString().getBytes());
    }
}
