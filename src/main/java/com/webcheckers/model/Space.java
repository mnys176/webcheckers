package com.webcheckers.model;

/**
 * Entity for a space on the game board.
 *
 * @author Mike Nystoriak
 */
public class Space {
    // Private Fields
    /**
     * The index of a {@link Space} in a {@link Row}.
     */
    private final int cellIndex;
    /**
     * The validity of a {@link Space}.
     */
    private boolean isValid;
    /**
     * The {@link Piece} that resides on a {@link Space}.
     */
    private Piece piece;
    // Constructors
    /**
     * The constructor of a {@link Space} object.
     *
     * @param cellIndex
     *         index of a {@link Space} in a {@link Row}
     * @param isValid
     *         validity of a {@link Space}
     * @param piece
     *         {@link Piece} that resides on a {@link Space}
     */
    public Space(int cellIndex, boolean isValid, Piece piece) {
        //ensure that piece is NULL if isValid is FALSE
        //if (!isValid) piece = null;
        this.cellIndex = cellIndex;
        this.isValid = isValid;
        this.piece = piece;
    }
    // Public Methods
    /**
     * The accessor to the index of the {@link Space} in a {@link Row}.
     *
     * @return the index in the {@link Row}
     */
    public int getCellIndex() {
        return this.cellIndex;
    }
    /**
     * The accessor to the validity of the {@link Space}.
     *
     * @return the validity of the {@link Space}
     *
     *         TRUE: space is dark and unoccupied
     *         FALSE: space is white or occupied
     */
    public boolean isValid() {
        return this.isValid;
    }
    /**
     * The setter for the validity of a {@link Space}.
     *
     * @param isValid
     *         the validity of the {@link Space}
     *         TRUE: the {@link Space} is valid
     *         FALSE: the {@link Space} is invalid
     */
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
    /**
     * The accessor to the {@link Piece} that is on the {@link Space}.
     *
     * @return the {@link Piece}, if any, that resides on the {@link Space}
     */
    public Piece getPiece() {
        return this.piece;
    }
    /**
     * Removes the {@link Piece} that is on the {@link Space}.
     */
    public void liftPiece() {
        this.piece = null;
        this.isValid = true;
    }
    /**
     * Adds the {@link Piece} to the {@link Space}.
     *
     * @param piece
     *         the {@link Piece} to be placed on the {@link Space}
     */
    public void placePiece(Piece piece) {
        this.piece = piece;
        this.isValid = piece == null;
    }

    public Space cloneSpace(){
        if (this.piece==null){
            return new Space(this.cellIndex,this.isValid,null);
        }
        return new Space(this.cellIndex,this.isValid,this.piece.clonePiece());
    }
}
