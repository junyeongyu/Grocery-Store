package com.junyeong.yu.handler;

import com.junyeong.yu.models.BaseModel;
import com.junyeong.yu.core.BeanUtils;
import com.junyeong.yu.core.XmlUtils;
import com.junyeong.yu.core.annotations.Inject;

import java.util.*;


/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class has responsibility for treating xml file.
 */
//@Bean
public class DataHandlerFileExtXml implements DataHandlerFileExt {

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
                    return baseModelClass.getName() + "s.xml";
                }
            }); //System.out.println(result);

            List<? extends BaseModel> baseModelList = XmlUtils.xmlToBeanList(result, baseModelClass);//System.out.println(baseModelList.size());
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
                        return baseModelList.get(0).getClass().getName() + "s.xml";
                    }
                    throw new RuntimeException("No Object in the List");
                }
                @Override
                public String save() {
                    StringBuffer sb = new StringBuffer();
                    String className = baseModelList.get(0).getClass().getName();
                    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                    //System.out.println(baseModelList.size());
                    for (BaseModel baseModel: baseModelList) {
                        sb.append("<" + className + ">");
                        Map<String, Object> resultMap = BeanUtils.beanToMap(baseModel);
                        Set<Map.Entry<String, Object>> entrySet = resultMap.entrySet();
                        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, Object> entrySetTemp = iterator.next();
                            sb.append(String.format("<%s>%s</%s>", entrySetTemp.getKey(), entrySetTemp.getValue(), entrySetTemp.getKey()));
                        }
                        sb.append("</" + className + ">");
                    }

                    return sb.toString();
                }
            });
        }
    }
}
