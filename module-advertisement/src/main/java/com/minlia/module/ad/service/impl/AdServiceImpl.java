package com.minlia.module.ad.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.ad.ro.AdQRO;
import com.minlia.module.ad.ro.AdURO;
import com.minlia.module.ad.entity.Advertisement;
import com.minlia.module.ad.ro.AdsQRO;
import com.minlia.module.ad.ro.AdCRO;
import com.minlia.module.ad.mapper.AdMapper;
import com.minlia.module.ad.service.AdService;
import com.minlia.module.ad.service.AdsService;
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
    public Advertisement create(AdCRO cto) {
        long count = adsService.count(AdsQRO.builder().id(cto.getParentId()).build());
        ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);

        Advertisement advertisement = mapper.map(cto,Advertisement.class);
        adMapper.create(advertisement);

        //绑定附件
        if (null != cto.getCoverETag()) {
            attachmentService.bindByAccessKey(cto.getCoverETag(), advertisement.getId().toString(), "AD_COVER");
        }
        return advertisement;
    }

    @Override
    public Advertisement update(AdURO uto) {
        Advertisement advertisement = adMapper.queryById(uto.getId());
        ApiAssert.notNull(advertisement, SystemCode.Message.DATA_NOT_EXISTS);

        //绑定附件
        if (StringUtils.isNotBlank(uto.getCoverETag())) {
            attachmentService.bindByAccessKey(uto.getCoverETag(), advertisement.getId().toString(), "AD_COVER");
        }

        mapper.map(uto, advertisement);
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
    public Advertisement queryById(Long id) {
        return adMapper.queryById(id);
    }

    @Override
    public long count(AdQRO qo) {
        return adMapper.count(qo);
    }

    @Override
    public Advertisement one(AdQRO qo) {
        return adMapper.one(qo);
    }

    @Override
    public List<Advertisement> list(AdQRO qo) {
        return adMapper.list(qo);
    }

    @Override
    public PageInfo<Advertisement> page(AdQRO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> adMapper.list(qo));
    }

}
