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

import java.util.HashMap;
import testloadpackage.TestLoadInterface;

public class Loader {
    public static void main(String[] args) throws Exception {
        HashMap mappings = new HashMap();
        mappings.put("testloadpackage.TestLoadClassForLoader", "C:\\Users\\dboro\\Desktop\\Nexus\\NexusFX\\build\\test\\classes\\testloadpackage\\TestLoadClassForLoader.class");
        // Если убрать комментарий - будет больно
        /*
            mappings.put("testloadpackage.TestLoadInterface", "путь\\classes\\testloadpackage\\TestLoadInterface.class");
        */
        XLoader xloa = new XLoader(mappings);
        Class sexy_cla = xloa.loadClass("testloadpackage.TestLoadClassForLoader");
        System.out.println("class was loaded");
        System.out.println("begin object creation");
        Object sexy_ob = sexy_cla.newInstance();
        System.out.println("object was created");
        System.out.println("invoke: getFoo" + sexy_cla.getMethod("getSimpleFoo").invoke(sexy_ob));
        System.out.println("get: stat_foo" + sexy_cla.getField("stat_foo").get(sexy_ob));
        TestLoadInterface local_sexy = (TestLoadInterface) sexy_ob;
        local_sexy.makeBar();
    }
}
