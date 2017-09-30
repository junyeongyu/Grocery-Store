package com.junyeong.yu.models;

import com.junyeong.yu.core.annotations.Field;

import java.time.LocalDateTime;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * Parent common class of Invoice/InvoiceItem/Product
 */
public abstract class BaseModel {
    @Field(order = 1)
    private int id;
    @Field(order = {2, 5}, group = {"invoice", "invoiceItem"})
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime editDate = LocalDateTime.now();

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public LocalDateTime getEditDate() {
        return editDate;
    }
    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    abstract public String getFileOutput();
    abstract public BaseModel setFileOutput(String line);
}
