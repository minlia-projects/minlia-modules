package com.minlia.module.attachment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.attachment.entity.SysAttachmentRelationEntity;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 附件-关系 服务类
 * </p>
 *
 * @author garen
 * @since 2020-09-03
 */
public interface SysAttachmentRelationService extends IService<SysAttachmentRelationEntity> {

    /**
     * 保存附件关系
     *
     * @param relationId
     * @param relationTo
     * @param urls
     */
    void save(Long relationId, String relationTo, Set<String> urls);

    /**
     * 保存附件关系
     *
     * @param relationId
     * @param relationTo
     * @param url
     */
    void save(Long relationId, String relationTo, String url);

    /**
     * 获取附近地址
     *
     * @param relationId
     * @param relationTo
     * @return
     */
    List<String> getUrls(Long relationId, String relationTo);

    /**
     * 获取附近地址
     *
     * @param relationId
     * @param relationTo
     * @return
     */
    String getUrl(Long relationId, String relationTo);

}