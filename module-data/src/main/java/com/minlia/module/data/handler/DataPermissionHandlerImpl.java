package com.minlia.module.data.handler;

import cn.hutool.db.Db;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.google.common.base.Joiner;
import com.minlia.cloud.constant.SymbolConstants;
import com.minlia.module.data.constant.SysDataConstants;
import com.minlia.module.data.datascopee.DataScopeTypeEnum;
import com.minlia.module.redis.util.RedisUtils;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import com.minlia.modules.security.model.DataPermission;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author garen
 */
@AllArgsConstructor
public class DataPermissionHandlerImpl implements DataPermissionHandler {

    private DataSource dataSource;
    private final static String EQ = "org_id = ";
    private final static String IN = "org_id in (%s)";

    @SneakyThrows
    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        //判断是否需要过滤
        if (RedisUtils.sHasValue(SysDataConstants.Redis.DATA_PERMISSION_PREFIX, mappedStatementId)) {
            //从上下文获取 org_id, dp_type, dp_scope
            DataPermission dataPermission = MinliaSecurityContextHolder.getDataPermission();
            if (Objects.nonNull(dataPermission)) {
                Expression sqlSegmentExpression = builder(dataPermission);
                if (null != where) {
                    return new AndExpression(where, sqlSegmentExpression);
                }
                return sqlSegmentExpression;
            }
        }
        return where;
    }

    @SneakyThrows
    public Expression builder(DataPermission dataPermission) {
        String condition = null;
        if (DataScopeTypeEnum.ALL.getType() == dataPermission.getDpType()) {
            return null;
        } else if (DataScopeTypeEnum.CUSTOMIZE.getType() == dataPermission.getDpType()) {
            condition = dataPermission.getDpScope();
        } else if (DataScopeTypeEnum.OWN_CHILD.getType() == dataPermission.getDpType()) {
            //获取所有子集
            condition = splitIn(Db.use(dataSource)
                    .findBy("sys_org_relation", "ancestor", dataPermission.getOrgId())
                    .stream().map(entity -> entity.getLong("descendant"))
                    .collect(Collectors.toList()));
        } else if (DataScopeTypeEnum.OWN.getType() == dataPermission.getDpType()) {
            condition = EQ + dataPermission.getOrgId();
        } else if (DataScopeTypeEnum.OWN_PARENT.getType() == dataPermission.getDpType()) {
            //获取所有父集
            condition = splitIn(Db.use(dataSource)
                    .findBy("sys_org_relation", "ancestor", dataPermission.getOrgId())
                    .stream().map(entity -> entity.getLong("descendant"))
                    .collect(Collectors.toList()));
        }
        assert condition != null;
        return CCJSqlParserUtil.parseCondExpression(condition);
    }

    public static String splitIn(List<Long> orgIdList) {
        return String.format(IN, Joiner.on(SymbolConstants.COMMA).join(orgIdList));
//        return String.format(IN, String.join(SymbolConstants.COMMA, orgIdList));
    }

}