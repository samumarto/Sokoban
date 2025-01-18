package pt.ipbeja.po2.sokoban2023.model;

import pt.ipbeja.po2.sokoban2023.images.ImageType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Game board model
 * Contains the game state and reactive behaviour:
 * the interface tells the model what happened and the model tells the interface what to update
 * @author Samuel Marto e Alexandre Amorim
 * @version 2023/06/14
 * Based on https://en.wikipedia.org/wiki/Sokoban
 */
public class SokobanGameModel {
    private final BoardModel board;
    private final Keeper keeper;
    private final Set<Box> boxes;
    private SokobanView view;

    public SokobanGameModel(Level level) {
        this.board = new BoardModel(level.boardContent());
        this.keeper = new Keeper(level.keeperPosition());
        this.boxes = this.createSetOfBoxes(level.boxesPositions());
        this.view = null;
    }

    private Set<Box> createSetOfBoxes(Set<Position> boxesPositions) {
        Set<Box> set = new HashSet<>();
        for (Position pos : boxesPositions) {
            set.add(new Box(pos));
        }
        return set;
    }

    public Keeper keeper() { return this.keeper; }

    /**
     * Register a view (an observer) and updates it
     *
     * @param view the view to register
     */
    public void registerView(SokobanView view) {
        this.view = view;
    }

    /**
     * @return the position content or WALL it outside the board
     */
    public PositionContent getPosContent(Position pos) {
        if (this.isOutsideBoard(pos)) return PositionContent.WALL;
        else return this.board.getPosContent(pos);
    }

    public boolean isOutsideBoard(Position pos) {
        return pos.line() < 0 || pos.col() < 0 ||
                pos.line() >= board.nLines() ||
                pos.col() >= board.nCols();
    }

    /**
     * @return number of board lines
     */
    public int getNLines() {
        return this.board.nLines();
    }

    /**
     * @return number of board columns
     */
    public int getNCols() {
        return this.board.nCols();
    }

    /**
     * Game ends successfully
     *
     * @return true if all boxes are in the end position (games ends),
     * false otherwise
     */
    public boolean allBoxesAreStored() {
        for (Box box : this.boxes) {
            if (this.getPosContent(box.getPosition()) != PositionContent.END)
                return false;
        }
        return true;
    }

    /**
     * Tries to move keeper in the specified direction
     * @param dir direction of movement
     * @return true if moved, false otherwise
     */
    public boolean moveKeeper(Direction dir) {
        return this.moveKeeperTo(this.keeper.getPosition().move(dir));
    }

    /**
     * Tries to move keeper to position newPosition
     *
     * @param newPosition target position
     * @return true if moved, false otherwise
     */
    private boolean moveKeeperTo(Position newPosition) {
        Position initialPos = this.keeper.getPosition();
        List<Position> positions = this.moveTo(initialPos, newPosition);

        if (positions.size() > 0) {
            String messageToGUI = "move from " + positions.get(0) + " to " + positions.get(1);
            this.view.update(new MessageToUI(positions, messageToGUI));
            return true;
        }
        return false;
    }

    /**
     * Move from one position to the other and return the changed positions that need to be updated
     * @param keeperPosition initial keeper position
     * @param newKeeperPos position where the keeper wants to move
     * @return positions that where changed
     */
    private List<Position> moveTo(Position keeperPosition, Position newKeeperPos) {
        final Position possibleFinalBoxPos = Position.boxNextPositionAfterPush(keeperPosition, newKeeperPos);
        final boolean boxInNewKeeperPos = this.boxInPos(newKeeperPos);
        final boolean boxInPossibleFinalBoxPos = this.boxInPos(possibleFinalBoxPos);
        if (!boxInNewKeeperPos
                &&
                !this.getPosContent(newKeeperPos).equals(PositionContent.WALL)) {
            // move to empty position
            this.keeper.moveTo(newKeeperPos);
            return List.of(newKeeperPos, keeperPosition);
        } else if (boxInNewKeeperPos
                &&
                !this.getPosContent(possibleFinalBoxPos).equals(PositionContent.WALL)
                &&
                !boxInPossibleFinalBoxPos) {
            // move box
            this.keeper.moveTo(newKeeperPos);
            this.moveBoxAt(newKeeperPos, possibleFinalBoxPos);
            return List.of(newKeeperPos, keeperPosition, possibleFinalBoxPos);
        }
        // no movement
        return List.of();
    }

    /**
     * Move box
     * @param start initial box position
     * @param end   final box position
     */
    private void moveBoxAt(Position start, Position end) {
        this.boxes.remove(new Box(start));
        this.boxes.add(new Box(end));
    }

    /**
     * Ask for text for a given position
     *
     * @param p position
     * @return text with contents of position
     */
    public String textForPosition(Position p) {
        return this.keeperInPosText(p) + this.boxInPosText(p) + endInPosText(p) + wallInPosText(p);
    }

    /**
     * Text if keeper in given position
     *
     * @param pos position
     * @return "K " or ""
     */
    private String keeperInPosText(Position pos) {
        return this.keeper.getPosition().equals(pos) ? "K " : "";
    }

    /**
     * Test if the given position has a box
     *
     * @param pos position to test
     * @return true if there is a box in pos
     */
    public boolean boxInPos(Position pos) {
        Set<Box> set = new HashSet<>();
        for (Box box : this.boxes) {
            if (box.getPosition().equals(pos)){
                return true;
            }
        }
        return false;
        //return this.boxes.contains(new Box(pos));
    }

    /**
     * Text if a box is in the given position
     *
     * @param pos position
     * @return "BOX " or ""
     */
    private String boxInPosText(Position pos) {
        return this.boxInPos(pos) ? "BOX " : "";
    }

    /**
     * Text if given position is an end position
     *
     * @param pos position
     * @return "END " or ""
     */
    private String endInPosText(Position pos) {
        return this.board.getPosContent(pos).equals(PositionContent.END) ? "END " : "";
    }

    /**
     * Text if given position is a wall
     *
     * @param pos position
     * @return "WALL " or ""
     */
    private String wallInPosText(Position pos) {
        return this.board.getPosContent(pos).equals(PositionContent.WALL) ? "WALL " : "";
    }

    /**
     * image that should be shown at position pos
     * @param pos position
     * @return image type at position pos
     */
    public ImageType imageForPosition(Position pos) {
        if (board.getPosContent(pos).equals(PositionContent.WALL))
            return ImageType.WALL;
        else if (board.getPosContent(pos).equals(PositionContent.FREE)) {
            if (this.keeper.getPosition().equals(pos))
                return ImageType.KEEPER;
            else if (this.boxInPos(pos))
                return ImageType.BOX;
            else
                return ImageType.FREE;
        }
        else if (board.getPosContent(pos).equals(PositionContent.END)) {
            if (this.keeper.getPosition().equals(pos))
                return ImageType.KEEPER;
            else if (this.boxInPos(pos))
                return ImageType.BOXEND;
            else
                return ImageType.END;
        }
        return ImageType.END;
    }

    //comment for testing
}
