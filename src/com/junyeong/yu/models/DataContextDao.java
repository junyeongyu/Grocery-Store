package com.junyeong.yu.models;

import com.junyeong.yu.core.annotations.Bean;
import com.junyeong.yu.core.annotations.Inject;
import com.junyeong.yu.handler.DataHandlerFile;
import com.junyeong.yu.handler.DataHandlerFileExt;

import java.util.*;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * DataContextDao can treat data in file(database).
 */
@Bean
public class DataContextDao {

    @Inject
    private DataHandlerFileExt dataHandlerFileExt;
    public void setDataHandlerFileExt(DataHandlerFileExt dataHandlerFileExt) {
        this.dataHandlerFileExt = dataHandlerFileExt;
    }
    public void setDataHandlerFile(DataHandlerFile dataHandlerFile) {
        this.dataHandlerFileExt.setDataHandlerFile(dataHandlerFile);
    }

    public List<Product> getProductList() {
        Map<Class<? extends BaseModel>, List<? extends BaseModel>> baseModelListMap = new LinkedHashMap<Class<? extends BaseModel>, List<? extends BaseModel>>();
        baseModelListMap.put(Product.class, new ArrayList<Product>());
        return (List<Product>) this.dataHandlerFileExt.read(baseModelListMap).get(Product.class);
    }
    public Product getProduct(int productId) {
        return getProduct(productId, getProductList());
    }
    private Product getProduct(int productId, List<Product> productList) {
        if (productId > 0) {
            for (Product p : productList) {
                if (p.getId() == productId) {
                    return p;
                }
            }
        }
        throw new RuntimeException("Request product can not be found");
    }
    public void addProduct(Product product) {
        List<Product> productList = getProductList();
        productList.add(product);

        List<List<? extends BaseModel>> baseModelListList = new ArrayList<List<? extends BaseModel>>();
        baseModelListList.add(productList);
        this.dataHandlerFileExt.save(baseModelListList);
    }

    public List<Invoice> getInvoiceList() {
        return (List<Invoice>) setInvoiceItemAndProduct(this.dataHandlerFileExt.read(makeBaseModelListMap())).get(Invoice.class);
    }
    private Map<Class<? extends BaseModel>, List<? extends BaseModel>> makeBaseModelListMap() {
        Map<Class<? extends BaseModel>, List<? extends BaseModel>> baseModelListMap = new LinkedHashMap<Class<? extends BaseModel>, List<? extends BaseModel>>();
        baseModelListMap.put(Product.class, new ArrayList<Product>());
        baseModelListMap.put(Invoice.class, new ArrayList<Invoice>());
        baseModelListMap.put(InvoiceItem.class, null);
        return baseModelListMap;
    }
    private Map<Class<? extends BaseModel>, List<? extends BaseModel>> setInvoiceItemAndProduct(Map<Class<? extends BaseModel>, List<? extends BaseModel>> baseModelListMap) {
        List<InvoiceItem> invoiceItemList = (List<InvoiceItem>) baseModelListMap.get(InvoiceItem.class);
        List<Invoice> invoiceList = (List<Invoice>) baseModelListMap.get(Invoice.class);
        List<Product> productList = (List<Product>) baseModelListMap.get(Product.class);
        for (Invoice invoice: invoiceList) {
            for (InvoiceItem invoiceItem: invoiceItemList) {
                invoiceItem.setProduct(getProduct(invoiceItem.getProductId(), productList));
                if (invoice.getId() == invoiceItem.getInvoiceId()) {
                    invoice.getInvoiceItemList().add(invoiceItem);
                }
            }
        }
        return baseModelListMap;
    }
    public void addInvoice(Invoice invoice) {
        List<Invoice> invoiceList = getInvoiceList();
        invoiceList.add(invoice);
        this.dataHandlerFileExt.save(makeBaseModelListList(invoiceList));

    }
    private List<List<? extends BaseModel>> makeBaseModelListList(List<Invoice> invoiceList) {
        List<InvoiceItem> invoiceItemList = new ArrayList<InvoiceItem>();
        for (Invoice invoiceTemp: invoiceList) {
            List<InvoiceItem> invoiceItemListTemp = invoiceTemp.getInvoiceItemList();
            for (InvoiceItem invoiceItem : invoiceItemListTemp) {
                invoiceItemList.add(invoiceItem);
            }
        }

        List<List<? extends BaseModel>> baseModelListList = new ArrayList<List<? extends BaseModel>>();
        //baseModelListList.add(getProductList());
        baseModelListList.add(invoiceList);
        baseModelListList.add(invoiceItemList);
        return baseModelListList;
    }
}
