/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testloadpackage;

/**
 *
 * @author dboro
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class XLoader extends ClassLoader {
    // карта отображения имен классов на файлы .class, где хранятся их определения
    HashMap mappings;
    XLoader(HashMap mappings) {
        this.mappings = mappings;
    }
    public synchronized Class loadClass(String name) throws ClassNotFoundException {
        try {
            System.out.println("loadClass (" + name + ")");
            // важно! 
            // приоритет отдан именно загрузке с помощью встроенного загрузчика
            if (!mappings.containsKey(name)) {
                return super.findSystemClass(name);
            }
            String fileName = (String) mappings.get(name);
            FileInputStream fin = new FileInputStream(fileName);            
            byte[] bbuf = new byte[(int)(new File(fileName).length())];
            fin.read(bbuf);
            fin.close();
            return defineClass(name, bbuf, 0, bbuf.length);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(e.getMessage(), e);
        }
    }    
}
