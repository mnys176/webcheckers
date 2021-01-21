package com.webcheckers.model;

import com.webcheckers.util.Color;
import com.webcheckers.util.Type;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A move performed by a {@link Piece} from one {@link Position} to another.
 *
 * @author Mike Nystoriak
 */
public class Move {
    // Private Fields
    /**
     * The starting {@link Position} of a {@link Piece}.
     */
    private Position start;
    /**
     * The final {@link Position} of a {@link Piece}.
     */
    private Position end;

    // Constructors
    /**
     * The constructor for a {@link Move} object.
     *
     * @param start
     *         the starting {@link Position} of a {@link Piece}
     * @param end
     *         the final {@link Position} of a {@link Piece}
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    // Public Methods
    /**
     * The accessor to the starting {@link Position} of a {@link Piece}.
     *
     * @return the starting point
     */
    public Position getStart() {
        return this.start;
    }
    /**
     * The accessor to the final {@link Position} of a {@link Piece}.
     *
     * @return the destination
     */
    public Position getEnd() {
        return this.end;
    }
    /**
     * Determines whether a {@link Piece} at a {@link Space} is a king.
     *
     * @param boardView
     *         the {@link BoardView} to look in
     * @param position
     *         the {@link Position} to look at
     *
     * @return a boolean that states whether or not the {@link Piece} is a king.
     *
     *         TRUE: the {@link Piece} is a king.
     *         FALSE: the {@link Piece} is not a king.
     */
    private boolean isKing(BoardView boardView, Position position) {
        return boardView.getRow(position.getRow()).getSpace(position.getCell()).getPiece().getType().equals(Type.KING);
    }
    /**
     * Determines whether a standard (non-capture) {@link Move} is valid.
     *
     * @param boardView
     *         the {@link BoardView} to look in
     *
     * @return a boolean that states whether or not the {@link Move} is valid.
     *
     *         TRUE: the {@link Move} is valid with the current board.
     *         FALSE: the {@link Move} is not valid with the current board.
     */
    private boolean validateStandardMove(BoardView boardView) {
        boolean king = this.isKing(boardView, this.start);
        if (!boardView.getSpace(this.end.getRow(), this.end.getCell()).isValid()) return false;
        if (king && Math.abs(this.start.getRow() - this.end.getRow()) != 1) return false;
        if (!king && this.start.getRow() - this.end.getRow() != 1) return false;
        if (Math.abs(this.start.getCell() - this.end.getCell()) != 1) return false;
        return true;
    }
    /**
     * Determines whether a {@link Move} is a capture, exclusively looking at its distances.
     *
     * @return a boolean that states whether or not the {@link Move} is a capture.
     *
     *         TRUE: the {@link Move} is a capture.
     *         FALSE: the {@link Move} is not a capture.
     */
    public boolean isCapture() {
        return Math.abs(this.start.getRow() - this.end.getRow()) == 2 && Math.abs(this.start.getCell() - this.end.getCell()) == 2;
    }
    /**
     * Determines whether a {@link Move} has available captures, either at its beginning or end {@link Space}.
     *
     * @param boardView
     *         the {@link BoardView} to look in
     * @param fromStart
     *         the {@link Move}'s starting {@link Space} should be inspected, if false, the ending {@link Space} should be.
     *
     * @return a boolean that states whether or not the {@link Space} has an available capture.
     *
     *         TRUE: the {@link Space} has an available capture.
     *         FALSE: the {@link Space} does not have an available capture.
     */
    private boolean canCapture(BoardView boardView, boolean fromStart) {
        Color attackerColor = boardView.getRow(this.start.getRow()).getSpace(this.start.getCell()).getPiece().getColor();

        boolean king = isKing(boardView, this.start);
        if (!fromStart && this.end.getRow() == 0) king = true;

        ArrayList<Integer> diffs =
                king ? new ArrayList<>(Arrays.asList(-2, -2, -2, 2, 2, -2, 2, 2)) :
                        new ArrayList<>(Arrays.asList(-2, -2, -2, 2));

        Position fromPosition = fromStart ? this.start : this.end;

        for (int i = 0; i < diffs.size(); i += 2) {
            int endPossibleRow = fromPosition.getRow() + diffs.get(i);
            int endPossibleCol = fromPosition.getCell() + diffs.get(i + 1);
            if (!(endPossibleRow > 7 || endPossibleRow < 0 || endPossibleCol > 7 || endPossibleCol < 0)) {
                if (boardView.getRow(endPossibleRow).getSpace(endPossibleCol).isValid()) {
                    int endVictimRow = fromPosition.getRow() + diffs.get(i) / 2;
                    int endVictimCell = fromPosition.getCell() + diffs.get(i + 1) / 2;
                    if (boardView.getRow(endVictimRow).getSpace(endVictimCell).getPiece() != null) {
                        Color victimColor = boardView.getRow(endVictimRow).getSpace(endVictimCell).getPiece().getColor();
                        if (!victimColor.equals(attackerColor)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * Determines whether a capture {@link Move} is valid, meaning the {@link Piece} to jump exists and is an opposite color.
     *
     * @param boardView
     *         the {@link BoardView} to look in
     *
     * @return a boolean that states whether or not the capture {@link Move} is valid.
     *
     *         TRUE: the capture {@link Move} is valid with the current board.
     *         FALSE: the capture {@link Move} is not valid with the current board.
     */
    private boolean isChallengersCapture(BoardView boardView,Position onlyValidPosition) {
        if (onlyValidPosition!=null){
            if (!(this.start.getCell()==onlyValidPosition.getCell())||!(this.start.getRow()==onlyValidPosition.getRow())){
                return false;
            }
        }
        if (!isKing(boardView, this.start) && !(this.start.getRow() - 2 == this.end.getRow()) || (Math.abs(this.start.getCell() - this.end.getCell()) != 2))
            return false;
        else if (!this.isCapture())
            return false;

        int targetRow = (this.start.getRow() + this.end.getRow()) / 2;
        int targetCell = (this.start.getCell() + this.end.getCell()) / 2;
        Piece target = boardView.getRow(targetRow).getSpace(targetCell).getPiece();
        Piece attacker = boardView.getRow(this.start.getRow()).getSpace(this.start.getCell()).getPiece();

        boolean attackerOpposite;
        try {
            attackerOpposite = !target.getColor().equals(attacker.getColor());
        } catch (NullPointerException e) {
            System.out.println("No piece to jump!");
            attackerOpposite = false;
        }

        boolean targetExists;
        try {
            targetExists = target.getType().equals(Type.KING) || target.getType().equals(Type.SINGLE);
        } catch (NullPointerException e) {
            targetExists = false;
        }
        return attackerOpposite && targetExists;
    }
    /**
     * Determines whether a capture {@link Move} is available for the color of the {@link Player} who has the turn.
     *
     * @param boardView
     *         the {@link BoardView} to look in
     * @param game
     *         the {@link Game} to take the colors from
     *
     * @return a boolean that states whether or not the {@link Player} has an available capture {@link Move} anywhere on the {@link BoardView}.
     *
     *         TRUE: the {@link Player} has a valid capture {@link Move}.
     *         FALSE: the {@link Player} does not have a valid capture {@link Move}.
     */
    private boolean captureAvailable(BoardView boardView, Game game) {
        Color color = game.getActiveColor();
        for (int row = 0; row < 8; row++) {
            for (int cell = 0; cell < 8; cell++) {
                boolean myColor;
                try {
                    myColor = boardView.getRow(row).getSpace(cell).getPiece().getColor().equals(color);
                } catch (NullPointerException npe) {
                    myColor = false;
                }
                if (myColor) {
                    Move paperMove = new Move(new Position(row, cell), new Position(-1, -1));
                    if (paperMove.canCapture(boardView, true)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Determines whether a {@link Move} is valid for the {@link Player}.
     *
     * @param boardView
     *         the {@link BoardView} to look in
     * @param game
     *         the {@link Game} to validate the move for
     *
     * @return an integer code that states whether or not the {@link Player} has made a valid {@link Move}, and what kind of validity it was.
     *
     *         TRUE: the {@link Player} has made a valid {@link Move}.
     *         FALSE: the {@link Player} has not made a valid {@link Move}.
     */
    public int validate(BoardView boardView, Game game) {//Player currentUser
        game.setMulti(false);
        //if (!game.getMultiCapture()) game.setStagedMove(null);
        if (game.hasStagedMove()) {
            //failure code 2: submit move first
            return 2;
        }
        boolean hasCapture = this.captureAvailable(boardView, game);
        boolean isStandardMove = this.validateStandardMove(boardView);
        boolean challengersCapture = this.isChallengersCapture(boardView,game.getOnlyValidAttacker());
        boolean success = (isStandardMove && !hasCapture || challengersCapture);
        if (this.isChallengersCapture(boardView,game.getOnlyValidAttacker()) && this.canCapture(boardView, false)) {
            game.toggleMulti();
            //success code 1, has another valid capture to take
            return 1;
        }
        if (success) {
            //success code 0, the move went through as expected
            return 0;
        }
        if (!isStandardMove) {
            //failure code 3, the move was not a valid distance
            return 3;
        }
        //failure code 4, a capture must be taken here
        return 4;
    }

    public Move reverse(){
        return new Move(this.end,this.start);
    }
}