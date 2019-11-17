package com.minlia.module.data.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.minlia.module.data.annotation.SensitiveInfo;
import com.minlia.module.data.enumeration.SensitiveTypeEnum;

import java.io.IOException;
import java.util.Objects;

public class SensitiveInfoSerialize extends JsonSerializer<String> implements ContextualSerializer {

    private SensitiveTypeEnum type;

    private int size;

    private int begin;

    private int end;

    private String pad;

    public SensitiveInfoSerialize() {
    }

    public SensitiveInfoSerialize(final SensitiveTypeEnum type, final int size, final int begin, final int end, final String pad) {
        this.type = type;
        this.size = size;
        this.begin = begin;
        this.end = end;
        this.pad = pad;
    }

    @Override
    public void serialize(final String s, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        switch (this.type) {
            case ALL: {
                jsonGenerator.writeString(SensitiveInfoUtils.all(s, pad));
                break;
            } case AROUND: {
                jsonGenerator.writeString(SensitiveInfoUtils.around(s, begin, end, pad));
                break;
            } case LEFT: {
                jsonGenerator.writeString(SensitiveInfoUtils.left(s, size, pad));
                break;
            } case RIGHT: {
                jsonGenerator.writeString(SensitiveInfoUtils.right(s, size, pad));
                break;
            } case LEFT_PAD: {
                jsonGenerator.writeString(SensitiveInfoUtils.leftPad(s, size, pad));
                break;
            } case RIGHT_PAD: {
                jsonGenerator.writeString(SensitiveInfoUtils.rightPad(s, size, pad));
                break;
            } case CHINESE_NAME: {
                jsonGenerator.writeString(SensitiveInfoUtils.chineseName(s, pad));
                break;
            } case ID_CARD: {
                jsonGenerator.writeString(SensitiveInfoUtils.idCardNum(s, pad));
                break;
            }
            case FIXED_PHONE: {
                jsonGenerator.writeString(SensitiveInfoUtils.fixedPhone(s, pad));
                break;
            }
            case MOBILE_PHONE: {
                jsonGenerator.writeString(SensitiveInfoUtils.mobilePhone(s, pad));
                break;
            }
            case ADDRESS: {
                jsonGenerator.writeString(SensitiveInfoUtils.address(s, 4, pad));
                break;
            }
            case EMAIL: {
                jsonGenerator.writeString(SensitiveInfoUtils.email(s, pad));
                break;
            }
            case BANK_CARD: {
                jsonGenerator.writeString(SensitiveInfoUtils.bankCard(s, pad));
                break;
            }
            case CNAPS_CODE: {
                jsonGenerator.writeString(SensitiveInfoUtils.cnapsCode(s, pad));
                break;
            }
            case PASSWORD:
                jsonGenerator.writeString(SensitiveInfoUtils.password(s, pad));
                break;
            case HK_ID_CARD:
                jsonGenerator.writeString(SensitiveInfoUtils.hkIdCardNum(s, pad));
                break;
            case HK_CELLPHONE:
                jsonGenerator.writeString(SensitiveInfoUtils.hkMobilePhone(s, pad));
                break;
            case SURNAME:
                jsonGenerator.writeString(SensitiveInfoUtils.surname(s, pad));
                break;
            case GIVEN_NAME:
                jsonGenerator.writeString(SensitiveInfoUtils.givenName(s, pad));
                break;
        }

    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider, final BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) { // 为空直接跳过
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) { // 非 String 类直接跳过
                SensitiveInfo sensitiveInfo = beanProperty.getAnnotation(SensitiveInfo.class);
                if (sensitiveInfo == null) {
                    sensitiveInfo = beanProperty.getContextAnnotation(SensitiveInfo.class);
                }
                if (sensitiveInfo != null) { // 如果能得到注解，就将注解的 value 传入 SensitiveInfoSerialize
//                    return new SensitiveInfoSerialize(sensitiveInfo.value());
                    return new SensitiveInfoSerialize(sensitiveInfo.value(), sensitiveInfo.size(), sensitiveInfo.begin(), sensitiveInfo.end(), sensitiveInfo.pad());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(beanProperty);
    }

}