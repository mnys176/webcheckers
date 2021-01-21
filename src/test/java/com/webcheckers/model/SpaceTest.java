package com.webcheckers.model;

import com.webcheckers.util.Color;
import com.webcheckers.util.Type;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for the {@link Space} class.
 *
 * @author Mike Nystoriak
 */
@Tag("Model-tier")
public class SpaceTest {
    // Private Fields
    /**
     * This {@link Space} should not be NULL.
     */
    private final String SHOULD_NOT_BE_NULL = "This Space should not be NULL.";
    /**
     * This {@link Piece} should have been set to NULL.
     */
    private final String PIECE_SHOULD_HAVE_BEEN_SET_TO_NULL = "This Piece should have been set to NULL.";
    /**
     * This {@link Piece} should have been set to not NULL.
     */
    private final String PIECE_SHOULD_HAVE_BEEN_SET_TO_NOT_NULL = "This Piece should have been set to not NULL.";
    /**
     * The cellIndex field is out of bounds.
     */
    private final String CELL_INDEX_OUT_OF_BOUNDS = "The cellIndex field is out of bounds.";
    /**
     * This {@link Space} should be valid.
     */
    private final String SHOULD_BE_VALID = "This Space should be valid.";
    /**
     * This {@link Space} should not be valid.
     */
    private final String SHOULD_NOT_BE_VALID = "This Space should not be valid.";
    /**
     * A {@link Piece} should be allowed on a valid {@link Space}.
     */
    private final String PIECE_ALLOWED = "A Piece should be allowed on a valid Space.";
    /**
     * A {@link Piece} should not be allowed on a valid {@link Space}.
     */
    private final String PIECE_NOT_ALLOWED = "A Piece should not be allowed on an invalid Space.";

    // Public Methods
    /**
     * The constructor test for {@link Space}.
     *
     * Tests construction, instantiation, and field assignments.
     */
    @Test
    public void constructorTest() {
        final Space CuTA = new Space(0, false, new Piece(Type.SINGLE, Color.RED));
        final Space CuTB = new Space(7, true, new Piece(Type.SINGLE, Color.RED));

        //test construction
        assertNotNull(CuTA, SHOULD_NOT_BE_NULL);
        assertNotNull(CuTB, SHOULD_NOT_BE_NULL);

        //test cellIndex
        assertTrue(CuTA.getCellIndex() >= 0 && CuTA.getCellIndex() <= 7, CELL_INDEX_OUT_OF_BOUNDS);
        assertTrue(CuTB.getCellIndex() >= 0 && CuTB.getCellIndex() <= 7, CELL_INDEX_OUT_OF_BOUNDS);

        //test isValid
        assertFalse(CuTA.isValid(), SHOULD_NOT_BE_VALID);
        assertTrue(CuTB.isValid(), SHOULD_BE_VALID);

        //test piece
        assertNotNull(CuTA.getPiece(), PIECE_NOT_ALLOWED);
        assertNotNull(CuTB.getPiece(), PIECE_ALLOWED);

        CuTB.liftPiece();
        assertNull(CuTB.getPiece(), PIECE_SHOULD_HAVE_BEEN_SET_TO_NULL);
        CuTB.placePiece(new Piece(Type.SINGLE, Color.RED));
        assertNotNull(CuTB.getPiece(), PIECE_SHOULD_HAVE_BEEN_SET_TO_NOT_NULL);
    }
}
