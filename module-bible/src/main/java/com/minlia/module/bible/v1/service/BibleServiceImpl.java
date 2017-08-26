//package com.minlia.module.bible.v1.service;
//
//import com.minlia.boot.utils.ApiPreconditions;
//import com.minlia.boot.v1.code.ApiCode;
//import com.minlia.boot.v1.service.AbstractService;
//import com.minlia.module.bible.v1.code.BibleApiCode;
//import com.minlia.module.bible.v1.domain.Bible;
//import com.minlia.module.bible.v1.repository.BibleRepository;
//import com.minlia.module.security.v1.service.PermissionCreationService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class BibleServiceImpl extends AbstractService<Bible> implements BibleService {
//
//
//    @Autowired
//    BibleRepository bibleRepository;
//
//    @Autowired
//    PermissionCreationService permissionCreationService;
//
//
//    @Override
//    protected JpaRepository<Bible, Long> getRepository() {
//        return bibleRepository;
//    }
//
//    public Bible create(Bible bible) {
//        Bible found=bibleRepository.findOneByCode(bible.getCode());
//        if(null!=found){
//            ApiPreconditions.checkNotNull(found, ApiCode.DATA_ALREADY_EXISTS);
//        }
//        return bibleRepository.save(bible);
//    }
//
//    /**
//     * 当有子项时无法删除
//     *
//     * @param id
//     */
//    public void delete(Long id) {
//        Bible found = bibleRepository.findOne(id);
//        ApiPreconditions.checkNotNull(found, BibleApiCode.NOT_FOUND);
//        if (null != found.getItems() || found.getItems().size() > 0) {
//            ApiPreconditions.checkNotNull(found, BibleApiCode.COULD_NOT_DELETE_HAS_CHILDREN);
//        } else {
//            bibleRepository.delete(id);
//        }
//    }
//
//
//}
