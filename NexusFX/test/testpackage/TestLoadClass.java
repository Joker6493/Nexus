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
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.application.Application;
import utils.PluginLoader;

public class TestLoadClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        // TODO code application logic here
        String pluginPath = "C:\\Users\\dboro\\Desktop\\Nexus\\NexusFX\\plugins";
        //String pluginPath = "C:\\Users\\d.borodin\\Desktop\\Nexus\\NexusFX\\plugins";
        String fileName = "TestModule";
        String className = "testmodule.TestFX";
        PluginLoader clist = new PluginLoader();
        Class testClass = clist.loadClass(pluginPath, fileName, className);
        System.out.println("Класс "+testClass.getName()+" загружен!");
        System.out.println("Создаем объект");
        Object test_ob = testClass.newInstance();
        System.out.println("Объект создан");
        NexusPlugin local_test = (NexusPlugin) test_ob;
        Application.launch(testClass, args);
        local_test.invoke();
        
    }
    
}
