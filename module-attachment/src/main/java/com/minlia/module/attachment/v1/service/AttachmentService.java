//package com.minlia.module.attachment.v1.service;
//
//
//import com.minlia.boot.v1.service.IService;
//import com.minlia.module.attachment.v1.domain.Attachment;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * Created by will on 6/17/17.
// */
//@Transactional(readOnly = false)
//public interface AttachmentService extends IService<Attachment> {
//
//    String ENTITY = "attachment";
//
//    String ENTITY_CREATE = ENTITY + ".create";
//    String ENTITY_UPDATE = ENTITY + ".update";
//    String ENTITY_DELETE = ENTITY + ".delete";
//    String ENTITY_READ = ENTITY + ".read";
//    String ENTITY_SEARCH= ENTITY + ".search";
//
//    /**
//     * 创建
//     * @param entity
//     * @return
//     */
//    Attachment create(Attachment entity);
//
//    /**
//     * 修改
//     * @param entity
//     * @return
//     */
//    Attachment update(Attachment entity);
//
//    /**
//     * 通过令牌绑定业务ID
//     * @param accessToken
//     * @param businessId
//     */
//    void bindByAccessToken(String accessToken,String businessId);
//
//    /**
//     * 删除
//     * @param id
//     */
//    void delete(Long id);
//
//    /**
//     * 读取
//     * @param id
//     * @return
//     */
//    Attachment findOne(Long id);
//
//    /**
//     * 返回所有
//     * @return
//     */
//    List<Attachment> findAll();
//
//}
