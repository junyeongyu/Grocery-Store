package com.junyeong.yu.handler;

import com.junyeong.yu.models.BaseModel;

import java.util.List;
import java.util.Map;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * It is handler to read and save the file
 */
public interface DataHandlerFileExt {
    public Map<Class<? extends BaseModel>, List<? extends BaseModel>> read(Map<Class<? extends BaseModel>, List<? extends BaseModel>> baseModelListMap);
    public void save(List<List<? extends BaseModel>> baseModelListList);
    public void setDataHandlerFile(DataHandlerFile dataHandlerFile);
}
