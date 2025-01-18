package pt.ipbeja.po2.sokoban2023.guiimages;

import javafx.collections.ObservableList;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import pt.ipbeja.po2.sokoban2023.images.ImageType;
//import nifty.sokoban.model.*;
import pt.ipbeja.po2.sokoban2023.model.*;

import java.awt.Toolkit;
import java.util.Map;

/**
 * Game interface. Just a GridPane of images. No menu.
 * @author Samuel Marto e Alexandre Amorim
 * @version 2023/06/14
 * Based on https://en.wikipedia.org/wiki/Sokoban
 */
public class SokobanBoardImages extends GridPane implements SokobanView {
    private final SokobanGameModel sokoban;
    final Map<ImageType, Image> imageTypeToImage;
    private static final int SQUARE_SIZE = 80;

    /**
     * Create a sokoban board with labels and handle keystrokes
     */
    public SokobanBoardImages(SokobanGameModel sokoban,
                              String keeperImageFilename,
                              String boxImageFilename,
                              String boxEndImageFilename,
                              String wallImageFilename,
                              String freeImageFilename,
                              String endImageFilename) {

        this.imageTypeToImage =
                Map.of(ImageType.KEEPER, new Image("images/" + keeperImageFilename),
                        ImageType.BOX, new Image("images/" + boxImageFilename),
                        ImageType.BOXEND, new Image("images/" + boxEndImageFilename),
                        ImageType.WALL,  new Image("images/" + wallImageFilename),
                        ImageType.END, new Image("images/" + endImageFilename),
                        ImageType.FREE, new Image("images/" + freeImageFilename));


        this.sokoban = sokoban;
        this.buildGUI();

        this.setOnKeyPressed( event ->
        {
            final Map<KeyCode, Direction> keyToDir = Map.of(
                    KeyCode.UP, Direction.UP,
                    KeyCode.DOWN, Direction.DOWN,
                    KeyCode.LEFT, Direction.LEFT,
                    KeyCode.RIGHT, Direction.RIGHT);
            Direction direction = keyToDir.get(event.getCode());
            if (direction != null && !sokoban.moveKeeper(direction)) {
                SokobanBoardImages.this.couldNotMove();
            }
        });
    }

    /**
     * Build the interface
     */
    private void buildGUI() {
        assert (this.sokoban != null);

        // create one label for each position
        for (int line = 0; line < this.sokoban.getNLines(); line++) {
            for (int col = 0; col < this.sokoban.getNCols(); col++) {
                Label label = new Label();
                label.setMinWidth(SQUARE_SIZE);
                label.setMinHeight(SQUARE_SIZE);
                ImageType imgType = this.sokoban.imageForPosition(new Position(line, col));
                label.setGraphic(this.createImageView(this.imageTypeToImage.get(imgType)));
                this.add(label, col, line); // add label to GridPane
            }
        }
        this.requestFocus();
    }

    private Node createImageView(Image image) { // Req 6
        return new ImageView(image);
    }

    /**
     * Signal that keeper could not move
     */
    private void couldNotMove() {
        Toolkit.getDefaultToolkit().beep(); // did not move
    }


    @Override
    public void update(MessageToUI messageToUI) { // Samuel
        for (Position p : messageToUI.positions()) {
            ImageType imgType = this.sokoban.imageForPosition(p);

            this.getLabel(p.line(), p.col()).setGraphic(this.createImageView(this.imageTypeToImage.get(imgType)));
        }
        if (this.sokoban.allBoxesAreStored()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Level completed!");
            alert.showAndWait();
            System.exit(0);
        }
    }

    public Label getLabel(int line, int col) {
        ObservableList<Node> children = this.getChildren();
        for (Node node : children) {
            if(GridPane.getRowIndex(node) == line && GridPane.getColumnIndex(node) == col) {
                assert(node.getClass() == Label.class);
                return (Label)node;
            }
        }
        assert(false); // must not happen
        return null;
    }



}
