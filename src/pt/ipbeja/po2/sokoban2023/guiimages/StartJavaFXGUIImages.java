package pt.ipbeja.po2.sokoban2023.guiimages;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import pt.ipbeja.po2.sokoban2023.model.Level;
import pt.ipbeja.po2.sokoban2023.model.Position;
import pt.ipbeja.po2.sokoban2023.model.SokobanGameModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Start a game with a hardcoded board and images
 * @author Samuel Marto e Alexandre Amorim
 * @version 2023/06/14
 * Based on https://en.wikipedia.org/wiki/Sokoban
 */
public class StartJavaFXGUIImages extends Application {

    Stage stage;
    //Scene mainScene;


    @Override
    public void start(Stage primaryStage) {

        final String boardContent =
                """
                        FFWWWWFF
                        FFWFFWFF
                        WWWFFWWW
                        WFFEEFFW
                        WFFFFFFW
                        WWWWWWWW""";

        Position keeperPosition = new Position(3, 5);
        Set<Position> boxesPositions =
                Set.of(new Position(3, 2),
                        new Position(3,3));

        SokobanGameModel sokoban = new SokobanGameModel(new Level(keeperPosition, boxesPositions, boardContent));

        //MenuBar menuBar = createMenu();


        SokobanBoardImages sokobanBoardImages =
                new SokobanBoardImages(sokoban,
                        "keeper.png",
                        "box.png",
                        "boxend.png",
                        "wall.png",
                        "free.png",
                        "end.png"
                );


        BorderPane borderPane = new BorderPane();

        borderPane.setTop(createMenu());
        //borderPane.setTop(new TopBar());
        borderPane.setCenter(sokobanBoardImages);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(100));
        borderPane.setRight(new RightBar("3, 5"));

        Scene scene = new Scene(borderPane);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        sokoban.registerView(sokobanBoardImages);
        sokobanBoardImages.requestFocus(); // to remove focus from first button
        primaryStage.show();
    }

    /**
     * @param args not used
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    private VBox createMovementsHistory() {
        return new VBox();
    }

    private MenuBar createMenu() {

        MenuBar menuBar = new MenuBar();

        // Create a "File" menu
        Menu fileMenu = new Menu("File");

        // Create an "Open" MenuItem
        MenuItem openMenuItem = new MenuItem("Open");

        // Set an action on the "Open" MenuItem
        openMenuItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            // Set the initial directory, file extension filters, etc., for the FileChooser if needed

            fileChooser.setInitialDirectory(new File("C:\\PO2\\po2-tp-2022-2023-samuel-marto\\levelFiles"));
            // Show the FileChooser dialog
            Stage fileChooserStage = new Stage();

            fileChooserStage.setTitle("Open File");
            File selectedFile = fileChooser.showOpenDialog(fileChooserStage);
            if (selectedFile != null) {
                // TODO OPEN CHOSEN LEVEL



                changeScene(e, selectedFile.getAbsolutePath());
            }
        });

        // Add the "Open" MenuItem to the "File" menu
        fileMenu.getItems().add(openMenuItem);

        // Add the "File" menu to the MenuBar
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    private void changeScene(ActionEvent event, String path) {
        // TODO CHANGE SCENE
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createMenu());
        borderPane.setCenter(getScene(path));
        Scene scene = new Scene(borderPane);

        MenuItem menuItem = (MenuItem) event.getSource();
        stage = (Stage)menuItem.getParentPopup().getOwnerWindow();
        stage.setScene(scene);
        stage.show();
    }

    private Node getScene(String path){

        SokobanGameModel sokoban;

        Position keeperPosition;

        Set<Position> boxesPositions = new HashSet<>();

        String board;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){

            bufferedReader.readLine();
            String[] keeperCoordinates = bufferedReader.readLine().trim().split("\\s");
            keeperPosition = new Position(Integer.parseInt(keeperCoordinates[0]), Integer.parseInt(keeperCoordinates[1]));

            int nrBoxes = Integer.parseInt(bufferedReader.readLine().trim());


            for (int i = 0; i < nrBoxes; i++) {
                String[] box = bufferedReader.readLine().trim().split("\\s");

                boxesPositions.add(new Position(Integer.parseInt(box[0]), Integer.parseInt(box[1])));
            }


            StringBuilder stringBuilder = new StringBuilder();
            String boardContent = "";


            for (int i = 0; i < 8; i++) {
//                        System.out.println(bufferedReader.readLine());
                stringBuilder.append(bufferedReader.readLine()).append("\r\n");
                boardContent = boardContent.concat(stringBuilder.toString());
            }


            System.out.println(stringBuilder);

            sokoban = new SokobanGameModel(new Level(keeperPosition, boxesPositions, "FFFFFFFFF"));

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        String boardContent =
                """
                        FWWWWWFFF
                        FWFFFWWWW
                        FWFFFWFFW
                        FWWFFFFEW
                        WWWFWWWEW
                        WFFFWFWEW
                        WFFFWFWWW
                        WFFFWFFFF
                        WWWWWFFFF""";

        //Position keeperPosition = new Position(3, 6);
        /*Set<Position> boxesPositions =
                Set.of(new Position(3, 2),
                        new Position(3,3));*/


        return new SokobanBoardImages(sokoban,
                        "keeper.png",
                        "box.png",
                        "boxend.png",
                        "wall.png",
                        "free.png",
                        "end.png"
                );
    }

    private void resetGame(){

    }
}


