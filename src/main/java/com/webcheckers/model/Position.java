package com.webcheckers.model;

/**
 * The position of a {@link Piece} on a {@link BoardView}.
 *
 * @author Mike Nystoriak
 */
public class Position {
    // Private Fields
    /**
     * The row index of a {@link Row} in a {@link BoardView}.
     */
    private int row;
    /**
     * The cell index of a {@link Space} in a {@link Row}.
     */
    private int cell;

    // Constructors
    /**
     * The constructor for a {@link Position} object.
     *
     * @param row
     *         the row index on a {@link BoardView}
     * @param cell
     *         the cell index of a {@link Row} on a {@link BoardView}
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    // Public Methods
    /**
     * The accessor to the row index on a {@link BoardView}.
     *
     * @return the row index
     */
    public int getRow() {
        return this.row;
    }
    /**
     * The accessor to the cell index of a {@link Row} on a {@link BoardView}.
     *
     * @return the cell index
     */
    public int getCell() {
        return this.cell;
    }

    public Position getOpposite(){
        return new Position(7-this.row,7-this.cell);
    }
}
