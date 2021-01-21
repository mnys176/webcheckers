package com.webcheckers.model;

import com.webcheckers.util.Color;
import com.webcheckers.util.Type;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for the {@link Piece} class.
 *
 * @author Mike Nystoriak
 */
@Tag("Model-tier")
public class PieceTest {
    // Private Fields
    /**
     * This {@link Piece} should not be NULL.
     */
    private final String SHOULD_NOT_BE_NULL = "This Piece should not be NULL.";
    /**
     * This {@link Piece} has the wrong {@link Type}.
     */
    private final String WRONG_TYPE = "This Piece has the wrong Type.";
    /**
     * This {@link Piece} has the wrong {@link Color}.
     */
    private final String WRONG_COLOR = "This Piece has the wrong Color.";
    /**
     * These {@link Piece}s should be equal.
     */
    private final String SHOULD_BE_EQUAL = "These Pieces should be equal.";
    /**
     * These {@link Piece}s should not be equal.
     */
    private final String SHOULD_NOT_BE_EQUAL = "These Pieces should not be equal.";

    // Public Methods
    /**
     * The constructor test for {@link Piece}.
     *
     * Tests construction, instantiation, and field assignments.
     */
    @Test
    public void constructorTest() {
        final Piece CuTA = new Piece(Type.SINGLE, Color.RED);
        final Piece CuTB = new Piece(Type.KING, Color.WHITE);

        //test construction
        assertNotNull(CuTA, SHOULD_NOT_BE_NULL);
        assertNotNull(CuTB, SHOULD_NOT_BE_NULL);

        //test colors
        assertEquals(Color.RED, CuTA.getColor(), WRONG_COLOR);
        assertEquals(Color.WHITE, CuTB.getColor(), WRONG_COLOR);

        //test types
        assertEquals(Type.SINGLE, CuTA.getType(), WRONG_TYPE);
        assertEquals(Type.KING, CuTB.getType(), WRONG_TYPE);
    }
    /**
     * The test for the equals() method in {@link Piece}.
     *
     * Tests that all fields are correctly implemented in an
     * equals test.
     */
    @Test
    public void equalsTest() {
        final Piece CuTA = new Piece(Type.SINGLE, Color.RED);
        final Piece CuTB = new Piece(Type.SINGLE, Color.RED);
        final Piece CuTC = new Piece(Type.KING, Color.RED);
        final Piece CuTD = new Piece(Type.KING, Color.WHITE);

        //test equality
        assertEquals(CuTA, CuTA, SHOULD_BE_EQUAL);
        assertEquals(CuTB, CuTA, SHOULD_BE_EQUAL);

        //test inequality
        assertNotEquals(CuTC, CuTA, SHOULD_NOT_BE_EQUAL);
        assertNotEquals(CuTD, CuTC, SHOULD_NOT_BE_EQUAL);
    }
}
