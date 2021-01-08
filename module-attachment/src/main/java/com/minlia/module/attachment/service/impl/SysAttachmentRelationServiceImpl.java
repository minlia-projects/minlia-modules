package com.minlia.module.attachment.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.attachment.entity.SysAttachmentRelationEntity;
import com.minlia.module.attachment.mapper.SysAttachmentRelationMapper;
import com.minlia.module.attachment.service.SysAttachmentRelationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 附件-关系 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-03
 */
@Service
public class SysAttachmentRelationServiceImpl extends ServiceImpl<SysAttachmentRelationMapper, SysAttachmentRelationEntity> implements SysAttachmentRelationService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Long relationId, String relationTo, Set<String> urls) {
        this.remove(Wrappers.<SysAttachmentRelationEntity>lambdaQuery().eq(SysAttachmentRelationEntity::getRelationId, relationId).eq(SysAttachmentRelationEntity::getRelationTo, relationTo));
        this.saveBatch(urls.stream().map(url -> SysAttachmentRelationEntity.builder()
                .relationId(relationId).relationTo(relationTo).url(url).build()).collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Long relationId, String relationTo, String url) {
        this.remove(Wrappers.<SysAttachmentRelationEntity>lambdaQuery().eq(SysAttachmentRelationEntity::getRelationId, relationId).eq(SysAttachmentRelationEntity::getRelationTo, relationTo));
        this.save(SysAttachmentRelationEntity.builder().relationId(relationId).relationTo(relationTo).url(url).build());
    }

    @Override
    public List<String> getUrls(Long relationId, String relationTo) {
        return this.list(Wrappers.<SysAttachmentRelationEntity>lambdaQuery()
                .select(SysAttachmentRelationEntity::getUrl)
                .eq(SysAttachmentRelationEntity::getRelationId, relationId)
                .eq(SysAttachmentRelationEntity::getRelationTo, relationTo))
                .stream().map(data -> data.getUrl()).collect(Collectors.toList());
    }

    @Override
    public String getUrl(Long relationId, String relationTo) {
        List<String> urls = this.getUrls(relationId, relationTo);
        return CollectionUtils.isNotEmpty(urls) ? urls.get(0): null;
    }

}