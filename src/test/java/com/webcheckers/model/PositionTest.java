package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tester for the {@link Position} class.
 *
 * @author Mike Nystoriak
 */
@Tag("Model-tier")
public class PositionTest {
    // Private Fields
    /**
     * This {@link Position} should not be NULL.
     */
    private final String SHOULD_NOT_BE_NULL = "This Position should not be NULL.";
    /**
     * This {@link Position} has the wrong cell index.
     */
    private final String WRONG_CELL_INDEX = "This Position has the wrong cell index.";
    /**
     * This {@link Position} has the wrong row index.
     */
    private final String WRONG_ROW_INDEX = "This Position has the wrong row index.";

    // Public Methods
    /**
     * The constructor test for {@link Position}.
     *
     * Tests construction, instantiation, and field assignments.
     */
    @Test
    public void constructorTest() {
        final Position CuTA = new Position(0, 0);
        final Position CuTB = new Position(7, 7);

        //test construction
        assertNotNull(CuTA, SHOULD_NOT_BE_NULL);
        assertNotNull(CuTB, SHOULD_NOT_BE_NULL);

        //test row index
        assertEquals(0, CuTA.getRow(), WRONG_ROW_INDEX);
        assertEquals(7, CuTB.getRow(), WRONG_ROW_INDEX);

        //test cell index
        assertEquals(0, CuTA.getCell(), WRONG_CELL_INDEX);
        assertEquals(7, CuTB.getCell(), WRONG_CELL_INDEX);
    }
}
