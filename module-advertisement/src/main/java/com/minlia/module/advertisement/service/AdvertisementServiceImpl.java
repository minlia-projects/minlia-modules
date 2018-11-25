package com.minlia.module.advertisement.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.advertisement.bean.domain.Advertisement;
import com.minlia.module.advertisement.bean.qo.AdvertisementQO;
import com.minlia.module.advertisement.bean.qo.AdvertisementsQO;
import com.minlia.module.advertisement.bean.to.AdvertisementCTO;
import com.minlia.module.advertisement.bean.to.AdvertisementUTO;
import com.minlia.module.advertisement.mapper.AdvertisementMapper;
import com.minlia.modules.attachment.constant.AttachmentCode;
import com.minlia.modules.attachment.entity.Attachment;
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
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Autowired
    private AdvertisementsService advertisementsService;

    @Override
    public Advertisement create(AdvertisementCTO cto) {
        long count = advertisementsService.count(AdvertisementsQO.builder().id(cto.getParentId()).build());
        ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);

        Advertisement advertisement = mapper.map(cto,Advertisement.class);
        advertisementMapper.create(advertisement);

        //绑定附件
        if (null != cto.getCoverETag()) {
            attachmentService.bindByAccessKey(cto.getCoverETag(), advertisement.getId().toString(), "AD_COVER");
        }
        return advertisement;
    }

    @Override
    public Advertisement update(AdvertisementUTO uto) {
        Advertisement advertisement = advertisementMapper.queryById(uto.getId());
        ApiAssert.notNull(advertisement, SystemCode.Message.DATA_NOT_EXISTS);

        //绑定附件
        if (StringUtils.isNotBlank(uto.getCoverETag())) {
            attachmentService.bindByAccessKey(uto.getCoverETag(), advertisement.getId().toString(), "AD_COVER");
        }

        mapper.map(uto, advertisement);
        advertisementMapper.update(advertisement);
        return advertisement;
    }

    @Override
    public void delete(Long id) {
        Advertisement advertisement = advertisementMapper.queryById(id);
        ApiAssert.notNull(advertisement, SystemCode.Message.DATA_NOT_EXISTS);
        advertisementMapper.delete(id);
    }

    @Override
    public Advertisement queryById(Long id) {
        return advertisementMapper.queryById(id);
    }

    @Override
    public long count(AdvertisementQO qo) {
        return advertisementMapper.count(qo);
    }

    @Override
    public Advertisement one(AdvertisementQO qo) {
        return advertisementMapper.one(qo);
    }

    @Override
    public List<Advertisement> list(AdvertisementQO qo) {
        return advertisementMapper.list(qo);
    }

    @Override
    public PageInfo<Advertisement> page(AdvertisementQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()->advertisementMapper.list(qo));
    }

}
