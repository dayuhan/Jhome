package com.account.common.sbUtil;

import com.account.common.sbUtil.model.response.error.ErrorEntity;
import com.ar.common.exception.IllegalValidateException;
import com.ar.common.exception.RestStatusException;
import com.ar.common.rest.BasicRestStatusEnum;
import com.ar.common.rest.RestStatus;
import com.ar.common.util.JsonUtils;
import com.beust.jcommander.internal.Maps;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;

public final class Shift {
    private static final Logger LOGGER = LoggerFactory.getLogger(Shift.class);

    private Shift() {
    }

    public static void bindErrors(@Nonnull BindingResult result) throws IllegalValidateException {
        Preconditions.checkNotNull(result);
        boolean flag = true;
        HashMap<Object, Object> errorMap = (HashMap<Object, Object>) Maps.newHashMap();
        if (result.getErrorCount() > 0) {
            flag = false;
            Iterator var4 = result.getFieldErrors().iterator();
            while(var4.hasNext()) {
                FieldError fieldError = (FieldError)var4.next();
                String errorFieldName = decideFieldName(result, fieldError);
                String errorMessage = fieldError.getDefaultMessage();
                errorMap.put(errorFieldName, errorMessage);
            }
        }
        if (!flag) {
            BasicRestStatusEnum invalidFieldsEnum = BasicRestStatusEnum.INVALID_MODEL_FIELDS;
            ErrorEntity entity = new ErrorEntity(invalidFieldsEnum, JsonUtils.parse(errorMap));
            String errorName = invalidFieldsEnum.name();
            bindStatusCodesInRequest(entity, errorName);
            throw new IllegalValidateException(errorName);
        }
    }

    public static void fatal(@Nonnull RestStatus status) {
        Preconditions.checkNotNull(status);
        fatalInject(status, null);
    }
    public static void fatal(@Nonnull RestStatus status, @Nonnull String details) {
        Preconditions.checkNotNull(status);
        Preconditions.checkNotNull(details);
        fatalInject(status, details);
    }

    /**
     * 去掉status自带的提示
     * @param status
     * @param details
     */
    public static void fatalOnlyDetail(@Nonnull RestStatus status, @Nonnull String details) {
        Preconditions.checkNotNull(status);
        Preconditions.checkNotNull(details);
        fatalInjectOnlyDetail(status, details);
    }

//    public static void fatalInject(@Nonnull RestStatus status, @Nonnull String injectErrorMessage) {
//        Preconditions.checkNotNull(status);
//        Preconditions.checkNotNull(injectErrorMessage);
//        fatalInject(status, injectErrorMessage, null);
//    }

    private static void fatalInject(@Nonnull RestStatus status, String details) {
        Preconditions.checkNotNull(status);
        ErrorEntity entity = new ErrorEntity(status);
        if(!Strings.isNullOrEmpty(details)) {
            entity.setDetail(details);
        }
//        Optional.ofNullable(injectErrorMessage).filter((x) -> {
//            return !x.isEmpty();
//        }).ifPresent(entity::setReturnMsg);
//        Optional.ofNullable(details).ifPresent(entity::setDetails);
        bindStatusCodesInRequest(entity, status.name());
        throw new RestStatusException(status.name());
    }

    private static void fatalInjectOnlyDetail(@Nonnull RestStatus status, String details) {
        Preconditions.checkNotNull(status);
        ErrorEntity entity = new ErrorEntity(status);
        if(!Strings.isNullOrEmpty(details)) {
            entity.setOnlyDetail(details);
        }
        bindStatusCodesInRequest(entity, status.name());
        throw new RestStatusException(status.name());
    }

    private static String decideFieldName(@Nonnull BindingResult result, @Nonnull FieldError fieldError) {
        Preconditions.checkNotNull(result);
        Preconditions.checkNotNull(fieldError);
        String errorFieldName = fieldError.getField();
        Class clazz = result.getTarget().getClass();
        try {
            Field field = clazz.getDeclaredField(fieldError.getField());
            JsonProperty annotation = (JsonProperty)field.getAnnotation(JsonProperty.class);
            if (annotation != null) {
                errorFieldName = annotation.value();
            }
        } catch (NoSuchFieldException var6) {
            Throwables.throwIfUnchecked(var6);
            LOGGER.error("decideFieldName([result, fieldError])-> {}", var6.getMessage());
        }
        return errorFieldName;
    }

    private static void bindStatusCodesInRequest(@Nonnull ErrorEntity entity, @Nonnull String name) {
        Preconditions.checkNotNull(entity);
        Preconditions.checkNotNull(name);
        LOGGER.error("name:"+name+",entity:"+ JsonUtils.parse(entity));
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().setAttribute(name, entity);
        }
    }

}
