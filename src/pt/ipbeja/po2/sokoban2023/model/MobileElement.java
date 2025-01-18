package pt.ipbeja.po2.sokoban2023.model;

/**
 * Mobile Element is every element that is mobile
 * It is used to Keeper and the Boxes
 * @author Samuel Marto e Alexandre Amorim
 * @version 2023/06/14
 */

public abstract class MobileElement {

    // req1 - 2
    Position position;
    public MobileElement(Position position) {
        this.position = position;
    }

    public Position getPosition() { return position; } //req1 - 5
}
