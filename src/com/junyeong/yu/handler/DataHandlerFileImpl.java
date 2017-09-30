package com.junyeong.yu.handler;

import com.junyeong.yu.core.annotations.Bean;

import java.io.*;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * Class should have one responsibility and range.
 * File treatment can not be business logic but one of the way of treatment or persistence.
 */
@Bean
public class DataHandlerFileImpl implements DataHandlerFile {

    public Boolean save(DataHandlerFileSave dataHandlerFileSave) {
        boolean ret = true;
        BufferedWriter bw = null;
        try {
            File file = new File(dataHandlerFileSave.getTableName()); // Create a reference to a file

            if (file.exists() == false) {// Does the file exist
                file.createNewFile(); // Does not exist, create a new file
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile()); // Create a writer to send information to the stress
            bw = new BufferedWriter(fw);
            bw.write(dataHandlerFileSave.save()); // write in file -> perform business logic
        } catch (Exception e) {
            throw new RuntimeException(e); // throw wrapped new exception after treating persistence level exception
        } finally {
            // return resource
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception e1){}
            }
        }
        return ret;
    }

    public String read(DataHandlerFileRead dataHandlerFileRead) {
        BufferedReader br = null;
        try {
            File file = new File(dataHandlerFileRead.getTableName()); // handler has information of filename
            StringBuffer sb = new StringBuffer();
            if (file.exists()) {
                br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
                String line = br.readLine();
                while (line != null) {
                    //list.add(dataContextDaoHandlerFileRead.read(line)); // handler contains business logic
                    sb.append(line + "\\");
                    //System.out.println(line);
                    line = br.readLine();
                }
            }
            //System.out.println(sb.length());
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e); // throw wrapped new exception after treating persistence level exception
        } finally {
            // return resource
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e1){}
            }
        }
    }
}