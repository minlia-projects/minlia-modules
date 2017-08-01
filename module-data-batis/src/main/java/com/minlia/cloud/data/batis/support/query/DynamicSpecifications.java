package com.minlia.cloud.data.batis.support.query;

import com.google.common.collect.Lists;
import com.minlia.cloud.body.query.QueryOperator;
import com.minlia.cloud.data.batis.support.persistence.SpecificationDetail;
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
@Deprecated
public class DynamicSpecifications<PAYLOAD extends SearchRequestBody> {

    public static final String OPERATOR_FIELD_SUFFIX = "Operator";

    public <T> SpecificationDetail<T> buildSpecification(ApiSearchRequestBody<PAYLOAD> body) {
        List<QueryCondition> payloadCondition = Lists.newArrayList();
        if (null != body.getPayload()) {
            SearchRequestBody content = body.getPayload();
            List<Field> fields = FieldUtils.getAllFieldsList(content.getClass());

            for (Field field : fields) {
                val fieldName = field.getName();
                if (!fieldName.endsWith(OPERATOR_FIELD_SUFFIX)) {
                    Object value = Reflections.getFieldValue(content, fieldName);

                    //根据字段获取相应的操作符, 如为空则默认为 equals
                    Object operatorObject =null;
                    try {
                        operatorObject = Reflections.getFieldValue(content, fieldName + OPERATOR_FIELD_SUFFIX);
                    }catch (Exception e){
//                        log.info("No such field found {}", e);
                    }

                    if(null!=operatorObject) {
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

            if(CollectionUtils.isEmpty(body.getConditions())){
                return new SpecificationDetail<T>().andAll(payloadCondition);
            } else {
                return new SpecificationDetail<T>().andAll(payloadCondition).andAll(body.getConditions());
            }
        } else {
            //没有 conditions
            if(CollectionUtils.isEmpty(body.getConditions()) ){
                return new SpecificationDetail<T>();
            }else {
                //有conditions
                return new SpecificationDetail<T>().andAll(body.getConditions());
            }

        }
    }


//    public static <T> SpecificationDetail<T> buildSpecification(List<QueryCondition> postedConditions, List<QueryCondition> overrideConditions, QueryCondition... conditions) {
//        if (CollectionUtils.isEmpty(postedConditions)) postedConditions = Lists.newArrayList();
//        if (CollectionUtils.isEmpty(overrideConditions)) overrideConditions = Lists.newArrayList();
//
//        overrideConditions.addAll(postedConditions);
//
//        return new SpecificationDetail<T>().andAll(overrideConditions).and(conditions);
//    }
//
//    public static <T> SpecificationDetail<T> buildSpecification(List<QueryCondition> postedConditions, QueryCondition... conditions) {
//        if (CollectionUtils.isEmpty(postedConditions)) postedConditions = Lists.newArrayList();
//        return new SpecificationDetail<T>().andAll(postedConditions).and(conditions);
//    }
//
//
//    public static <T> SpecificationDetail<T> bySearchQueryCondition(final List<QueryCondition> queryConditions) {
//        return new SpecificationDetail<T>().andAll(queryConditions);
//    }
//
//    public static <T> SpecificationDetail<T> bySearchQueryCondition(final List<QueryCondition> queryConditions, QueryCondition... conditions) {
//        return new SpecificationDetail<T>().andAll(queryConditions).and(conditions);
//    }
//
//    public static <T> SpecificationDetail<T> bySearchQueryCondition(QueryCondition... conditions) {
//        return new SpecificationDetail<T>().and(conditions);
//    }
//
//    public static <T> SpecificationDetail<T> buildSpecification(String queryConditionJson, QueryCondition... conditions) {
//        return buildSpecification(queryConditionJson, null, conditions);
//    }
//
//    public static <T> SpecificationDetail<T> buildSpecification(String queryConditionJson, List<QueryCondition> list, QueryCondition... conditions) {
//        if (list == null) list = Lists.newArrayList();
//
//        if (PublicUtil.isNotEmpty(queryConditionJson)) {
//            try {
//                list.addAll(JSONArray.parseArray(queryConditionJson, QueryCondition.class));
//            } catch (Exception e) {
//                log.warn(PublicUtil.toAppendStr("queryCondition[", queryConditionJson,
//                        "] is not json or other error", e.getMessage()));
//            }
//        }
//
//        return new SpecificationDetail<T>().andAll(list).and(conditions);
//    }

}