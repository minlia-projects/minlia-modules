package com.minlia.cloud.data.batis.support.repository.impl;

import com.google.common.collect.Lists;
import com.minlia.cloud.body.query.Combo;
import com.minlia.cloud.body.query.ComboData;
import com.minlia.cloud.body.query.ComboQuery;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractAuditableEntity;
import com.minlia.cloud.data.batis.support.repository.JpaCustomeRepository;
import com.minlia.cloud.utils.PreconditionsHelper;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class JpaCustomeRepositoryImpl<ENTITY extends AbstractAuditableEntity>
        extends SqlSessionRepositorySupport implements JpaCustomeRepository<ENTITY> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected JpaCustomeRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComboData> findJson(Combo combo) {

        List<ComboData> mapList = Lists.newArrayList();
        if (PreconditionsHelper.isNotEmpty(combo) && PreconditionsHelper.isNotEmpty(combo.getId())
                && PreconditionsHelper.isNotEmpty(combo.getName()) && PreconditionsHelper.isNotEmpty(combo.getModule())) {
            StringBuffer sb = new StringBuffer()
                    .append(combo.getId()).append("as id,").append(combo.getName()).append("as name,");
            boolean flag = PreconditionsHelper.isNotEmpty(combo.getParentId());
            if (flag) sb.append(",").append(combo.getParentId()).append("as pId");
            ComboQuery comboQuery = new ComboQuery();
            comboQuery.setColumns(sb.toString());
            comboQuery.setTableName(combo.getName());
            if (PreconditionsHelper.isNotEmpty(combo.getWhere())) comboQuery.setCondition(" and " + combo.getWhere());
            mapList = selectOne("_findByCombo", comboQuery);
        }
        return mapList;
    }


    @Override
    protected String getNamespace() {
        return JpaCustomeRepositoryImpl.class.getName();
    }
}
