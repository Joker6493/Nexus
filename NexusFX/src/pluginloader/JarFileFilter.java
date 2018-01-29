/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pluginloader;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author d.borodin
 */
public class JarFileFilter implements FileFilter {
    // Только принимает 'pathname' как файл и имеет 'расширение' (extension) это txt.
    @Override
    public boolean accept(File pathname) {
        if (!pathname.isFile()) {
            return false;
        }
        if (pathname.getAbsolutePath().endsWith(".jar")) {
            return true;
        }
        return false;
    }
}
