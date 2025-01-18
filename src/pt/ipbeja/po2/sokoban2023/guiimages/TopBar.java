package pt.ipbeja.po2.sokoban2023.guiimages;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TopBar extends MenuBar {

    public TopBar() {

        Menu fileMenu = new Menu("File");
        MenuItem openMenuItem = new MenuItem("Open");

        openMenuItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.setInitialDirectory(new File("C:\\PO2\\po2-tp-2022-2023-samuel-marto\\levelFiles"));
            Stage fileChooserStage = new Stage();

            fileChooserStage.setTitle("Open File");
            File selectedFile = fileChooser.showOpenDialog(fileChooserStage);
            if (selectedFile != null) {
                // TODO OPEN CHOSEN LEVEL AND RESET GAME
            }
        });

        fileMenu.getItems().add(openMenuItem);

        this.getMenus().add(fileMenu);
    }

    private List<String> chosenLevel(ActionEvent event, String path){

        // TODO ALEX ARRAY LIST WITH KEEPER, BOXES POSITIONS, AND BOARD CONTENT (SOKOBAN MODEL)
        return new ArrayList<>();
    }
}
