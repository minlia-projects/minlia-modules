package com.minlia.cloud.query.specification.jpa;

import com.alibaba.fastjson.annotation.JSONField;
import com.minlia.cloud.body.query.QueryOperator;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.Encodes;
import com.minlia.cloud.utils.PreconditionsHelper;
import com.minlia.cloud.utils.QueryStatementUtil;
import com.minlia.cloud.utils.Reflections;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.Dialect;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;

@Slf4j
@Data
public class QueryCondition implements Comparable<QueryCondition>, java.io.Serializable {

    /*** 节点 */
    public static final String F_FILEDNODE = "fieldNode";
    /*** 实体属性 注意区分大小写 */
    public static final String F_FILEDNAME = "fieldName";
    /*** 操作符 */
    public static final String F_OPERATE = "operate";
    /*** 值 */
    public static final String F_ATTRTYPE = "attrType";
    public static final String F_FORMAT = "format";
    public static final String F_VALUE = "value";
    public static final String F_ENDVALUE = "endValue";
    /*** 查询权重 */
    public static final String F_WEIGHT = "weight";
    private static final long serialVersionUID = 1L;
    private Class<?> persistentClass;
    /**
     * 节点
     */
    private String fieldNode;
    /**
     * 实体属性 注意区分大小写
     */
    private String fieldName;
    /**
     * 操作符
     */
    private QueryOperator operate;
    /**
     * 值类型
     */
    private String attrType;
    /**
     * 值格式
     */
    private String format;
    /**
     * 值
     */
    private Object value;
    /**
     * 第二个值 operate= between
     */
    private Object endValue;
    /**
     * @Fields ingore : 是否忽略
     */
    private boolean ingore = false;
    /**
     * 查询权重
     */
    private Integer weight = Integer.valueOf(0x186a0);

    /**
     * 是否解析动态条件查询前缀（一般自定义sql查询时建议设置为false,动态则设置为true）
     */
    @JSONField(serialize = false)
    private boolean analytiColumn = true;

    /**
     * 解析动态条件查询前缀
     */
    @JSONField(serialize = false)
    private String analytiColumnPrefix;
    @JSONField(serialize = false)
    private String fieldRealColumnName;

    public QueryCondition() {
    }

    public QueryCondition(String fieldNode, String fieldName, QueryOperator operate, Object value) {
        this.fieldNode = fieldNode;
        this.fieldName = fieldName;
        this.operate = operate;
        this.value = value;
    }

    public QueryCondition(String fieldName, QueryOperator operate, Object value, Class<?> persistentClass) {
        this.fieldName = fieldName;
        this.operate = operate;
        this.value = value;
        this.persistentClass = persistentClass;
    }

    public QueryCondition(String fieldName, QueryOperator operate, Object value) {
        this.fieldName = fieldName;
        this.operate = operate;
        this.value = value;
    }

    /**
     * 返回等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 等于筛选
     */
    public static QueryCondition eq(String property, Object value) {
        return new QueryCondition(property, QueryOperator.eq, value);
    }

    /**
     * 返回不等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 不等于筛选
     */
    public static QueryCondition ne(String property, Object value) {
        return new QueryCondition(property, QueryOperator.ne, value);
    }

    public QueryCondition setAnalytiColumn(boolean flag) {
        this.analytiColumn = flag;
        return this;
    }

    public QueryCondition setAnalytiColumnPrefix(String analytiPrefix) {
        this.analytiColumnPrefix = analytiPrefix;
        return this;
    }

    /**
     * 返回大于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 大于筛选
     */
    public static QueryCondition gt(String property, Object value) {
        return new QueryCondition(property, QueryOperator.gt, value);
    }

    /**
     * 返回小于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 小于筛选
     */
    public static QueryCondition lt(String property, Object value) {
        return new QueryCondition(property, QueryOperator.lt, value);
    }

    /**
     * 返回大于等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 大于等于筛选
     */
    public static QueryCondition ge(String property, Object value) {
        return new QueryCondition(property, QueryOperator.ge, value);
    }

    /**
     * 返回小于等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 小于等于筛选
     */
    public static QueryCondition le(String property, Object value) {
        return new QueryCondition(property, QueryOperator.le, value);
    }

    /**
     * 返回相似筛选
     *
     * @param property 属性
     * @param value    值
     * @return 相似筛选
     */
    public static QueryCondition like(String property, Object value) {
        return new QueryCondition(property, QueryOperator.like, value);
    }

    /**
     * 返回包含筛选
     *
     * @param property 属性
     * @param value    值
     * @return 包含筛选
     */
    public static QueryCondition in(String property, Object value) {
        return new QueryCondition(property, QueryOperator.in, value);
    }

    /**
     * 返回为Null筛选
     *
     * @param property 属性
     * @return 为Null筛选
     */
    public static QueryCondition isNull(String property) {
        return new QueryCondition(property, QueryOperator.isNull, null);
    }

    /**
     * 返回不为Null筛选
     *
     * @param property 属性
     * @return 不为Null筛选
     */
    public static QueryCondition isNotNull(String property) {
        return new QueryCondition(property, QueryOperator.isNotNull, null);
    }

    public String getFieldNode() {
        return fieldNode;
    }

    public void setFieldNode(String fieldNode) {
        this.fieldNode = fieldNode;
    }

    public String getFieldName() {
        if (PreconditionsHelper.isNotEmpty(fieldName) && fieldName.contains("this.")) {
            fieldName = fieldName.replace("this.", "");
        }
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @JSONField(serialize = false)
    public String getFieldRealColumnName() {
        if (fieldRealColumnName == null) {
            String columnName = null, fieldPropery = fieldName;
            Class<?> targetPersistentClass = persistentClass;
            if (analytiColumn) {
                try {
                    if (persistentClass != null && PreconditionsHelper.isNotEmpty(getFieldName())) {
                        String quota = analytiColumnPrefix;
                        int indexQuote = fieldPropery.indexOf(".");
                        if (PreconditionsHelper.isEmpty(quota)) {
                            Entity entity = Reflections.getAnnotation(targetPersistentClass, Entity.class);
                            quota = null != entity && StringUtils.hasText(entity.name()) ? entity.name() :
                                    StringUtils.uncapitalize(targetPersistentClass.getSimpleName());
                            if (indexQuote != -1) {
                                quota += "." + fieldPropery.substring(0, fieldPropery.lastIndexOf("."));
                            }
                        }
                        if (indexQuote != -1) {
                            String[] properties = fieldPropery.split("\\.");
                            int size = properties.length;
                            for (int i = 0; i < size - 1; i++) {
                                Field field = Reflections.getAccessibleField(targetPersistentClass, properties[i]);
                                targetPersistentClass = field.getType();
                            }
                            fieldPropery = properties[size - 1];
                        }
                        Column column = Reflections.getAnnotationByClazz(targetPersistentClass, fieldPropery, Column.class);
                        if (column != null) columnName = column.name();

                        if (PreconditionsHelper.isNotEmpty(columnName)) {
                            Dialect dialect = ContextHolder.getContext().getBean(Dialect.class);
                            fieldRealColumnName = dialect.openQuote() + quota + dialect.closeQuote() + '.' + columnName;
                        }else{
                            fieldRealColumnName=LOWER_CAMEL.to(UPPER_UNDERSCORE,fieldPropery);
                        }
                    }
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }

        }
        return fieldRealColumnName;
//        return null;
    }

    public QueryOperator getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = QueryOperator.valueOf(operate);
    }

    public void setOperate(QueryOperator operate) {
        this.operate = operate;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        //字符串编码处理
        if (value instanceof String) {
            String tempStr = value.toString();
            if (tempStr.contains("&")) {
                try {
                    value = new String(Encodes.unescapeHtml(tempStr).getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    log.warn("Illegal query conditions ---------> queryFieldName[",
                            fieldName, "]  operation[", operate.getOperator(),
                            "] value[", value, "], please check!!!");
                }
            }
        }
        this.value = value;
    }

    @JSONField(serialize = false)
    public boolean legalityCheck() {
        return QueryStatementUtil.checkStrForHqlWhere(fieldName)
                && QueryStatementUtil.checkStrForHqlWhere(operate.getOperator())
                && QueryStatementUtil.checkStrForHqlWhere(String.valueOf(value));
    }


    public Object getEndValue() {
        return endValue;
    }

    public void setEndValue(Object endValue) {
        this.endValue = endValue;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public boolean isIngore() {
        return ingore;
    }

    public void setIngore(boolean ingore) {
        this.ingore = ingore;
    }

    public int compareTo(QueryCondition arg) {
        int weight = valueOfQueryType(operate) - valueOfQueryType(arg.getOperate());
        return weight != 0 ? weight : this.weight.intValue() - arg.weight.intValue();
    }

    private int valueOfQueryType(QueryOperator op) {
        if (QueryOperator.eq.equals(op))
            return 0;
        if (QueryOperator.ne.equals(op))
            return 1;
        if (QueryOperator.between.equals(op))
            return 2;
        if (QueryOperator.in.equals(op))
            return 3;
        if (QueryOperator.ge.equals(op) || QueryOperator.gt.equals(op))
            return 4;
        if (QueryOperator.le.equals(op) || QueryOperator.lt.equals(op))
            return 5;
        if (QueryOperator.like.equals(op))
            return 6;
        return !"query".equals(op) ? 999 : 100;
    }


}
