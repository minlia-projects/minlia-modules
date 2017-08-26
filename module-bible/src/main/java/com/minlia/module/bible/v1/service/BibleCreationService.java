//package com.minlia.module.bible.v1.service;
//
//import com.minlia.module.bible.v1.domain.Bible;
//import com.minlia.module.bible.v1.domain.BibleItem;
//import com.minlia.module.bible.v1.repository.BibleItemRepository;
//import com.minlia.module.bible.v1.repository.BibleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Created by will on 7/10/17.
// */
//@Service
//public class BibleCreationService {
//
//
//    @Autowired
//    BibleRepository bibleRepository;
//
//
//    @Autowired
//    BibleItemRepository bibleItemRepository;
//
//    /**
//     * 初始化系统Bible配置项
//     * 当有的时候不需要插入, 不存在时插入
//     *
//     * @param bibleCode
//     * @param bibleItemCode
//     * @param bibleItemLabel
//     * @param bibleItemNotes
//     */
//    public void initialBibleWithCode(String bibleCode, String bibleItemCode, String bibleItemLabel, String bibleItemNotes) {
//        Bible bibleFound = bibleRepository.findOneByCode(bibleCode);
//        //不为空, 已找到此BIBLE
//        if (null != bibleFound) {
//            //查找是否有子项, 没有则初始化
//            initialBibleItem(bibleItemCode, bibleItemLabel, bibleItemNotes, bibleFound);
//        } else {
//            //没找到此BIBLE先创建
//            Bible entity = new Bible();
//            entity.setCode(bibleCode);
//            entity.setNotes("系统自动初始化配置项" + bibleCode);
//            entity.setNotes(bibleCode);
//            Bible entityCreated = bibleRepository.save(entity);
//            initialBibleItem(bibleItemCode, bibleItemLabel, bibleItemNotes, entityCreated);
//        }
//    }
//
//
//    public void initialBibleItem(String bibleItemCode, String bibleItemLabel, String bibleItemNotes, Bible bible) {
////        BibleItem bibleItemFound = bibleItemRepository.findOneByCode(bibleItemCode);
//        BibleItem bibleItemFound = bibleItemRepository.findOneByBible_idAndCode(bible.getId(),bibleItemCode);
//
//        if (null == bibleItemFound) {
//            BibleItem bibleItem = new BibleItem();
//            bibleItem.setCode(bibleItemCode);
//            bibleItem.setLabel(bibleItemLabel);
//            bibleItem.setNotes(bibleItemNotes);
//            if (null != bible) {
//                bibleItem.setBible(bible);
//            }
//            bibleItemRepository.save(bibleItem);
//        }
//    }
//
//
//}
