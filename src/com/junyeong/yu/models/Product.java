package com.junyeong.yu.models;

import com.junyeong.yu.core.annotations.Data;
import com.junyeong.yu.core.annotations.Field;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * Product class contains information of id, price, name and being taxable.
 */
@Data(group = "product")
public class Product extends BaseModel {

    @Field(order = 3, group = "product")
    private float price;
    @Field(order = 2, group = "product")
    private String name;
    @Field(order = 4, group = "product")
    private boolean taxable;

    public Product() {
        setTaxable(true);
    }

    public Product(int id, String name) {
        this();
        setId(id);
        this.name = name;
    }

    public Product(int id, String name, float price) {
        this(id, name);
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    @Override
    public String toString() {
        return "Product Id : " + getId() + ", Product Name : " + getName() + ", Price : " + getPrice() + ", taxable : " + isTaxable();
    }
    @Override
    public String getFileOutput() {
        return String.format("%d,%s,%f,%s", getId(), getName(), getPrice(), isTaxable());
    }
    @Override
    public Product setFileOutput(String line) {
        String[] results = line.split(",");
        this.setId(Integer.parseInt(results[0]));
        this.setName(results[1]);
        this.setPrice(Float.parseFloat(results[2]));
        this.setTaxable(Boolean.parseBoolean(results[3]));
        return this;
    }
}
