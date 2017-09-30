package com.junyeong.yu.handler;

import com.junyeong.yu.core.annotations.Bean;
import com.junyeong.yu.models.BaseModel;
import com.junyeong.yu.core.annotations.Inject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class has responsibility for treating csv file.
 */
//@Bean
public class DataHandlerFileExtCsv implements DataHandlerFileExt {

    @Inject
    private DataHandlerFile dataHandlerFile;
    public void setDataHandlerFile(DataHandlerFile dataHandlerFile) {
        this.dataHandlerFile = dataHandlerFile;
    }

    @Override
    public Map<Class<? extends BaseModel>, List<? extends BaseModel>> read(Map<Class<? extends BaseModel>, List<? extends BaseModel>> baseModelListMap) {
        // When data are read, DataContextDaoReadHandler are used.
        Iterator<Class<? extends BaseModel>> baseModelIterator = baseModelListMap.keySet().iterator();
        while (baseModelIterator.hasNext()) {
            final Class<? extends BaseModel> baseModelClass = baseModelIterator.next();
            //System.out.println(baseModelClass.getName());
            String result = dataHandlerFile.read(new DataHandlerFileRead() { // describe business logic that can be used in persistence level
                @Override
                public String getTableName() {
                    return baseModelClass.getName() + "s.csv";
                }
            });
            String[] lines = result.split("\\\\");
            List<BaseModel> baseModelList = new ArrayList<BaseModel>();
            for (String line: lines) {
                try {
                    if (line != null && line.equals("") == false) {
                        baseModelList.add(baseModelClass.newInstance().setFileOutput(line));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            baseModelListMap.put(baseModelClass, baseModelList);
        }
        return baseModelListMap;
    }

    @Override
    public void save(List<List<? extends BaseModel>> baseModelListList) {
        // Implementation of saveHandler
        for (final List<? extends BaseModel> baseModelList : baseModelListList) {
            // dataContext object should not know detail way of implementation to store data. (Separation of aspect(role))
            dataHandlerFile.save(new DataHandlerFileSave() { // describe business logic that can be used in persistence level
                @Override
                public String getTableName() {
                    if (baseModelList.size() > 0) {
                        return baseModelList.get(0).getClass().getName() + "s.csv";
                    }
                    throw new RuntimeException("No Object in the List");
                }
                @Override
                public String save() {
                    StringBuffer sb = new StringBuffer();
                    for (BaseModel baseModel: baseModelList) {
                        sb.append(baseModel.getFileOutput() + "\n");
                    }
                    return sb.toString();
                }
            });
        }
    }
}
