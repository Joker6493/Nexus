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
public class TestLoadClassForLoader implements TestLoadInterface {
    public static String stat_foo = "hello stat_foo";
    static {
        System.out.println("TestLoadClassForLoader$$static");
    }
    {
        System.out.println("TestLoadClassForLoader$$init");
    }
    public static String getStatFoo() {
         return stat_foo;
    }
    public String getSimpleFoo() {
        return simple_foo;
    }
    public String simple_foo = "hello simple_foo";
    public void makeBar() {
        System.out.println ("make bar");
    }
}
