package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for the {@link Row} class.
 *
 * @author Mike Nystoriak
 */
@Tag("Model-tier")
public class RowTest {
    // Private Fields
    /**
     * This {@link Row} should not be NULL.
     */
    private final String SHOULD_NOT_BE_NULL = "This Row should not be NULL.";
    /**
     * The index field is out of bounds.
     */
    private final String INDEX_OUT_OF_BOUNDS = "The index field is out of bounds.";
    /**
     * This {@link Space} should not be NULL.
     */
    private final String SPACE_SHOULD_NOT_BE_NULL = "This Space should not be NULL.";
    /**
     * This {@link Iterator} should not be NULL.
     */
    private final String ITERATOR_SHOULD_NOT_BE_NULL = "This Iterator should not be NULL.";
    /**
     * These {@link Iterator}s should not be the same.
     */
    private final String ITERATORS_SHOULD_NOT_BE_THE_SAME = "These Iterators should not be the same.";

    // Public Methods
    /**
     * The constructor test for {@link Row}.
     *
     * Tests construction, instantiation, and field assignments.
     */
    @Test
    public void constructorTest() {
        List<Space> spaces = new ArrayList<>();
        for (int i = 0; i < 8; i++) spaces.add(new Space(i, i % 2 == 0, null));

        final Row CuT = new Row(0, spaces);

        //test construction
        assertNotNull(CuT, SHOULD_NOT_BE_NULL);

        //test index
        assertNotEquals(-1, CuT.getIndex(), INDEX_OUT_OF_BOUNDS);
    }
    /**
     * The test for the iterator() method in {@link Row}.
     *
     * Tests that {@link Iterator}s are properly generated and
     * distinct.
     */
    @Test
    public void iteratorTest() {
        List<Space> spaces = new ArrayList<>();
        for (int i = 0; i < 8; i++) spaces.add(new Space(i, i % 2 == 0, null));
        final Row CuTA = new Row(0, spaces);
        final Row CuTB = new Row(7, spaces);

        Iterator<Space> itA = CuTA.iterator();
        Iterator<Space> itB = CuTB.iterator();

        //test generation
        assertNotNull(itA, ITERATOR_SHOULD_NOT_BE_NULL);
        assertNotNull(itB, ITERATOR_SHOULD_NOT_BE_NULL);

        //test distinction
        assertNotSame(CuTA.iterator(), CuTB.iterator(), ITERATORS_SHOULD_NOT_BE_THE_SAME);

        //test iteration
        while (itA.hasNext()) assertNotNull(itA.next(), SPACE_SHOULD_NOT_BE_NULL);
        while (itB.hasNext()) assertNotNull(itB.next(), SPACE_SHOULD_NOT_BE_NULL);
    }
}