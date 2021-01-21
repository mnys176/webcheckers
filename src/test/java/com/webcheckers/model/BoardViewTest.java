package com.webcheckers.model;

import com.webcheckers.util.Color;
import com.webcheckers.util.Type;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.webcheckers.util.BoardSeed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for the {@link BoardView} class.
 *
 * @author Mike Nystoriak
 */
@Tag("Model-tier")
public class BoardViewTest {
    // Private Fields
    /**
     * This {@link BoardView} should not be NULL.
     */
    private final String SHOULD_NOT_BE_NULL = "This BoardView should not be NULL.";
    /**
     * This {@link Row} should not be NULL.
     */
    private final String ROW_SHOULD_NOT_BE_NULL = "This Row should not be NULL.";
    /**
     * The selected {@link Row} should be the same.
     */
    private final String SELECTED_ROW_SHOULD_BE_THE_SAME = "The selected Row should be the same.";
    /**
     * The selected {@link Space} should be the same.
     */
    private final String SELECTED_SPACE_SHOULD_BE_THE_SAME = "The selected Space should be the same.";
    /**
     * This {@link Iterator} should not be NULL.
     */
    private final String ITERATOR_SHOULD_NOT_BE_NULL = "This Iterator should not be NULL.";
    /**
     * These {@link Iterator}s should not be the same.
     */
    private final String ITERATORS_SHOULD_NOT_BE_THE_SAME = "These Iterators should not be the same.";
    /**
     * This {@link Piece} should be a white king.
     */
    private final String PIECE_SHOULD_BE_WHITE_KING = "This Piece should be a white king.";
    /**
     * This {@link Piece} should be a red single.
     */
    private final String PIECE_SHOULD_BE_RED_SINGLE = "This Piece should be a red single.";
    /**
     * This {@link Piece} should be a white single.
     */
    private final String PIECE_SHOULD_BE_WHITE_SINGLE = "This Piece should be a white single.";


    // Public Methods
    /**
     * The constructor test for {@link BoardView}.
     *
     * Tests construction, instantiation, and field assignments.
     */
    @Test
    public void constructorTest() {
        List<Row> rows = new ArrayList<>();
        List<Space> spaces = new ArrayList<>();
        for (int i = 0; i < 8; i++) spaces.add(new Space(i, i % 2 == 0, null));
        for (int i = 0; i < 8; i++) rows.add(new Row(i, spaces));

        //test construction
        final BoardView CuT = new BoardView(rows);
        assertNotNull(CuT, SHOULD_NOT_BE_NULL);

        //test rows
        assertSame(rows.get(4), CuT.getRow(4), SELECTED_ROW_SHOULD_BE_THE_SAME);

        //test spaces
        assertSame(rows.get(2).getSpace(6), CuT.getSpace(2, 6), SELECTED_SPACE_SHOULD_BE_THE_SAME);
    }
    /**
     * The test for the iterator() method in {@link BoardView}.
     *
     * Tests that {@link Iterator}s are properly generated and
     * distinct.
     */
    @Test
    public void iteratorTest() {
        List<Space> spaces = new ArrayList<>();
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < 8; i++) spaces.add(new Space(i, i % 2 == 0, null));
        for (int i = 0; i < 8; i++) rows.add(new Row(i, spaces));
        final BoardView CuTA = new BoardView(rows);
        final BoardView CuTB = new BoardView(rows);

        Iterator<Row> itA = CuTA.iterator();
        Iterator<Row> itB = CuTB.iterator();

        //test generation
        assertNotNull(itA, ITERATOR_SHOULD_NOT_BE_NULL);
        assertNotNull(itB, ITERATOR_SHOULD_NOT_BE_NULL);

        //test distinction
        assertNotSame(itA, itB, ITERATORS_SHOULD_NOT_BE_THE_SAME);

        //test iteration
        while (itA.hasNext()) assertNotNull(itA.next(), ROW_SHOULD_NOT_BE_NULL);
        while (itB.hasNext()) assertNotNull(itB.next(), ROW_SHOULD_NOT_BE_NULL);
    }

    /**
     * The tests for seeding a {@link BoardView} for a demo
     * with a {@link BoardSeed}.
     *
     * Tests that {@link Piece}s are placed properly depending
     */
    @Test
    public void generateBoardWithSeedTest() {
        final BoardView CuTA = BoardView.generateBoard(Color.RED, BoardSeed.SIMPLE_MOVE);
        final BoardView CuTB = BoardView.generateBoard(Color.WHITE, BoardSeed.DEFAULT);

        //test placement on first BoardView from red perspective
        final Piece whiteKingA = CuTA.getSpace(2, 3).getPiece();
        final Piece redSingleA = CuTA.getSpace(5, 4).getPiece();
        assertEquals(new Piece(Type.KING, Color.WHITE), whiteKingA, PIECE_SHOULD_BE_WHITE_KING);
        assertEquals(new Piece(Type.SINGLE, Color.RED), redSingleA, PIECE_SHOULD_BE_RED_SINGLE);

        //test placement on second BoardView from white perspective
        final Piece whiteSingleB = CuTB.getSpace(7, 0).getPiece();
        final Piece redSingleB = CuTB.getSpace(0, 1).getPiece();
        assertEquals(new Piece(Type.SINGLE, Color.WHITE), whiteSingleB, PIECE_SHOULD_BE_WHITE_SINGLE);
        assertEquals(new Piece(Type.SINGLE, Color.RED), redSingleB, PIECE_SHOULD_BE_RED_SINGLE);
    }
    /**
     * The tests for proper {@link BoardView} updates.
     *
     * Tests that {@link BoardView} updates correctly
     */
    @Test
    public void updateBoardTest() {
        final Position start = new Position(4, 3);
        final Position end = new Position(2, 5);
        final Move move = new Move(start, end);
        final BoardView CuT = BoardView.generateBoard(Color.RED, BoardSeed.SINGLE_CAPTURE);

        //test update board
        final Piece piece = CuT.getRow(start.getRow()).getSpace(start.getCell()).getPiece();
        CuT.updateBoard(move, false);
        assertEquals(piece, CuT.getRow(end.getRow()).getSpace(end.getCell()).getPiece(), PIECE_SHOULD_BE_RED_SINGLE);
    }
}
