package com.junyeong.yu.models;

import com.junyeong.yu.core.annotations.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * Invoice is basic unit for individual transaction.
 */
@Data(group = "invoice") // This annotation is target of reflection for csv and xml procedure.
public class Invoice extends BaseModel {
    private List<InvoiceItem> invoiceItemList = new ArrayList<InvoiceItem>();

    public Invoice() {}
    public Invoice(int id) {
        setId(id);
    }

    public void addInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItemList.add(invoiceItem);
    }
    public float subTotal() {
        float ret = 0.00f;
        for (InvoiceItem invoiceItem: this.invoiceItemList) {
            ret += invoiceItem.getSubTotal();
        }
        return ret;
    }
    public float totalTax() {
        /*float subTotal = getSubTotal();
        if (subTotal > 0) {
            return subTotal * Common.taxRate;
        }*/
        float ret = 0.00f;

        for (InvoiceItem invoiceItem: this.invoiceItemList) {
            ret += invoiceItem.getTaxes();
        }
        return ret;
    }
    public float total() {
        float ret = 0.00f;
        for (InvoiceItem invoiceItem: this.invoiceItemList) {
            ret += invoiceItem.getTotal();
        }
        return ret;
    }

    public String toSummaryString() {
        return "InvoiceId : " + getId() + ", Number of items : " + this.invoiceItemList.size() + ", subTotal : " + subTotal() + ", Taxes : " + totalTax() + ", Total : " + total();
    }
    public String toDetailString() {
        StringBuffer sb = new StringBuffer();
        int i = 1;
        for (InvoiceItem invoiceItem : this.invoiceItemList) {
            sb.append(String.format("Item %d - %s\n", i, invoiceItem.toString()));
            i++;
        }
        return sb.toString();
    }
    @Override
    public String getFileOutput() {
        return String.format("%d,%s", getId(), getCreateDate());
    }
    @Override
    public Invoice setFileOutput(String line) {
        String[] results = line.split(",");
        this.setId(Integer.parseInt(results[0]));
        this.setCreateDate(LocalDateTime.parse(results[1]));
        return this;
    }

    public List<InvoiceItem> getInvoiceItemList() {
        return this.invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItem> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
    }
}
