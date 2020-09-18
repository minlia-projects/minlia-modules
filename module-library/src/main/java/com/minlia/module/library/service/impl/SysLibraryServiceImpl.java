package com.minlia.module.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.attachment.service.AttachmentUploadService;
import com.minlia.module.library.entity.SysLibraryEntity;
import com.minlia.module.library.mapper.SysLibraryMapper;
import com.minlia.module.library.service.SysLibraryService;
import com.minlia.module.library.util.OcrUtils;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.aliyun.oss.builder.PathBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 文库 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
@Service
public class SysLibraryServiceImpl extends ServiceImpl<SysLibraryMapper, SysLibraryEntity> implements SysLibraryService {

    @Autowired
    private OssService ossService;
    private final AttachmentUploadService attachmentUploadService;

    public SysLibraryServiceImpl(AttachmentUploadService attachmentUploadService) {
        this.attachmentUploadService = attachmentUploadService;
    }

    @Override
    public SysLibraryEntity upload(MultipartFile file, String type, String keyword) {
        OssFile ossFile = null;
        try {
            ossFile = (OssFile) attachmentUploadService.upload(file).getPayload();
        } catch (Exception e) {
            ApiAssert.state(false, e.getMessage());
        }

        //OCR识别内容
        String content = null;
        try {
            content = OcrUtils.pdf2String(file.getBytes());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SysLibraryEntity libraryEntity = SysLibraryEntity.builder()
                .type(type)
                .keyword(keyword)
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .url(ossFile.getUrl())
                .accessKey(ossFile.geteTag())
                .content(content)
                .build();
        this.save(libraryEntity);
        return libraryEntity;
    }

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public boolean upload(File file, String targetPath, String type, String keyword) {
        //OCR识别内容
        String content = null;
        try {
            content = OcrUtils.pdf2String(file, targetPath);
        } catch (JSONException e) {
//            e.printStackTrace();
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
//            ApiAssert.state(false, e.getMessage());
            return false;
        }

        SysLibraryEntity libraryEntity = SysLibraryEntity.builder()
                .type(type)
                .keyword(keyword)
                .fileName(FilenameUtils.getBaseName(file.getName()))
                .fileType(FilenameUtils.getExtension(file.getName()))
                .fileSize(file.length())
                .url(ossFile.getUrl())
                .accessKey(ossFile.geteTag())
                .content(content)
                .build();
        this.save(libraryEntity);
        return true;
    }

}