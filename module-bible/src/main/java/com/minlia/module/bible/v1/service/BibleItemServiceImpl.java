//package com.minlia.module.bible.v1.service;
//
//import com.minlia.boot.utils.ApiPreconditions;
//import com.minlia.boot.v1.service.AbstractService;
//import com.minlia.module.bible.v1.code.BibleApiCode;
//import com.minlia.module.bible.v1.domain.Bible;
//import com.minlia.module.bible.v1.domain.BibleItem;
//import com.minlia.module.bible.v1.repository.BibleItemRepository;
//import com.minlia.module.bible.v1.repository.BibleRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//
//@Service
//@Slf4j
//public class BibleItemServiceImpl extends AbstractService<BibleItem> implements BibleItemService {
//
//    @Autowired
//    BibleRepository bibleRepository;
//
//    @Autowired
//    BibleItemRepository bibleItemRepository;
//
//
//    @Override
//    protected JpaRepository<BibleItem, Long> getRepository() {
//        return bibleItemRepository;
//    }
//
//    @Override
//    public BibleItem createItem(String code, BibleItem body) {
//        Bible parentEntity = bibleRepository.findOneByCode(code);
//        ApiPreconditions.checkNotNull(parentEntity, BibleApiCode.NOT_FOUND);
//
//        body.setBible(parentEntity);
//        BibleItem entityCreated = bibleItemRepository.save(body);
//        return entityCreated;
//    }
//
//    @Override
//    public Set<BibleItem> findByBible_Code(String bibleCode) {
//        return bibleItemRepository.findByBible_Code(bibleCode);
//    }
//
//
//    public BibleItem update(BibleItem entity){
//
//        BibleItem entityFound=bibleItemRepository.findOne(entity.getId());
//        ApiPreconditions.checkNotNull(entityFound, BibleApiCode.NOT_FOUND);
//
//        entityFound.setCode(entity.getCode());
//        entityFound.setLabel(entity.getLabel());
//        entityFound.setNotes(entity.getNotes());
//
//        BibleItem updated=bibleItemRepository.save(entityFound);
//        return updated;
//    }
//
//}
