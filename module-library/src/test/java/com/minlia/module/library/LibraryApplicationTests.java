package com.minlia.module.library;

import com.minlia.module.library.service.SysLibraryService;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private SysLibraryService sysLibraryService;

    @Test
    public void test() {
        String rootPath = "/Users/garen/Documents/环视技术/文库/Resource-to-20200905";
        String pathname = "D:\\";
        File dir = new File(rootPath);
        List<File> fileList = (List<File>) FileUtils.listFiles(dir, new String[]{"pdf"}, true);//列出该目录下的所有doc文件，递归（扩展名不必带.doc）
        fileList.stream().forEach(file -> {
//            String ossPath = "library/" + LocalDate.now().format(DATE_TIME_FORMATTER) + "/" + PathBuilder.uuidNameBuild(file.getName());
//            System.out.println(ossPath);
            boolean bool = sysLibraryService.ocr(file, "/Users/garen/Documents/环视技术/test/", "defaultt", "defaultt");
            if (bool) {
                //删除文件
                FileUtils.deleteQuietly(file);
            }
        });

    }

}