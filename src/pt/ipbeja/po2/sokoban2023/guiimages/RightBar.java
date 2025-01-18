package pt.ipbeja.po2.sokoban2023.guiimages;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class RightBar extends VBox {

    List<Label> movementsList = new ArrayList<>();

    public RightBar(String keeperPos) {
        this.setPadding(new Insets(100));
        addLabel(new Label(keeperPos));
    }

    // Adds a new Label (last movement)
    public void addLabel(Label label){ this.getChildren().add(label); }

    // Removes Last Label(movement)
    private void removeLabel() { movementsList.remove(movementsList.size() - 1); }
}
