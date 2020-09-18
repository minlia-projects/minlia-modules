package com.minlia.module.library.util;

import com.baidu.aip.ocr.AipOcr;
import com.minlia.cloud.utils.ApiAssert;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class OcrUtils {

    //设置APPID/AK/SK
//    public static final String APP_ID = "16931843";
//    public static final String API_KEY = "yKkQ9gtsxGGALysuBq2NalrM";
//    public static final String SECRET_KEY = "Mb7MCpAG9SnXAEmbG0hZfDZcvSa0pnOy";

    public static final String APP_ID = "22628478";
    public static final String API_KEY = "6kvHwIqiFUfGfdTHm8rpeGPx";
    public static final String SECRET_KEY = "tjtNoWsK1GPWEgSz5RUQ3GFPtniNQ0BE";

    static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    public static String pdf2String(byte[] bytes) throws JSONException {
        String fileAddress = "/Users/garen/Documents/环视技术/test";
        String filename = "pp";
        String type = "jpg";
        //pdf to images
//        int pageCount = pdf2png(fileAddress, filename, type);
        int pageCount = pdf2png(bytes, fileAddress, filename, type);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pageCount; i++) {
            String fileName = fileAddress + "/" + filename + "_" + (i) + "." + type;
            sb.append(gjd(fileName));
            File file = new File(fileName);
            FileUtils.deleteQuietly(file);
        }
        return sb.toString();
    }

    public static String pdf2String(String filePath, String fileName, String fileType) throws JSONException {
        //pdf to images
        int pageCount = pdf2png(filePath, fileName, fileType);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pageCount; i++) {
            String fileAddress = filePath + "/" + fileName + "_" + (i) + "." + fileType;
            sb.append(gjd(fileAddress));
            File file = new File(fileAddress);
            FileUtils.deleteQuietly(file);
        }
        return sb.toString();
    }

    /**
     * @param file
     * @param targetPath
     * @return
     * @throws JSONException
     */
    public static String pdf2String(File file, String targetPath) throws JSONException {
        //pdf to images
        int pageCount = pdf2png(file, targetPath, "jpg");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pageCount; i++) {
            String fileAddress = targetPath + FilenameUtils.getBaseName(file.getName()) + "_" + i + "." + "jpg";
            sb.append(gjd(fileAddress));
            File targetFile = new File(fileAddress);
            FileUtils.deleteQuietly(targetFile);
        }
        return sb.toString();
    }

    /**
     * @param file
     * @param targetPath
     * @param type
     * @return
     */
    public static int pdf2png(File file, String targetPath, String type) {
        // 将pdf装图片 并且自定义图片得格式大小
        int pageCount = 0;
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300); // Windows native DPI
                ImageIO.write(image, type, new File(targetPath + FilenameUtils.getBaseName(file.getName()) + "_" + i + "." + type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageCount;
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

    public static int pdf2png(byte[] bytes, String fileAddress, String filename, String type) {
        // 将pdf装图片 并且自定义图片得格式大小
        int pageCount = 0;
        try {
            PDDocument doc = PDDocument.load(bytes);
            PDFRenderer renderer = new PDFRenderer(doc);
            pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300); // Windows native DPI
                ImageIO.write(image, type, new File(fileAddress + "/" + filename + "_" + (i) + "." + type));
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
        options.put("detect_direction", "true");
        options.put("probability", "true");


        StringBuilder sb = new StringBuilder();

        // 参数为本地路径
        JSONObject res = client.basicAccurateGeneral(image, options);
        if (!res.isNull("error_code") && res.getInt("error_code") != 282000) {
            ApiAssert.state(false, res.getString("error_msg"));
        }

        if (!res.isNull("words_result")) {
            JSONArray jsonArray = res.getJSONArray("words_result");
            if (null != jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (!jsonArray.getJSONObject(i).isNull("words")) {
                        String s = jsonArray.getJSONObject(i).getString("words");
                        if (StringUtils.isNotBlank(s)) {
                            sb.append(s);
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

}