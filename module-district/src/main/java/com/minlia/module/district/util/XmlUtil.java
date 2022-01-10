package com.minlia.module.district.util;
/**
 * XMLUtil类
 * 提供xml解析
 */

import com.minlia.module.district.entity.DistrictDto;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * 封装了XML转换成object，object转换成XML的代码
 *
 * @author Steven
 *
 */
public class XmlUtil {
    /**
     * 将对象直接转换成String类型的 XML输出
     *
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    /**
     * 将对象根据路径转换成xml文件
     *
     * @param obj
     * @param path
     * @return
     */
    public static void convertToXml(Object obj, String path) {
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            // 创建输出流
            FileWriter fw = null;
            try {
                fw = new FileWriter(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            marshaller.marshal(obj, fw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * 将String类型的xml转换成对象
     */
    public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    @SuppressWarnings("unchecked")
    /**
     * 将file类型的xml转换成对象
     */
    public static Object convertXmlFileToObject(Class clazz, String xmlPath) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Reader re = null;
            try {
                re = new FileReader(xmlPath);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            xmlObject = unmarshaller.unmarshal(re);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    public void setXml(Object object, String name) {
        //User user = new User(1, "Steven", "@sun123", new Date(), 1000.0);
        String path = "D:\\user.xml";
        System.out.println("---将对象转换成File类型的xml Start---");
        XmlUtil.convertToXml(object, path);
        System.out.println("---将对象转换成File类型的xml End---");
    }

    public Object getXml(Class clazz, Object object, String name) {
        String path = name;
        object = (Object) XmlUtil.convertXmlStrToObject(clazz, path);
        return object;
    }

    /**
     * 读取xml 将xml转换成对象
     * @param request
     * @param response
     * @param object
     * @return
     */

    public static Object getDocument(HttpServletRequest request, Object object) {
        //SAXReader saxReader = null;
        //String xmlStr = null;
        //Document doc = null;
        //XmlUtil xml = new XmlUtil();
        //try {
        //    saxReader = new SAXReader();
        //    doc = saxReader.read(request.getInputStream());
        //    xmlStr = doc.asXML();
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        //return xml.getXml(object.getClass(), object, xmlStr);
        return null;
    }

    /**
     *
     * 将List转换成XML
     * @param obj
     * @param load
     * @return
     * @throws JAXBException
     */
    public static String beanToXml(Object obj, Class<?> load) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(load);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return writer.toString();
    }

    public static void main(String[] args) {
        Object districtDto = convertXmlFileToObject(DistrictDto.class, "/Users/garen/Documents/cameslc/区域地址/en.xml");
        DistrictDto dto = (DistrictDto) districtDto;
        System.out.println(districtDto);
    }

}