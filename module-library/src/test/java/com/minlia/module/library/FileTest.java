package com.minlia.module.library;

import com.minlia.module.library.util.OcrUtils;
import com.minlia.modules.aliyun.oss.builder.PathBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2020/9/12 11:32:37
 */
public class FileTest {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) throws JSONException, IOException {
        String rootPath = "/Users/garen/Documents/环视技术/文库/Resource-to-20200905";
        String pathname = "D:\\";
        File dir = new File(rootPath);
        List<File> fileList = (List<File>) FileUtils.listFiles(dir, new String[]{"pdf"}, true);//列出该目录下的所有doc文件，递归（扩展名不必带.doc）
//        List<File> fileList = (List<File>)FileUtils.listFiles(dir,null,true);//列出该目录下的所有文件，递归
//        List<File> fileList = (List<File>)FileUtils.listFiles(dir,null,false);//列出该目录下的所有文件，不递归
//        fileList.stream().forEach(file -> System.out.println(file.getName()));

//        fileList.stream().forEach(file -> System.out.println(file.getAbsolutePath()));
        //以上只输出文件路径，不输出文件夹路径，也不输出空文件夹路径


//        fileList.stream().forEach(file -> {
//            String ossPath = "library/" + LocalDate.now().format(DATE_TIME_FORMATTER) + "/" + PathBuilder.uuidNameBuild(file.getName());
////            System.out.println(ossPath);
//            System.out.println(FilenameUtils.getBaseName(file.getName()));
//            System.out.println(FilenameUtils.getExtension(file.getName()));
//            System.out.println(file.getName());
//        });

//        String content = OcrUtils.pdf2String(new File("/Users/garen/Documents/环视技术/test/pp.pdf"), "/Users/garen/Documents/环视技术/test/");
//        System.out.println(content);

//        FileUtils.deleteDirectory(new File("/Users/garen/Documents/环视技术/test"));
        FileUtils.deleteQuietly(new File("/Users/garen/Documents/环视技术/test"));
        new File("/Users/garen/Documents/环视技术/test").mkdir();
    }

}
