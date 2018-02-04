/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import utils.JarFileFilter;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

/**
 * Модуль загрузки 
 * 
 * 
 * @author d.borodin
 */

public class PluginLoader {
    //Массивы файлов, JAR-файлов и классов
    ArrayList<File> jarFileList = new ArrayList();
    ArrayList<JarFile> JarList = new ArrayList();
    ArrayList<String> ClassList = new ArrayList();
    
    public void fillLists (String path){
       try {
            //получаем путь к папке плагинов
            File PluginDir = new File(path);
            //список файлов в папке с разрешением .jar
            File[] jFiles = PluginDir.listFiles(new JarFileFilter());
            //заполняем массивы файлами
            for (File jarFile : jFiles) {
                JarFile jf = new JarFile(jarFile);
                //System.out.println(jarFile.getName());
                jarFileList.add(jarFile);
                JarList.add(jf);
            }
            //debug
            System.out.println("Файлы из папки "+path+" с расширением .jar:");
            System.out.println(jarFileList);
            /*System.out.println("JAR-файлы:");
            System.out.println(JarList);*/
            //заполняем массив классов (имен классов)
            for (JarFile jarFile : JarList){
                jarFile.stream().forEach(jc -> {
                    if (jc.getName().endsWith(".class")) {
                    ClassList.add(jc.getName().replace("/", ".").replace(".class", ""));
                    }
                });
            }
            //debug
            System.out.println("Классы:");
            System.out.println(ClassList);
            
        }catch(Exception e) {
            // Если произошла ошибка
            e.printStackTrace(); 
        }
    }
    
    public Class<?> loadClass (String path, String fileName, String className) throws MalformedURLException, ClassNotFoundException{
        Class<?> clazz = null;
        File file = new File (path.concat("/").concat(fileName).concat(".jar"));
        if (!file.exists()) {
            //если файл не найден
            System.out.println("Файл не найден");
        } else {
            String classForLoad = null;
            URL url = file.toURI().toURL(); 
            URL[] urls = new URL[] { url };
            for (String classes : ClassList) {
                if (!classes.equals(className)) {
                    //Если класс не найден
                    System.out.println("Класс не найден");
                }else{
                    classForLoad = classes;
                    break;
                }
            }
            ClassLoader cl = new URLClassLoader(urls);
            clazz = cl.loadClass(classForLoad);
            
        }
        
        return clazz;
    }
    
}