package com.junyeong.yu.models;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * Common value should be put here.
 */
public class Common {
    public static float taxRate = 0.13f;
    public static class Database {
        public final static String BASE_PACKAGE = "com.junyeong.yu";
        public final static String DOT = ".";
        public final static String INVOICE = BASE_PACKAGE + DOT + "models.invoices.csv";
        public final static String INVOICE_ITEM = BASE_PACKAGE + DOT + "models.invoiceItems.csv";
        public final static String PRODUCT = BASE_PACKAGE + DOT + "models.products.csv";
    }
}
