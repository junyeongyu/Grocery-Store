package com.junyeong.yu.core;

import com.junyeong.yu.core.annotations.Data;
import com.junyeong.yu.sort.factory.SortingFactory;
import com.junyeong.yu.sort.sortType.SortingComparable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This is bean utils in order to marshal and unmarshal data
 */
public class BeanUtils {
    private static class FieldInformation implements SortingComparable<FieldInformation> {
        private String fieldName;
        private Object value;
        private int order;

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }
        public String getFieldName() {
            return fieldName;
        }
        public void setValue(Object value) {
            this.value = value;
        }
        public Object getValue() {
            return value;
        }
        public void setOrder(int order) {
            this.order = order;
        }
        public int getOrder() {
            return order;
        }

        @Override
        public int compareTo(FieldInformation target) {
            return getOrder() - target.getOrder();
        }
    }

    public static Map<String, Object> beanToMap (Object bean/*, String groupName*/) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Class c = bean.getClass();
        List<FieldInformation> fieldInformationList = new ArrayList<FieldInformation>();
        String groupName = bean.getClass().getAnnotation(Data.class).group();
        while (c != null) {
            c = getFieldList(c, groupName, bean, fieldInformationList);
        }

        new SortingFactory().bubble().sort(fieldInformationList);

        for (FieldInformation fieldInformation: fieldInformationList) {
            System.out.println(fieldInformation.getOrder() + fieldInformation.getFieldName() + fieldInformation.getValue());
            resultMap.put(fieldInformation.getFieldName(), fieldInformation.getValue());
        }

        return resultMap;
    }
    private static Class getFieldList(Class c, String groupName, Object bean, List<FieldInformation> fieldInformationList) {
        try {
            Field[] fields = c.getDeclaredFields();
            for (Field field: fields) {
                //System.out.println(field.getName());
                field.setAccessible(true);
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation annotation: annotations) {
                    if (annotation.annotationType().getName().equals(com.junyeong.yu.core.annotations.Field.class.getName())) {
                        //System.out.println(field.getName() + ":" + annotation.annotationType().getName() + ":" + annotation.annotationType().getDeclaredMethods().length);
                        Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
                        com.junyeong.yu.core.annotations.Field annotationField = field.getAnnotation(com.junyeong.yu.core.annotations.Field.class);
                        //System.out.println(":" + annotationField.order().length + " : " + annotationField.group().length);
                        int[] orders = annotationField.order();
                        String[] groups = annotationField.group();
                        if (orders.length != groups.length) {
                            throw new RuntimeException("Length of order array and group array is different.");
                        }
                        FieldInformation fieldInformation = new FieldInformation();
                        fieldInformation.setFieldName(field.getName());
                        fieldInformation.setValue(field.get(bean));
                        if (groups.length == 1 && "".equals(groups[0])) {
                            fieldInformation.setOrder(orders[0]);
                            fieldInformationList.add(fieldInformation);
                        } else {
                            for (int i = 0; i < groups.length; i++) {
                                if (groups[i].equals(groupName)) {
                                    //System.out.println(groups[i]);
                                    fieldInformation.setOrder(orders[i]);
                                    fieldInformationList.add(fieldInformation);
                                    break;
                                }
                            }
                        }

                        //System.out.println(fieldInformation.getFieldName() + ":" + fieldInformation.getValue() + ":" + fieldInformation.getOrder());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return c.getSuperclass();
    }

/*    public static void main(String[] args) throws Exception {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(3);
        invoiceItem.setProductId(33);
        invoiceItem.setQuantity(333);
        invoiceItem.setId(3333);
        BeanUtils.beanToMap(invoiceItem);//, "invoiceItem");
    }
*/
}
