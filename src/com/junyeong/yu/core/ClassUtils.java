package com.junyeong.yu.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This is class utils in order to look for packages
 * reference site [No License] : http://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
 */
public class ClassUtils {

    // Scans all classes that are parts of package and subpackages.
    public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    // Look for all classes in base package.
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static Field getDeclaredField(Class c, String fieldName) {
        try {
            if (c == null) {
                throw new RuntimeException("No field : " + fieldName);
            }
            Field[] fields = c.getDeclaredFields();
            boolean hasField = false;
            for (Field field: fields) {
                //System.out.println(field.getName() + ":" + fieldName);
                if (field.getName().equals(fieldName)) {
                    hasField = true;
                    break;
                }
            }
            if (hasField) {
                return c.getDeclaredField(fieldName);
            } else {
                return getDeclaredField(c.getSuperclass(), fieldName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void setField(T bean, Field field, String value) {
        try {
            String type = field.getType().getName();
            //System.out.format("field : %s, type : %s, value : %s\n", field.getName(), type, field.get(bean));
            if (type.equals("int")) {
                field.set(bean, Integer.valueOf(value));
            } else if (type.equals("float")) {
                field.set(bean, Float.valueOf(value));
            } else if (type.equals("boolean")) {
                field.set(bean, Boolean.valueOf(value));
            } else if (type.equals("java.time.LocalDateTime")) {
                field.set(bean, LocalDateTime.parse(value));
            } else {
                //System.out.println(type);
                field.set(bean, value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}