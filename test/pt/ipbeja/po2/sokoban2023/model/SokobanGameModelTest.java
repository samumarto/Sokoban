package pt.ipbeja.po2.sokoban2023.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SokobanGameModelTest {

    @Test
    void testKeeperMovementToTheRight() {
        Level level = new Level();
        SokobanGameModel sokoban = new SokobanGameModel(level);
        // first a view that does not check
        sokoban.registerView(messageToUI -> {});

        assertEquals(new Position(3, 5), sokoban.keeper().getPosition());
        // move right
        // test new position
    }

    @Test
    void testReturnedPositionsOnMoveToEmpty() {
        Level level = new Level();
        SokobanGameModel sokoban = new SokobanGameModel(level);

        sokoban.registerView(messageToUI -> {
            List<Position> expectedPositons = List.of(new Position(3, 6), new Position(3, 5));
            assertEquals(expectedPositons, messageToUI.positions());
        });
        sokoban.moveKeeper(Direction.RIGHT);
    }

    @Test
    void testReturnedPositonsOnMoveBox() {
        Level level = new Level();
        SokobanGameModel sokoban = new SokobanGameModel(level);

        // first a view that does not check
        sokoban.registerView(messageToUI -> {});

        //move
        sokoban.moveKeeper(Direction.LEFT);
        sokoban.moveKeeper(Direction.LEFT);
        sokoban.moveKeeper(Direction.LEFT);

        // now a view that checks
        sokoban.registerView(messageToUI -> {
            List<Position> expectedPositons =
                    List.of(new Position(3, 3), new Position(4, 3), new Position(2, 3));
            assertEquals(expectedPositons, messageToUI.positions());
        });
        //sokoban.moveKeeper(Direction.LEFT);
    }
 // baixo,esquerda,esquerda,cima,baixo,esquerda,esquerda,cima,direita,direita,direita,cima,cima,esquerda,baixo,direira,baixo,baixo,direita,direita,cima,esquerda
}

