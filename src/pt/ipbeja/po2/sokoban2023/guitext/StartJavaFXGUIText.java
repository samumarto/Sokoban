package pt.ipbeja.po2.sokoban2023.guitext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipbeja.po2.sokoban2023.model.Level;
import pt.ipbeja.po2.sokoban2023.model.Position;
import pt.ipbeja.po2.sokoban2023.model.SokobanGameModel;

import java.util.Set;

/**
 * Start a game with a hardcoded board and labels
 * @author Samuel Marto e Alexandre Amorim
 * @version 2023/06/14
 * Based on https://en.wikipedia.org/wiki/Sokoban
 */
public class StartJavaFXGUIText extends Application {

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

        SokobanBoardText sokobanBoard = new SokobanBoardText(sokoban);
        primaryStage.setScene(new Scene(sokobanBoard));

        sokoban.registerView(sokobanBoard);
        sokobanBoard.requestFocus(); // to remove focus from first button
        primaryStage.show();
    }

    /**
     * @param args  not used
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
