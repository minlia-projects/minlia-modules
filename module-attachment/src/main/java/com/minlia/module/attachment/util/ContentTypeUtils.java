package com.minlia.module.attachment.util;

import com.google.common.collect.Lists;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by garen on 2018/6/7.
 */
public class ContentTypeUtils {

    private static List<String> imgageContentTypes = Lists.newArrayList("image/jpeg","image/png","image/jpg","image/gif","image/gif","image/x-icon","image/tiff","timage/fax");

    private static List<String> imgageExtensions = Lists.newArrayList("jpeg","png","jpg","gif","bmp");

    public static boolean isImage(MultipartFile file) {
        return imgageContentTypes.contains(file.getContentType());
    }

    public static boolean isImage(File file) {
        return imgageExtensions.contains(getExtension(file.getName()));
    }

    public static String getExtension(String fileName){
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if(lastIndexOfDot < 0){
            return "";//没有拓展名
        }
        String extension = fileName.substring(lastIndexOfDot+1);
        return extension;
    }

}
