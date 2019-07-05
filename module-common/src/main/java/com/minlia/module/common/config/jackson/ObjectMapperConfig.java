package com.minlia.module.common.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/4/28 2:39 PM
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);

    /**
     * Json序列化和反序列化转换器，用于转换Post请求体中的json以及将我们的对象序列化为返回响应的json
     */
//  是在@ResponseBody转换json的时候不打印null的内容
//  @JsonInclude(JsonInclude.Include.NON_NULL)
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);               // 禁用序列化日期为timestamps
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);            // 禁用遇到未知属性抛出异常
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);     // 禁用将日期调整到时间区域
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);                     // 禁用空对象序列化
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);     // 启用空字符传为null
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);  // 允许属性名称没有引号
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);             // 启用美化打印
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);               // 如果为空则不输出
        objectMapper.registerModule(javaTimeModule());
        return objectMapper;
    }

    /**
     * LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
     *
     * @return
     */
    private JavaTimeModule javaTimeModule() {
//        ZoneOffset zoneOffset = ZoneOffset.of("+8");
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));

        javaTimeModule.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//                jsonGenerator.writeNumber(localDateTime.toEpochSecond(ZoneOffset.ofHours(8)));
                jsonGenerator.writeNumber(localDateTime.toInstant(zoneOffset).toEpochMilli());
            }
        });

        javaTimeModule.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeNumber(localDate.atStartOfDay().toInstant(zoneOffset).toEpochMilli());
            }
        });

//        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
//        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
//        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));

        javaTimeModule.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                if (NumberUtils.isDigits(jsonParser.getText())) {
//                    return LocalDateTime.ofEpochSecond(jsonParser.getLongValue() / 1000, 0, ZoneOffset.ofHours(8));
                    return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.getLongValue()), zoneOffset);
                } else {
                    if (jsonParser.getText().contains("T")) {
                        return LocalDateTime.parse(jsonParser.getText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                    } else {
                        return LocalDateTime.parse(jsonParser.getText(), DATE_TIME_FORMATTER);
                    }
                }
            }
        });

        javaTimeModule.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                if (NumberUtils.isDigits(jsonParser.getText())) {
                    return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.getLongValue()), zoneOffset).toLocalDate();
                } else {
                    return LocalDate.parse(jsonParser.getText());
                }
            }
        });

        javaTimeModule.addDeserializer(LocalTime.class, new JsonDeserializer<LocalTime>() {
            @Override
            public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                if (NumberUtils.isDigits(jsonParser.getText())) {
                    return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.getLongValue()), zoneOffset).toLocalTime();
                } else {
                    return LocalTime.parse(jsonParser.getText());
                }
            }
        });

        //Date序列化和反序列化
//        javaTimeModule.addSerializer(Date.class, new JsonSerializer<Date>() {
//            @Override
//            public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//                SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
//                String formattedDate = formatter.format(date);
//                jsonGenerator.writeString(formattedDate);
//            }
//        });
//        javaTimeModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
//            @Override
//            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//                SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
//                String date = jsonParser.getText();
//                try {
//                    return format.parse(date);
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
        return javaTimeModule;
    }

    public static void main(String[] args) {
        Long remindTime = 1534825831L;
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(remindTime, 0, ZoneOffset.ofHours(8));
        System.out.println(localDateTime);
        System.out.println(localDateTime.toLocalDate());
        System.out.println(localDateTime.toLocalTime());


        System.out.println(localDateTime.toLocalDate().atStartOfDay(ZoneOffset.ofHours(8)));

        System.out.println(LocalDate.parse("2019-05-05"));
        System.out.println(LocalTime.parse("12:12:12"));

        String s = "2019-07-04T11:47:32.041Z";
        System.out.println(LocalDateTime.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

}
