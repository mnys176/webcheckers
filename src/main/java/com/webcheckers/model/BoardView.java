package com.webcheckers.model;

import com.webcheckers.util.Color;
import com.webcheckers.util.Type;
import com.webcheckers.util.BoardSeed;

import java.util.*;

/**
 * Entity for the game board.
 *
 * @author Mike Nystoriak
 */
public class BoardView implements Iterable<Row> {
    // Private Fields
    /**
     * The {@link List} of {@link Row}s on a {@link BoardView} object.
     */
    private final List<Row> rows;

    // Constructors

    /**
     * The constructor for a {@link BoardView} object.
     *
     * @param rows
     *         the {@link List} of {@link Row}s
     */
    public BoardView(List<Row> rows) {
        this.rows = rows;
    }

    // Public Methods

    /**
     * Fetches an {@link Iterator} to the {@link List} of {@link Row}s.
     *
     * @return the {@link Iterator} to the {@link List} of {@link Row}s
     */
    @Override
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }

    /**
     * Fetches {@link Row} of given index.
     *
     * @param row
     *         the index of a row
     *
     * @return the {@link Row} of that index
     */
    public Row getRow(int row) {
        return rows.get(row);
    }

    /**
     * Fetches {@link Space} of given index.
     *
     * @param rowIndex
     *         the index of a row
     * @param cellIndex
     *         the index of a cell
     *
     * @return the {@link Space} of the row and cell index
     */
    public Space getSpace(int rowIndex, int cellIndex) {
        return this.getRow(rowIndex).getSpace(cellIndex);
    }

    /**
     * The generator for a {@link BoardView} object.
     *
     * @param perspective
     *         switch that determines the orientation of the
     *         {@link BoardView}
     *
     *         RED: red {@link Piece}s are in the bottom rows
     *         WHITE: white {@link Piece}s are in the bottom rows
     * @param seed
     *         {@link BoardSeed} that determines the formation of the
     *         {@link BoardView}
     *
     *         DEFAULT: default {@link Piece} layout
     *         SIMPLE_MOVE: stages {@link BoardView}s for simple move demonstration
     *         SINGLE_CAPTURE: stages {@link BoardView}s for single capture demonstration
     *         MULTIPLE_CAPTURE: stages {@link BoardView}s for multiple capture demonstration
     *         KING_ME: stages {@link BoardView}s for king me demonstration
     *
     * @return a new {@link BoardView} object
     */
    public static BoardView generateBoard(Color perspective, BoardSeed seed) {
        List<Row> rows = new ArrayList<>();
        HashMap<Integer, Piece> op = new HashMap<>();

        //seed every row on the board
        for (int i = 0; i < 8; i++) {
            switch (seed) {
                case SIMPLE_MOVE:
                    if (i == 2) {
                        Color color = perspective == Color.RED ? Color.WHITE : Color.RED;
                        Type type = perspective == Color.RED ? Type.KING : Type.SINGLE;
                        op.put(3, new Piece(type, color));
                    } else if (i == 5) {
                        Color color = perspective == Color.RED ? Color.RED : Color.WHITE;
                        Type type = perspective == Color.RED ? Type.SINGLE : Type.KING;
                        op.put(4, new Piece(type, color));
                    }
                    rows.add(Row.generateRow(i, op));
                    break;
                case SINGLE_CAPTURE:
                    if (i == 3) {
                        Color color = perspective == Color.RED ? Color.WHITE : Color.RED;
                        Type type = perspective == Color.RED ? Type.KING : Type.SINGLE;
                        op.put(4, new Piece(type, color));
                        op.put(6, new Piece(type, color));
                    } else if (i == 4) {
                        Color color = perspective == Color.RED ? Color.RED : Color.WHITE;
                        Type type = perspective == Color.RED ? Type.SINGLE : Type.KING;
                        op.put(3, new Piece(type, color));
                        op.put(1, new Piece(type, color));
                    }
                    rows.add(Row.generateRow(i, op));
                    break;
                case MULTIPLE_CAPTURE:
                    if (i == 3) {
                        Color color = perspective == Color.RED ? Color.WHITE : Color.RED;
                        Type type = perspective == Color.RED ? Type.SINGLE : Type.KING;
                        op.put(4, new Piece(type, color));
                        op.put(6, new Piece(type, color));
                    } else if (i == 4) {
                        Color color = perspective == Color.RED ? Color.RED : Color.WHITE;
                        Type type = perspective == Color.RED ? Type.KING : Type.SINGLE;
                        op.put(3, new Piece(type, color));
                        op.put(1, new Piece(type, color));
                    }
                    rows.add(Row.generateRow(i, op));
                    break;
                case KING_ME:
                    if (i == 1) {
                        Color color = perspective == Color.RED ? Color.RED : Color.WHITE;
                        Type type = perspective == Color.RED ? Type.SINGLE : Type.KING;
                        op.put(2, new Piece(type, color));
                    } else if (i == 6) {
                        Color color = perspective == Color.RED ? Color.WHITE : Color.RED;
                        Type type = perspective == Color.RED ? Type.KING : Type.SINGLE;
                        op.put(5, new Piece(type, color));
                    }
                    rows.add(Row.generateRow(i, op));
                    break;
                default:
                    //correspond the colors based on board position; all Pieces start as SINGLE
                    Piece newPiece = null;
                    if (i <= 2) newPiece = new Piece(Type.SINGLE, perspective == Color.RED ? Color.WHITE : Color.RED);
                    else if (i >= 5) newPiece = new Piece(Type.SINGLE, perspective == Color.RED ? Color.RED : Color.WHITE);
                    rows.add(Row.generateRow(i, newPiece));
                    break;
            }
            op.clear();
        }
        return new BoardView(rows);
    }

    /**
     * The generator for a {@link BoardView} object.
     *
     * @param perspective
     *         switch that determines the orientation of the
     *         {@link BoardView}
     *
     *         RED: red {@link Piece}s are in the bottom rows
     *         WHITE: white {@link Piece}s are in the bottom rows
     *
     * @return a new {@link BoardView} object
     */
    public static BoardView generateBoard(Color perspective) {
        List<Row> rows = new ArrayList<>();
        //correspond the colors based on board position; all Pieces start as SINGLE
        for (int i = 0; i < 8; i++) {
            Piece newPiece = null;
            if (i <= 2) newPiece = new Piece(Type.SINGLE, perspective == Color.RED ? Color.WHITE : Color.RED);
            else if (i >= 5) newPiece = new Piece(Type.SINGLE, perspective == Color.RED ? Color.RED : Color.WHITE);
            rows.add(Row.generateRow(i, newPiece));
        }
        return new BoardView(rows);
    }

    /**
     * The updater for a {@link BoardView} object.
     *
     * @param move
     *         the {@link Move} object to decide what piece moves where
     * @param mirror
     *         switch that determines the orientation of the board
     *         {@link BoardView}
     *
     *         TRUE: red {@link Piece}s are in the bottom rows
     *         FALSE: white {@link Piece}s are in the bottom rows
     */
    public void updateBoard(Move move, boolean mirror) {
        //offset for board reversal
        int offset = mirror ? 7 : 0;

        int endCellIndex = Math.abs(offset - move.getEnd().getCell());
        int endRowIndex = Math.abs(offset - move.getEnd().getRow());
        int startCellIndex = Math.abs(offset - move.getStart().getCell());
        int startRowIndex = Math.abs(offset - move.getStart().getRow());


        Row startRow = this.getRow(startRowIndex);
        Space startSpace = startRow.getSpace(startCellIndex);
        Row endRow = this.getRow(endRowIndex);
        Space endSpace = endRow.getSpace(endCellIndex);

        Piece piece = startSpace.getPiece();
        startSpace.liftPiece();

        boolean isKingRow = endRowIndex == 0 || endRowIndex == 7;
        endSpace.placePiece(new Piece(isKingRow ? Type.KING : piece.getType(), piece.getColor()));

        if (move.isCapture()) {
            int targetRow = (startRowIndex + endRowIndex) / 2;
            int targetCell = (startCellIndex + endCellIndex) / 2;
            this.getRow(targetRow).getSpace(targetCell).liftPiece();
        }
    }

    public int checkScores(Color color) {
        int count = 0;
        Iterator<Row> rowIterator = this.rows.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Space> spaceIterator = row.iterator();
            while (spaceIterator.hasNext()) {
                Space space = spaceIterator.next();
                if (space.getPiece() != null) {
                    if (space.getPiece().getColor().equals(color)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public BoardView cloneBoardView(){
        List<Row> cloneRows = new ArrayList<>();
        for (Row row:this.rows){
            cloneRows.add(row.cloneRow());
        }
        return new BoardView(cloneRows);
    }

    public String handler(Piece piece){
        if (piece.getType().equals(Type.KING)){
            if (piece.getColor().equals(Color.RED)){
                return "R";
            }
            else{
                return "W";
            }
        }
        else{
            if (piece.getColor().equals(Color.RED)){
                return "r";
            }
            else{
                return "w";
            }
        }
    }

    public String boardToString(){
        String representation = "";
        for (int row=0;row<8;row++){
            for (int space=0;space<8;space++){
                if (rows.get(row).getSpace(space).getPiece()==null){
                    representation+=".";
                }
                else{
                    representation+=handler(rows.get(row).getSpace(space).getPiece());
                }
            }
            representation+="\n";
        }
        return representation;
    }

}
