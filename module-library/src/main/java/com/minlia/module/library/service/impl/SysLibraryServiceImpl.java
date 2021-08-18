package com.minlia.module.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.attachment.service.AttachmentUploadService;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.data.util.SequenceUtils;
import com.minlia.module.library.bean.SysLibraryOcrVo;
import com.minlia.module.library.bean.SysLibraryQro;
import com.minlia.module.library.config.LibraryConfig;
import com.minlia.module.library.config.OcrConfig;
import com.minlia.module.library.entity.SysLibraryEntity;
import com.minlia.module.library.mapper.SysLibraryMapper;
import com.minlia.module.library.service.SysLibraryService;
import com.minlia.module.library.util.OcrUtils;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.aliyun.oss.builder.PathBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * <p>
 * 文库 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
@Service
@RequiredArgsConstructor
public class SysLibraryServiceImpl extends ServiceImpl<SysLibraryMapper, SysLibraryEntity> implements SysLibraryService {

    private final OcrConfig ocrConfig;
    private final OssService ossService;
    private final LibraryConfig libraryConfig;
    private final AttachmentUploadService attachmentUploadService;

    @Override
    public SysLibraryOcrVo ocr(MultipartFile file) {
        OssFile ossFile = null;
        try {
            ossFile = (OssFile) attachmentUploadService.upload(file).getPayload();
        } catch (Exception e) {
            ApiAssert.state(false, e.getMessage());
        }

        //OCR识别内容
        String content = null;
        try {
            content = OcrUtils.pdf2String(file.getBytes(), ocrConfig.getFilePath());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SysLibraryOcrVo.builder().name(file.getOriginalFilename()).url(ossFile.getUrl()).content(content).build();
    }

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean ocr(File file, String targetPath, String type, String keyword) {
        //OCR识别内容
        String content = null;
        try {
            content = OcrUtils.pdf2String(file, targetPath);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            FileUtils.deleteQuietly(new File(targetPath));
            new File(targetPath).mkdir();
            return false;
        }

        OssFile ossFile = null;
        try {
            String key = "library/" + LocalDate.now().format(DATE_TIME_FORMATTER) + "/" + PathBuilder.uuidNameBuild(file.getName());
            ossFile = this.ossService.upload(file, key);
        } catch (Exception e) {
            return false;
        }

        SysLibraryEntity libraryEntity = SysLibraryEntity.builder()
                .type(type)
                .keyword(keyword)
                .name(FilenameUtils.getBaseName(file.getName()))
                .url(ossFile.getUrl())
                .content(content)
                .build();
        this.create(libraryEntity);
        return true;
    }

    @Override
    public SysLibraryEntity create(SysLibraryEntity entity) {
        if (StringUtils.isBlank(entity.getNumber())) {
            entity.setNumber(this.generatorNumber());
        }
        this.save(entity);
        return entity;
    }

    @Override
    public boolean disable(Long id) {
        SysLibraryEntity libraryEntity = this.getById(id);
        libraryEntity.setDisFlag(!libraryEntity.getDisFlag());
        this.updateById(libraryEntity);
        return libraryEntity.getDisFlag();
    }

    @Override
    public LambdaQueryWrapper builderQueryWrapper(SysLibraryQro qro) {
        LambdaQueryWrapper<SysLibraryEntity> queryWrapper = Wrappers.<SysLibraryEntity>lambdaQuery()
                .select(SysLibraryEntity::getId, SysLibraryEntity::getName, SysLibraryEntity::getUrl, SysLibraryEntity::getSummary, SysLibraryEntity::getCreateBy, SysLibraryEntity::getCreateDate, SysLibraryEntity::getLastModifiedBy, SysLibraryEntity::getLastModifiedDate);
        if (StringUtils.isNotBlank(qro.getNumber())) {
            queryWrapper.eq(SysLibraryEntity::getNumber, qro.getNumber());
        }
        if (StringUtils.isNotBlank(qro.getType())) {
            queryWrapper.eq(SysLibraryEntity::getType, qro.getType());
        }
        if (Objects.nonNull(qro.getDisFlag())) {
            queryWrapper.eq(SysLibraryEntity::getDisFlag, qro.getDisFlag());
        }
        if (StringUtils.isNotBlank(qro.getName())) {
            queryWrapper.like(SysLibraryEntity::getName, qro.getName());
        }
        if (!qro.hasOrder()) {
            queryWrapper.orderByDesc(SysLibraryEntity::getCreateDate);
        }
        return queryWrapper;
    }

    private String generatorNumber() {
        return NumberGenerator.generator(libraryConfig.getNumberPrefix(), Math.toIntExact(SequenceUtils.nextval(libraryConfig.getSequenceName())), libraryConfig.getNumberFullNum());
    }

}