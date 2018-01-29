/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testpackage;

/**
 *
 * @author d.borodin
 */

import api.NexusPlugin;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.stage.*;
import pluginloader.PluginLoader;

public class TestLoadClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // TODO code application logic here
        //String pluginPath = "C:\\Users\\dboro\\Desktop\\Nexus\\NexusFX\\plugins";
        String pluginPath = "C:\\Users\\d.borodin\\Desktop\\Nexus\\NexusFX\\plugins";
        String fileName = "TestModule";
        String className = "testmodule.TestFX";
        PluginLoader clist = new PluginLoader();
        clist.fillLists(pluginPath);
        Class testClass = clist.loadClass(pluginPath, fileName, className);
        System.out.println("Класс "+testClass.getName()+" загружен!");
        System.out.println("Создаем объект");
        Object test_ob = testClass.newInstance();
        System.out.println("Объект создан");
        NexusPlugin local_test = (NexusPlugin) test_ob;
        System.out.println(test_ob.getClass());
        System.out.println(local_test.getClass());
        Application.launch(testClass, args);
        local_test.invoke();
        
    }
    
}
