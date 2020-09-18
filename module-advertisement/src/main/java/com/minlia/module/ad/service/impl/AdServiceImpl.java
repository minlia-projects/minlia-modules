package com.minlia.module.ad.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.ad.bean.entity.Advertisement;
import com.minlia.module.ad.bean.ro.AdCRO;
import com.minlia.module.ad.bean.ro.AdQRO;
import com.minlia.module.ad.bean.ro.AdURO;
import com.minlia.module.ad.bean.ro.AdsQRO;
import com.minlia.module.ad.mapper.AdMapper;
import com.minlia.module.ad.service.AdService;
import com.minlia.module.ad.service.AdsService;
import com.minlia.modules.attachment.ro.AttachmentQRO;
import com.minlia.modules.attachment.service.AttachmentService;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 2018/09/27.
 */
@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AdMapper adMapper;

    @Autowired
    private AdsService adsService;

    @Override
    public Advertisement create(AdCRO cro) {
        long count = adsService.count(AdsQRO.builder().id(cro.getParentId()).build());
        ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);

        Advertisement advertisement = mapper.map(cro,Advertisement.class);
        adMapper.create(advertisement);

        //绑定附件
        if (null != cro.getCoverETag()) {
            attachmentService.bindByAccessKey(cro.getCoverETag(), advertisement.getId().toString(), "AD_COVER");
        }
        return advertisement;
    }

    @Override
    public Advertisement update(AdURO uro) {
        Advertisement advertisement = adMapper.queryById(uro.getId());
        ApiAssert.notNull(advertisement, SystemCode.Message.DATA_NOT_EXISTS);

        //绑定附件
        if (StringUtils.isNotBlank(uro.getCoverETag())) {
            attachmentService.bindByAccessKey(uro.getCoverETag(), advertisement.getId().toString(), "AD_COVER");
        }

        mapper.map(uro, advertisement);
        adMapper.update(advertisement);
        return advertisement;
    }

    @Override
    public void delete(Long id) {
        Advertisement advertisement = adMapper.queryById(id);
        ApiAssert.notNull(advertisement, SystemCode.Message.DATA_NOT_EXISTS);
        adMapper.delete(id);
    }


    @Override
    public long count(AdQRO qro) {
        return adMapper.count(qro);
    }

    @Override
    public Advertisement queryById(Long id) {
        return adMapper.one(AdQRO.builder().parentId(id).build());
    }

    @Override
    public Advertisement one(AdQRO qro) {
        Advertisement advertisement = adMapper.one(qro);
        bindDetailsParameter(advertisement);
        return advertisement;
    }

    @Override
    public List<Advertisement> list(AdQRO qro) {
        List<Advertisement> list = adMapper.list(qro);
        for (Advertisement advertisement : list) {
            bindDetailsParameter(advertisement);
        }
        return list;
    }

//    @Override
//    public PageInfo<Advertisement> page(AdQRO qro, Pageable pageable) {
//        PageInfo<Advertisement> pageInfo = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()-> adMapper.list(qro));
//        for (Advertisement advertisement : pageInfo.getList()) {
//            bindDetailsParameter(advertisement);
//        }
//        return pageInfo;
//    }

    private void bindDetailsParameter(Advertisement advertisement) {
        if (null != advertisement) {
            advertisement.setCover(attachmentService.queryOne(AttachmentQRO.builder().belongsTo("AD_COVER").relationId(advertisement.getId().toString()).build()));
        }
    }

}
