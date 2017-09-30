package com.junyeong.yu.core;

import com.junyeong.yu.core.annotations.Bean;
import com.junyeong.yu.core.annotations.Inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class is context initializer to make a bean object.
 *  -> It helps user make beans and follow dependency injection.
 *  -> Even if beans are made by reflection, there is no performance issue since all instances of classes are loaded at only first time.
 */
public class ApplicationContext {

    private Map<Class, Object> beanRegistry = new HashMap<Class, Object>();
    private String basePackage;
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public ApplicationContext(String basePackage) {
        setBasePackage(basePackage);
        initialize();
    }
    private void initialize () {
        registBean();
        injectDependency();
    }
    private void registBean() {
        try {
            Class[] classes = ClassUtils.getClasses(basePackage);
            for (Class c: classes) {
                Annotation[] annotations = c.getDeclaredAnnotations();//System.out.println(c.getName());
                for (Annotation annotation: annotations) {
                    if (Bean.class.getName().equals(annotation.annotationType().getName())) {//System.out.println(c.getName() + " - " + annotation.getClass().getName() + " - " + annotation.annotationType().getName() + " - " + Bean.class.getName());
                        beanRegistry.put(c, c.newInstance());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void injectDependency() {
        injectFields();
    }
    private void injectFields() {
        Set<Map.Entry<Class, Object>> set = beanRegistry.entrySet();
        Iterator<Map.Entry<Class, Object>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            Class c = (Class) entry.getKey();
            Object bean = entry.getValue();

            Field[] fields = c.getDeclaredFields();
            for (Field field: fields) {
                Annotation[] annotations = field.getDeclaredAnnotations();//System.out.println(c.getName() + " - " + field.getName());
                for (Annotation annotation: annotations) {
                    if(Inject.class.getName().equals(annotation.annotationType().getName())) { // find same Inject annotation.
                        injectField(bean, field); //System.out.println(c.getName() + " - " + field.getType().getName() + " - " + field.getName() + " - " + annotation.annotationType().getName()); System.out.println(" -> " + field.getType().isAssignableFrom(DataContextDaoFile.class));
                    }
                }
            }
        }
    }
    private void injectField(Object bean, Field field) {
        Set<Map.Entry<Class, Object>> setTemp = beanRegistry.entrySet();
        Iterator<Map.Entry<Class, Object>> iteratorTemp = setTemp.iterator();
        while (iteratorTemp.hasNext()) {
            Map.Entry entry = iteratorTemp.next();
            Class classTemp = (Class) entry.getKey();
            Object beanTemp = entry.getValue();
            if (field.getType().isAssignableFrom(classTemp)) {
                field.setAccessible(true);
                try {
                    field.set(bean, beanTemp);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Can not assign this field : " + field.getType().getName());
                }
                return;
            }
        }
        throw new RuntimeException("Can not find assignable bean");
    }

    public <T> T getBean(Class<T> c) {
        return (T) beanRegistry.get(c);
    }

    public static void main(String[] args) throws Exception {
        new ApplicationContext("com.junyeong.yu");
    }
}
