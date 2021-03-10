package com.minlia.module.library.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.cloud.code.Code;
import com.minlia.module.library.bean.SysLibraryOcrVo;
import com.minlia.module.library.bean.SysLibraryQro;
import com.minlia.module.library.entity.SysLibraryEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * <p>
 * 文库 服务类
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
public interface SysLibraryService extends IService<SysLibraryEntity> {

    SysLibraryOcrVo ocr(MultipartFile file);

    boolean ocr(File file, String targetPath, String type, String keyword);

    SysLibraryEntity create(SysLibraryEntity entity);

    boolean disable(Long id);

    LambdaQueryWrapper builderQueryWrapper(SysLibraryQro qro);

}