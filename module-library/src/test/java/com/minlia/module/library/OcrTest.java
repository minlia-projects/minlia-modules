package com.minlia.module.library;

import com.baidu.aip.ocr.AipOcr;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2020/9/11 13:30:27
 */
public class OcrTest {

    //设置APPID/AK/SK
    public static final String APP_ID = "22617954";
    public static final String API_KEY = "RnXvsn52mV4QdaBDYvLV9Who";
    public static final String SECRET_KEY = "raWa9yMTgr733tKPPgtkDQOeS3HxmtqT";

    @Test
    public void gjd() throws JSONException {
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("recognize_granularity", "big");
        options.put("detect_direction", "true");
        options.put("vertexes_location", "true");
        options.put("probability", "true");

        // 参数为本地路径
        String image = "/Users/garen/Downloads/WX20200911-150826@2x.png";
        JSONObject res = client.accurateGeneral(image, options);
        JSONArray jsonArray = res.getJSONArray("words_result");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            String s = jsonArray.getJSONObject(i).getString("words");
            sb.append(s);
        }
        System.out.println(sb.toString());


        // 参数为二进制数组
//        byte[] file = readFile("test.jpg");
//        res = client.basicAccurateGeneral(file, options);
//        System.out.println(res.toString(2));
    }

//    public static void pdfToImages() throws IOException {
//        String path = "/Users/garen/Documents/BMP/data/2019-11-21/resources/2019-11-11/others/1d134336-c4ed-41b9-85d3-0363e4ca1a5c.pdf";
//        PDDocument document = PDDocument.load(new File(path));
//        PDFRenderer pdfRenderer = new PDFRenderer(document);
//        int pageCounter = 0;
//        for (PDPage page : document.getPages()) {
//            // note that the page number parameter is zero based
//            BufferedImage image = pdfRenderer.renderImageWithDPI(pageCounter, 300, ImageType.RGB);
//
//            // suffix in filename will be used as the file format
//            ImageIO.write(image, type, new File(fileAddress + "\\" + filename + "_" + (i + 1) + "." + type));
//        }
//        document.close();
//    }

    @Test
    public void test() throws JSONException {
        String fileAddress = "/Users/garen/Documents/环视技术/test";
        String filename = "pp";
        String type = "jpg";
        //pdf to images
        int pageCount = pdf2png(fileAddress, filename, type);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pageCount; i++) {
            String fileName = fileAddress + "/" + filename + "_" + (i + 1) + "." + type;
            sb.append(gjd(fileName));
            File file = new File(fileName);
            FileUtils.deleteQuietly(file);
        }
        System.out.println(sb.toString());
    }

    /**
     * 转换全部的pdf
     *
     * @param fileAddress 文件地址
     * @param filename    PDF文件名
     * @param type        图片类型
     */
    public static int pdf2png(String fileAddress, String filename, String type) {
        // 将pdf装图片 并且自定义图片得格式大小
        File file = new File(fileAddress + "/" + filename + ".pdf");
        int pageCount = 0;
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300); // Windows native DPI
                ImageIO.write(image, type, new File(fileAddress + "/" + filename + "_" + (i + 1) + "." + type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageCount;
    }

    /**
     * 自由确定起始页和终止页
     *
     * @param fileAddress  文件地址
     * @param filename     pdf文件名
     * @param indexOfStart 开始页  开始转换的页码，从0开始
     * @param indexOfEnd   结束页  停止转换的页码，-1为全部
     * @param type         图片类型
     */
    public static void pdf2png(String fileAddress, String filename, int indexOfStart, int indexOfEnd, String type) {
        // 将pdf装图片 并且自定义图片得格式大小
        File file = new File(fileAddress + "\\" + filename + ".pdf");
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = indexOfStart; i < indexOfEnd; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
                // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
                ImageIO.write(image, type, new File(fileAddress + "\\" + filename + "_" + (i + 1) + "." + type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String gjd(byte[] bytes) throws JSONException {
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("recognize_granularity", "big");
        options.put("detect_direction", "true");
        options.put("vertexes_location", "true");
        options.put("probability", "true");

        // 参数为本地路径
        JSONObject res = client.accurateGeneral(bytes, options);
        JSONArray jsonArray = res.getJSONArray("words_result");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            String s = jsonArray.getJSONObject(i).getString("words");
            sb.append(s);
        }
        return sb.toString();
    }

    public static String gjd(String image) throws JSONException {
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("recognize_granularity", "big");
        options.put("detect_direction", "true");
        options.put("vertexes_location", "true");
        options.put("probability", "true");

        // 参数为本地路径
        JSONObject res = client.accurateGeneral(image, options);
        JSONArray jsonArray = res.getJSONArray("words_result");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            String s = jsonArray.getJSONObject(i).getString("words");
            sb.append(s);
        }
        return sb.toString();
    }

}