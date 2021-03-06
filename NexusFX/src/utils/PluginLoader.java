<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

//import utils.JarFileFilter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

/**
 * Модуль загрузки плагинов
 * 
 * 
 * @author d.borodin
 */

public class PluginLoader {
    //Массивы файлов, JAR-файлов и классов
    ArrayList<File> jarFileList = new ArrayList();
    ArrayList<String> ClassList = new ArrayList();
        
    public Class<?> loadClass (String path, String fileName, String className) throws MalformedURLException, ClassNotFoundException, IOException{
        Class<?> clazz = null;
        File file = new File (path.concat("/").concat(fileName).concat(".jar"));
        if (!file.exists()) {
            //если файл не найден
            System.out.println("Файл не найден");
        } else {
            JarFile jFile = new JarFile(file);
            jFile.stream().forEach(jc -> {
                if (jc.getName().endsWith(".class")) {
                    ClassList.add(jc.getName().replace("/", ".").replace(".class", ""));
                }
            });
            URL url = file.toURI().toURL(); 
            URL[] urls = new URL[] { url };
            for (String classes : ClassList) {
                ClassLoader cl = new URLClassLoader(urls);
                if (!classes.equals(className)) {
                    cl.loadClass(classes);
                    //загружаем все классы из jar-ки
                }else{
                    clazz = cl.loadClass(classes);
                    //загружаем и получаем нужный нам класс
                }
            }
        }
        return clazz;
    }
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

//import utils.JarFileFilter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

/**
 * Plugin Loader module
 * 
 * 
 * @author d.borodin
 */

public class PluginLoader {
    //Array of files and classes
    ArrayList<File> jarFileList = new ArrayList();
    ArrayList<String> ClassList = new ArrayList();
        
    public Class<?> loadClass (String path, String fileName, String className) throws MalformedURLException, ClassNotFoundException, IOException{
        Class<?> clazz = null;
        File file = new File (path.concat("/").concat(fileName).concat(".jar"));
        if (!file.exists()) {
            //if file not exist
            System.out.println("Файл не найден");
        } else {
            JarFile jFile = new JarFile(file);
            jFile.stream().forEach(jc -> {
                if (jc.getName().endsWith(".class")) {
                    ClassList.add(jc.getName().replace("/", ".").replace(".class", ""));
                }
            });
            //System.out.println(ClassList);
            URL url = file.toURI().toURL(); 
            URL[] urls = new URL[] { url };
            for (String classes : ClassList) {
                ClassLoader cl = new URLClassLoader(urls);
                if (!classes.equals(className)) {
                    cl.loadClass(classes);
                    //System.out.println("Загружен класс "+classes);
                    //Loading all classes from file
                }else{
                    clazz = cl.loadClass(classes);
                    //System.out.println("Класс загружен");
                    //Loading and get required class
                }
            }
        }
        return clazz;
    }
>>>>>>> Nexus_prealpha
}