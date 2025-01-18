package pt.ipbeja.po2.sokoban2023.model;

/**
 * A Box is just a mobile element
 * @author Samuel Marto e Alexandre Amorim
 * @version 2023/06/14
 * Based on https://en.wikipedia.org/wiki/Sokoban
 */
final class Box extends MobileElement {
    /**
     * Creates a Box at pos
     *
     * @param pos initial position for the box
     */

    public Box(Position pos) {
        super(pos);
    }

    //public void moveTo(Position newBoxPos) { (parameter)this.position = newBoxPos;}

}
