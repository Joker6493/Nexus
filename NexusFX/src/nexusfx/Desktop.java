/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexusfx;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author d.borodin
 */
public class Desktop {
    private Map<String,AnchorPane> windowList = new HashMap<String,AnchorPane>();
    private AnchorPane currentWindow = new AnchorPane();
    
    public AnchorPane getCurrentWindow() {
        return currentWindow;
    }
    public void setCurrentWindow(AnchorPane currentWindow) {
        this.currentWindow = currentWindow;
    }
    
    public void addWindow(String name, Node scene, BorderPane parent){
        AnchorPane newWindow = new AnchorPane();
        
    }
    public void closeWindow (String name){
        
    }
    
}
