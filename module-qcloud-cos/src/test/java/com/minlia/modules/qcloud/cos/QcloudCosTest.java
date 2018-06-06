//package com.minlia.modules.qcloud.cos;
//
//import org.apache.commons.io.IOUtils;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.activation.DataSource;
//
//@RunWith(SpringJUnit4ClassRunner.class)
////@SpringApplicationConfiguration(classes = OssTestApplication.class)
//@ContextConfiguration(classes = OssTestApplication.class)
//public class QcloudCosTest {
//    @Autowired
//    private ApplicationContext context;
//
//    @Test
//    public void clientExists() {
//        Assert.assertEquals(1, this.context.getBeanNamesForType(OSSClient.class).length);
//        Assert.assertEquals(1, this.context.getBeanNamesForType(FileStorageService.class).length);
//    }
//
//    @Test
//    public void testSave() throws Exception {
//        byte[] content = IOUtils.toByteArray(this.getClass().getResourceAsStream("/1.jpg"));
//        ByteArrayDataSource bads = new ByteArrayDataSource(content, "image/jpeg");
//        bads.setName("1.jpg");
//        FileStorageService fileStorageService = context.getBean(FileStorageService.class);
//        String newFileName = fileStorageService.save(bads);
//        DataSource fileDS = fileStorageService.get(newFileName);
//        Assert.assertNotNull(fileDS);
//        fileStorageService.delete(newFileName);
//    }
//
//}
