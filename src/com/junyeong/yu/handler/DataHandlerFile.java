package com.junyeong.yu.handler;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * There are many way to access resources like file, database and remote storage.
 * Thus, it is good way to provide abstraction level as an interface.
 */
public interface DataHandlerFile {
    public Boolean save(DataHandlerFileSave dataHandlerFileSave);
    public String read(DataHandlerFileRead dataHandlerFileRead);
}
