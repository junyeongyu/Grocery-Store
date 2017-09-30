package com.junyeong.yu.models;

import com.junyeong.yu.core.annotations.Bean;
import com.junyeong.yu.core.annotations.Inject;
import com.junyeong.yu.handler.DataHandlerFile;
import com.junyeong.yu.handler.DataHandlerFileExt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * DataContext can treat business logic.
 */
@Bean
public class DataContext {

    @Inject
    private DataContextDao dataContextDao;
    public void setDataContextDao(DataContextDao dataContextDao) {
        this.dataContextDao = dataContextDao;
    }
    public void setDataHandlerFileExt(DataHandlerFileExt dataHandlerFileExt) {
        dataContextDao.setDataHandlerFileExt(dataHandlerFileExt);
    }
    public void setDataContextDaoHandler(DataHandlerFile dataHandlerFile) {
        this.dataContextDao.setDataHandlerFile(dataHandlerFile);
    }

    public void initialize() {

        List<Product> productList = dataContextDao.getProductList();
        int productListSize = productList == null ? 0 : productList.size();
        if (productListSize == 0) {
            productList = new ArrayList<Product>();
            dataContextDao.addProduct(new Product(1, "Apple", 1.15f));
            dataContextDao.addProduct(new Product(2, "Broccoli", 5.50f));
            dataContextDao.addProduct(new Product(3, "Cucumber", 1.00f));
            dataContextDao.addProduct(new Product(4, "Banana", 0.5f));

        }
    }

    public Product getProduct(int productId) {
        return dataContextDao.getProduct(productId);
    }

    public List<Product> getProductList() {
        return dataContextDao.getProductList();
    }
    public List<Invoice> getInvoiceList() {
        return dataContextDao.getInvoiceList();
    }
    public void addInvoice(Invoice invoice) {
        dataContextDao.addInvoice(invoice);
    }

    public Invoice allocateInvoiceItem(Invoice invoice, InvoiceItem invoiceItem) {
        if (invoice == null) { // new invoice
            invoice = new Invoice(dataContextDao.getInvoiceList().size() + 1);
            //dataContextDao.getInvoiceList().add(invoice);
        }

        invoiceItem.setInvoiceId(invoice.getId());
        invoiceItem.setId(invoice.getInvoiceItemList().size() + getInvoiceItemListSize() + 1);
        invoice.addInvoiceItem(invoiceItem);

        return invoice;
    }

    public String toSummaryString() {
        String ret = String.format("Products: %d, Invoices: %d, Total Items Sold: %d", dataContextDao.getProductList().size(), dataContextDao.getInvoiceList().size(), getInvoiceItemListSize());
        return ret;
    }
    public String toSummaryString(LocalDateTime localDateTime) {
        int invoiceListTodaySize = 0;
        int invoiceItemListTodaySize = 0;
        for (Invoice invoice : dataContextDao.getInvoiceList()) {
            LocalDateTime invoiceDateTime = invoice.getCreateDate();
            //System.out.format("year : %d, month : %d, day : %d\n", invoiceDateTime.getYear(), invoiceDateTime.getMonthValue(), invoiceDateTime.getDayOfMonth());
            if (invoiceDateTime.getYear() == localDateTime.getYear() &&
                    invoiceDateTime.getMonthValue() == localDateTime.getMonthValue() &&
                    invoiceDateTime.getDayOfMonth() == localDateTime.getDayOfMonth()) {
                invoiceListTodaySize++;
            }
            for (InvoiceItem invoiceItem : invoice.getInvoiceItemList()) {
                LocalDateTime invoiceItemDateTime = invoiceItem.getCreateDate();
                if (invoiceItemDateTime.getYear() == localDateTime.getYear() &&
                        invoiceItemDateTime.getMonthValue() == localDateTime.getMonthValue() &&
                        invoiceItemDateTime.getDayOfMonth() == localDateTime.getDayOfMonth()) {
                    invoiceItemListTodaySize++;
                }
            }
        }

        String ret = String.format("Products: %d, Invoices: %d, Total Items Sold: %d", dataContextDao.getProductList().size(), invoiceListTodaySize, invoiceItemListTodaySize);
        return ret;
    }

    private int getInvoiceItemListSize() {
        int i = 0;
        for (Invoice invoice: dataContextDao.getInvoiceList()) {
            i += invoice.getInvoiceItemList().size();
        }
        return i;
    }

    public void addProduct(Product product) {
        dataContextDao.getProductList().add(product);
    }

    public void subTotal() {
        for (Invoice invoice: dataContextDao.getInvoiceList()) {
            System.out.println("subTotal : " + invoice.subTotal());
        }
    }
    public void taxes() {
        for (Invoice invoice: dataContextDao.getInvoiceList()) {
            System.out.println("taxes : " + invoice.totalTax());
        }
    }
    public void total() {
        for (Invoice invoice: dataContextDao.getInvoiceList()) {
            System.out.println("total : " + invoice.total());
        }
    }
}