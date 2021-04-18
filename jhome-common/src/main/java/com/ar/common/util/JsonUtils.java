package com.ar.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() { }

    public static <T> String parse(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            final String errorMsg = "JsonUtils#将对象转化为JSON字符串时抛出异常: {}";
            LOGGER.error(errorMsg, e.getMessage());
            throw new RuntimeException(errorMsg, e);
        }
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

}
