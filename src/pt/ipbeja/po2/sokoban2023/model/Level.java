package pt.ipbeja.po2.sokoban2023.model;

import java.util.Set;

/**
 * Level data
 * @author Samuel Marto e Alexandre Amorim
 * @version 2023/06/14
 * Based on https://en.wikipedia.org/wiki/Sokoban
 */
public record Level(Position keeperPosition, Set<Position> boxesPositions, String boardContent) {
     public Level() {
         this(new Position(3, 5),
         Set.of(new Position(3, 2), new Position(3, 3)),
                        """
                        FFWWWWFF
                        FFWFFWFF
                        WWWFFWWW
                        WFFEEFFW
                        WFFFFFFW
                        WWWWWWWW"""
         );
    }
}
