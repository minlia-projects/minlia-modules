package com.minlia.module.attachment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.attachment.bean.AttachmentCro;
import com.minlia.module.attachment.constant.SysAttachmentCode;
import com.minlia.module.attachment.entity.SysAttachmentEntity;
import com.minlia.module.attachment.event.AttachmentEvent;
import com.minlia.module.attachment.mapper.AttachmentMapper;
import com.minlia.module.attachment.service.AttachmentService;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 附件 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-26
 */
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, SysAttachmentEntity> implements AttachmentService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysAttachmentEntity> create(AttachmentCro cro) {
        //移除之前的
        this.remove(lambdaQuery().eq(SysAttachmentEntity::getRelationId, cro.getRelationId()).eq(SysAttachmentEntity::getRelationTo, cro.getRelationTo()));
        //批量保存
        List<SysAttachmentEntity> entities = cro.getData().stream().map(data -> SysAttachmentEntity.builder().relationTo(cro.getRelationTo()).relationId(cro.getRelationId()).url(data.getUrl()).accessKey(data.getAccessKey()).build()).collect(Collectors.toList());
        this.saveBatch(entities);
        AttachmentEvent.publish(entities.get(0));
        return entities;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysAttachmentEntity update(SysAttachmentEntity entity) {
        this.saveOrUpdate(entity);
        AttachmentEvent.publish(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String bindByAccessKey(String accessKey, String relationTo, String relationId) {
        if (StringUtils.isBlank(accessKey)) {
            //移除之前的
            this.remove(lambdaQuery().eq(SysAttachmentEntity::getRelationId, relationId).eq(SysAttachmentEntity::getRelationTo, relationTo));
            return null;
        } else {
            SysAttachmentEntity entity = this.getOne(lambdaQuery().eq(SysAttachmentEntity::getAccessKey, accessKey).eq(SysAttachmentEntity::getRelationTo, relationTo).eq(SysAttachmentEntity::getRelationId, relationId));
            if (null != entity) {
                return entity.getUrl();
            } else {
                entity = this.getOne(lambdaQuery().eq(SysAttachmentEntity::getAccessKey, accessKey).isNull(SysAttachmentEntity::getRelationTo).isNull(SysAttachmentEntity::getRelationId));
                ApiAssert.notNull(entity, SysAttachmentCode.Message.ETAG_NOT_EXISTS);
                this.remove(lambdaQuery().eq(SysAttachmentEntity::getRelationId, relationId).eq(SysAttachmentEntity::getRelationTo, relationTo));
                entity.setRelationTo(relationTo);
                entity.setRelationId(relationId);
                this.saveOrUpdate(entity);
                return entity.getUrl();
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindByAccessKey(List<String> accessKeys, String relationTo, String relationId, boolean allowNull) {
        if (CollectionUtils.isEmpty(accessKeys)) {
            this.remove(lambdaQuery().eq(SysAttachmentEntity::getRelationId, relationId).eq(SysAttachmentEntity::getRelationTo, relationTo));
        } else {
            for (String accessKey : accessKeys) {
                long count = this.count(lambdaQuery().eq(SysAttachmentEntity::getAccessKey, accessKey).eq(SysAttachmentEntity::getRelationId, relationId).eq(SysAttachmentEntity::getRelationTo, relationTo));
                if (count == 0) {
                    SysAttachmentEntity entity = this.getOne(lambdaQuery().eq(SysAttachmentEntity::getAccessKey, accessKey).isNull(SysAttachmentEntity::getRelationTo).isNull(SysAttachmentEntity::getRelationId));
                    if (allowNull && null == entity) {
                        entity = this.getOne(lambdaQuery().eq(SysAttachmentEntity::getAccessKey, accessKey).orderByDesc(SysAttachmentEntity::getId));
                        entity.setId(null);
                        entity.setRelationId(relationTo);
                        entity.setRelationId(relationId);
                        entity.setCreateBy(MinliaSecurityContextHolder.getUid());
                        this.save(entity);
                    } else {
                        ApiAssert.notNull(entity, SysAttachmentCode.Message.ETAG_NOT_EXISTS);
                        entity.setRelationId(relationTo);
                        entity.setRelationId(relationId);
                        this.saveOrUpdate(entity);
                    }
                }
            }
            this.remove(lambdaQuery().eq(SysAttachmentEntity::getRelationTo, relationTo).eq(SysAttachmentEntity::getRelationId, relationId).notIn(SysAttachmentEntity::getAccessKey, accessKeys));
        }
    }

//    @Override
//    public List<String> queryUrls(AttachmentQro qo) {
//        return attachmentMapper.queryUrlList(qo);
//    }
//
//    @Override
//    public String queryUrls(String relationId, String belongsTo) {
//        return attachmentMapper.queryUrls(relationId, belongsTo);
//    }
//
//    @Override
//    public String queryFirstUrl(String relationId, String belongsTo) {
//        return attachmentMapper.queryFirstUrl(relationId, belongsTo);
//    }

//    <select id="queryUrlList" resultType="String">
//    SELECT
//    mat.url
//    FROM mdl_attachment mat
//            <include refid="QUERY_CONDITION"/>
//    </select>
//
//    <select id="queryUrls" resultType="java.lang.String">
//    SELECT IFNULL(GROUP_CONCAT(url),'') FROM mdl_attachment WHERE relation_id = #{relationId} AND belongs_to = #{belongsTo}
//    </select>
//
//    <select id="queryFirstUrl" resultType="java.lang.String">
//    SELECT url FROM mdl_attachment WHERE relation_id = #{relationId} AND belongs_to = #{belongsTo} LIMIT 0 , 1
//    </select>

}
