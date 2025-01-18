package pt.ipbeja.po2.sokoban2023.model;

/**
 * A Keeper is just a mobile element
 * @author Samuel Marto e Alexandre Amorim
 * @version 2023/06/14
 * Based on https://en.wikipedia.org/wiki/Sokoban
 */
final class Keeper extends MobileElement {

    /**
     * Creates the Keeper at pos
     *
     * @param pos initial position for the Keeper
     */

    public Keeper(Position pos) {
        super(pos);
    }

    // Check if is possible to move to newKeeperPos, if is moved
    public void moveTo(Position newKeeperPos) { // Direction (parameter)
        this.position = newKeeperPos;
    }
}
