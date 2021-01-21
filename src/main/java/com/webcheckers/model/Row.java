package com.webcheckers.model;

import com.webcheckers.util.BoardSeed;

import java.util.*;

/**
 * Entity for a row on the game board.
 *
 * @author Mike Nystoriak
 */
public class Row implements Iterable<Space> {
    // Private Fields
    /**
     * The index of the {@link Row}.
     */
    private final int index;
    /**
     * The {@link List} of {@link Space}s in the {@link Row}.
     */
    private final List<Space> spaces;
    
    // Constructors
    /**
     * The constructor of a {@link Row} object.
     *
     * @param index
     *         the index of the {@link Row}
     * @param spaces
     *         the {@link List} of {@link Space}s
     */
    public Row(int index, List<Space> spaces) {
        this.index = index;
        this.spaces = spaces;
    }
    
    // Public Methods
    /**
     * Fetches an {@link Iterator} to the {@link List} of {@link Space}s.
     *
     * @return the {@link Iterator} to the {@link List} of {@link Space}s
     */
    @Override
    public Iterator<Space> iterator() {
        return this.spaces.iterator();
    }
    /**
     * The accessor to the index of the {@link Row}.
     *
     * @return the index of the {@link Row}
     */
    public int getIndex() {
        return this.index;
    }
    /**
     * The accessor to a {@link Space} at a given cell index.
     *
     * @param cell
     *         the cell index of the {@link Space}
     *
     * @return the {@link Space} at the given cell index
     */
    public Space getSpace(int cell) {
        return this.spaces.get(cell);
    }
    /**
     * The generator for a {@link Row} object.
     *
     * @param index
     *         the index of the {@link Row}
     * @param op
     *         a {@link Map} containing the {@link Piece}s and
     *         where to put the
     *
     * @return a new {@link Row} object
     */
    public static Row generateRow(int index, HashMap<Integer, Piece> op) {
        //determine whether or not alternate Piece placement based on row index
        List<Space> spaces = new ArrayList<>();
        boolean oddSpacesGetPiece = index % 2 == 1;
        for (int i = 0; i < 8; i++) {
            //construct a new Space with or without a Piece
            Space space = new Space(i, oddSpacesGetPiece, null);
            if (oddSpacesGetPiece && op.containsKey(i)) space.placePiece(op.get(i));

            spaces.add(space);
            oddSpacesGetPiece = !oddSpacesGetPiece;
        }
        return new Row(index, spaces);
    }
    /**
     * The generator for a {@link Row} object.
     *
     * @param index
     *         the index of the {@link Row}
     * @param newPiece
     *         the {@link Piece} to be placed or not placed on a {@link Space}
     *
     * @return a new {@link Row} object
     */
    public static Row generateRow(int index, Piece newPiece) {
        //determine whether or not alternate Piece placement based on row index
        List<Space> spaces = new ArrayList<>();
        boolean oddSpacesGetPiece = index % 2 == 1;
        for (int i = 0; i < 8; i++) {
            //construct a new Space with or without a Piece
            Space space = new Space(i, oddSpacesGetPiece, null);
            if (oddSpacesGetPiece) space.placePiece(newPiece);

            spaces.add(space);
            oddSpacesGetPiece = !oddSpacesGetPiece;
        }
        return new Row(index, spaces);
    }

    public Row cloneRow(){
        List<Space> cloneSpaces = new ArrayList<>();
        for (Space space: this.spaces){
            cloneSpaces.add(space.cloneSpace());
        }
        return new Row(this.index,cloneSpaces);
    }
}