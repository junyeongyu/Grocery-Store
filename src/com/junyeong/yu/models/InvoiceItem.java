package com.junyeong.yu.models;

import com.junyeong.yu.core.annotations.Data;
import com.junyeong.yu.core.annotations.Field;

import java.time.LocalDateTime;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * InvoiceItem is detail information of Invoice.
 */
@Data(group = "invoiceItem")
public class InvoiceItem extends BaseModel {
    @Field(order = 2, group = "invoiceItem") // It is designed to make sure field order of csv file, and resolve target of loading or saving fields.
    private int invoiceId;
    private Invoice invoice; // TODO: see carefully
    @Field(order = 3, group = "invoiceItem")
    private int productId;
    private Product product;
    @Field(order = 4, group = "invoiceItem")
    private int quantity;

    public InvoiceItem() {}
    public InvoiceItem(int id, int invoiceId, Product product) {
        setId(id);
        setInvoiceId(invoiceId);
        setProduct(product);
    }

    // toString() is method of Object. Through overriding this method, own customized method can be made.
    @Override // In order to avoid confusing, putting this annotation is important.
    public String toString() {
        return "Product Name : " + this.product.getName() + ", Quantity : " + getQuantity() + ", SubTotal : " + getSubTotal() + ", Taxes : " + getTaxes() + ", Total : " + getTotal();
    }
    @Override
    public String getFileOutput() {
        return String.format("%d,%d,%d,%d,%s", getId(), getInvoiceId(), getProductId(), getQuantity(), getCreateDate());
    }
    @Override
    public InvoiceItem setFileOutput(String line) {
        String[] results = line.split(",");
        this.setId(Integer.parseInt(results[0]));
        this.setInvoiceId(Integer.parseInt(results[1]));
        this.setProductId(Integer.parseInt(results[2]));
        this.setQuantity(Integer.parseInt(results[3]));
        this.setCreateDate(LocalDateTime.parse(results[4]));
        return this;
    }

    public float getSubTotal() {
        return getQuantity() * getProduct().getPrice();
    }
    public float getTaxes() {
        float subTotal = getSubTotal();
        if (subTotal > 0) {
            return subTotal * Common.taxRate;
        }
        return 0;
    }
    public float getTotal() {
        return getSubTotal() + getTaxes();
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.productId = product.getId();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
