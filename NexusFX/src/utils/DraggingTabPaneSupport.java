/*
 * Copyright (C) 2018 dboro
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package utils;

import java.util.concurrent.atomic.AtomicLong;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 *
 * @author dboro
 */

public class DraggingTabPaneSupport {

    private Tab currentDraggingTab ;
    private static final AtomicLong idGenerator = new AtomicLong();
    private final String draggingID = "DraggingTabPaneSupport-"+idGenerator.incrementAndGet() ;

    public void addSupport(TabPane tabPane) {
        tabPane.getTabs().forEach(this::addDragHandlers);
        tabPane.getTabs().addListener((Change<? extends Tab> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(this::addDragHandlers);
                }
                if (c.wasRemoved()) {
                    c.getRemoved().forEach(this::removeDragHandlers);
                }
            }
        });
// if we drag onto a tab pane (but not onto the tab graphic), add the tab to the end of the list of tabs:
        tabPane.setOnDragOver(e -> {
            if (draggingID.equals(e.getDragboard().getString()) && currentDraggingTab != null && currentDraggingTab.getTabPane() != tabPane) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });
        tabPane.setOnDragDropped(e -> {
            if (draggingID.equals(e.getDragboard().getString()) && currentDraggingTab != null && currentDraggingTab.getTabPane() != tabPane) {
                currentDraggingTab.getTabPane().getTabs().remove(currentDraggingTab);
                tabPane.getTabs().add(currentDraggingTab);
                currentDraggingTab.getTabPane().getSelectionModel().select(currentDraggingTab);
            }
        });
    }

    private void addDragHandlers(Tab tab) {
// move text to label graphic:
        if (tab.getText() != null && ! tab.getText().isEmpty()) {
            Label label = new Label(tab.getText(), tab.getGraphic());
            tab.setText(null);
            tab.setGraphic(label);
        }
        Node graphic = tab.getGraphic();
        graphic.setOnDragDetected(e -> {
            Dragboard dragboard = graphic.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
// dragboard must have some content, but we need it to be a Tab, which isn't supported
// So we store it in a local variable and just put arbitrary content in the dragbaord:
            content.putString(draggingID);
            dragboard.setContent(content);
            dragboard.setDragView(graphic.snapshot(null, null));
            currentDraggingTab = tab ;
        });
        graphic.setOnDragOver(e -> {
            if (draggingID.equals(e.getDragboard().getString()) && currentDraggingTab != null && currentDraggingTab.getGraphic() != graphic) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });
        graphic.setOnDragDropped(e -> {
            if (draggingID.equals(e.getDragboard().getString()) && currentDraggingTab != null && currentDraggingTab.getGraphic() != graphic) {
                int index = tab.getTabPane().getTabs().indexOf(tab) ;
                currentDraggingTab.getTabPane().getTabs().remove(currentDraggingTab);
                tab.getTabPane().getTabs().add(index, currentDraggingTab);
                currentDraggingTab.getTabPane().getSelectionModel().select(currentDraggingTab);
            }
        });
        graphic.setOnDragDone(e -> currentDraggingTab = null);
    }

    private void removeDragHandlers(Tab tab) {
        tab.getGraphic().setOnDragDetected(null);
        tab.getGraphic().setOnDragOver(null);
        tab.getGraphic().setOnDragDropped(null);
        tab.getGraphic().setOnDragDone(null);
    }
}