package com.junyeong.yu.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This is xml utils in order for xml to be changed as object (unmarshalling)
 */
public class XmlUtils {
    private static String XML_START = "<?xml";
    private static String XML_END = "?>";

    public static <T> List<T> xmlToBeanList(String xml, Class<T> c) {
        try {
            String className = c.getName();
            if (xml == null || xml.equals("")) {
                return new ArrayList<T>();
            } else if ((xml.contains(XML_START) && xml.contains(XML_END) & xml.indexOf(XML_START) < xml.indexOf(XML_END)) == false) {
                throw new RuntimeException("It is not xml document.");
            }

            List<T> list = new ArrayList<T>(); // Pattern.compile("<([^>]*)>([^<]*)</(.*)>");

            int startPointOfBean = 0;
            while (true) {//System.out.println("startPointOfBean : " + startPointOfBean);
                String startTag = "<" + className + ">";
                String endTag = "</" + className + ">";

                startPointOfBean = xml.indexOf(startTag, startPointOfBean);
                if (startPointOfBean == -1) {
                    break;
                }
                int endPointOfBean = xml.indexOf(endTag, startPointOfBean);
                int startPointOfContent = startPointOfBean + startTag.length();
                String content = xml.substring(startPointOfContent, endPointOfBean);
                startPointOfBean = endPointOfBean + endTag.length(); //System.out.println(xml.length() + ":" + content);

                T bean = c.newInstance();
                int setCount  = setBean(bean, content); //System.out.println("setCount : " + setCount);
                if (setCount > 0) {
                    list.add(bean);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private static <T> int setBean(T bean, String content) throws InstantiationException, IllegalAccessException {
        Class c = bean.getClass();
        int startPointOfField = 0;
        startPointOfField = content.indexOf("<", startPointOfField);
        int setCount = 0;
        while (startPointOfField != -1) {//System.out.println(content.length() + ":" + startPointOfField);
            int startPointOfFieldClose = content.indexOf(">", startPointOfField);
            int startPointOfBean = content.indexOf("</", startPointOfFieldClose);
            String fieldName = content.substring(startPointOfField + 1, startPointOfFieldClose);
            String value = content.substring(startPointOfFieldClose + 1, startPointOfBean);
            startPointOfField = content.indexOf("<", startPointOfBean + fieldName.length() + "</>".length());//System.out.println(startPointOfField); //System.out.println(fieldName);
            Field field = ClassUtils.getDeclaredField(c, fieldName);
            field.setAccessible(true);
            ClassUtils.setField(bean, field, value);
            setCount++;
        }
        return setCount;
    }

/*    public static void main(String[] args) throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><com.junyeong.yu.models.Product><id>1</id><name>Apple</name><price>1.15</price><taxable>true</taxable></com.junyeong.yu.models.Product>";
        xml += "<com.junyeong.yu.models.Product><id>11</id><name>Apple11</name><price>11.15</price><taxable>false</taxable></com.junyeong.yu.models.Product>";
        xmlToBeanList(xml, Product.class);
    }
*/
}