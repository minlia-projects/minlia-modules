package com.minlia.cloud.data.batis.support.persistence;

import com.google.common.collect.Lists;
import com.minlia.cloud.body.query.QueryOperator;
import com.minlia.cloud.data.batis.support.query.ApiSearchRequestBody;
import com.minlia.cloud.data.batis.support.query.QueryCondition;
import com.minlia.cloud.data.batis.support.query.SearchRequestBody;
import com.minlia.cloud.utils.Reflections;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Service
public class QuerySpecifications<PAYLOAD extends SearchRequestBody> {


    public static final String MYBITS_SEARCH_PARAMS_MAP = "paramsMap";
    public static final String MYBITS_SEARCH_DSF = "_sqlConditionDsf";
    public static final String MYBITS_SEARCH_CONDITION = "_condition";


    public static final String OPERATOR_FIELD_SUFFIX = "Operator";

    public static <T> SpecificationDetail<T> buildSpecification(ApiSearchRequestBody<?> body) {
        List<QueryCondition> payloadCondition = Lists.newArrayList();
        if (null != body.getPayload()) {
            SearchRequestBody content = body.getPayload();
            List<Field> fields = FieldUtils.getAllFieldsList(content.getClass());

            for (Field field : fields) {
                val fieldName = field.getName();
                if (!fieldName.endsWith(OPERATOR_FIELD_SUFFIX)) {
                    Object value = Reflections.getFieldValue(content, fieldName);

                    //根据字段获取相应的操作符, 如为空则默认为 equals
                    Object operatorObject = null;
                    try {
                        operatorObject = Reflections.getFieldValue(content, fieldName + OPERATOR_FIELD_SUFFIX);
                    } catch (Exception e) {
//                        log.info("No such field found {}", e);
                    }

                    if (null != operatorObject) {
                        QueryOperator operator = null;

                        try {
                            operator = QueryOperator.valueOf(operatorObject.toString());
                        } catch (Exception e) {
//                            log.info("No such operator found {}", e);
                            operator = QueryOperator.eq;
                        }

                        //null != value &&
                        if (null != value && null != operator) {
                            QueryCondition queryCondition = new QueryCondition(fieldName, operator, value);
                            payloadCondition.add(queryCondition);
                        }
                    }
                }
            }

            if (CollectionUtils.isEmpty(body.getConditions())) {
                return new SpecificationDetail<T>().andAll(payloadCondition);
            } else {
                return new SpecificationDetail<T>().andAll(payloadCondition).andAll(body.getConditions());
            }
        } else {
            //没有 conditions
            if (CollectionUtils.isEmpty(body.getConditions())) {
                return new SpecificationDetail<T>();
            } else {
                //有conditions
                return new SpecificationDetail<T>().andAll(body.getConditions());
            }

        }
    }
}