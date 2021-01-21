package com.webcheckers.model;

import com.webcheckers.application.GameLobby;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for the {@link Player} class.
 *
 * @author Jack Gilbert
 */
@Tag("Model-tier")
public class PlayerTest {
    // Private Fields
    /**
     * This {@link Player} has the wrong name.
     */
    private final String NAME_SHOULD_NOT_BE_NULL = "Name should not be NULL.";
    /**
     * This {@link Player} has the wrong Name availability assigned to it.
     */
    private final String NAME_NOT_EQUAL = "Name is not equal to Test";
    /**
     * This {@link Player} has a false availability.
     */
    private final String AVAILABILITY_NOT_TRUE = "Availability is not true";
    /**
     * This {@link Player} has true availability.
     */
    private final String AVAILABILITY_NOT_FALSE = "Availability is not false";
    /**
     * This {@link GameLobby} is not NULL.
     */
    private final String GAME_LOBBY_NOT_NULL = "Game lobby is not instantiated as null";
    /**
     * This {@link Player} is not assigned to the proper {@link GameLobby}.
     */
    private final String GAME_LOBBY_NOT_ASSIGNED = "Player does not go to proper game lobby";
    /**
     * This {@link Player} is not equal to another {@link Player}.
     */
    private final String PLAYERS_NOT_EQUAL = "Players do match";
    /**
     * This {@link Player} has a hash that is not equivalent to another {@link Player}.
     */
    private final String HASHES_EQUAL = "Hash values are equal";
    /**
     * This {@link Player} should be in a game.
     */
    private final String SHOULD_BE_IN_GAME = "This Player should be in a game.";
    /**
     * This {@link Player} should be in a game.
     */
    private final String SHOULD_NOT_BE_IN_GAME = "This Player should be not in a game.";

    // Public Methods
    /**
     * The constructor test for {@link Player}.
     *
     * Tests construction, instantiation, and field assignments.
     */
    @Test
    public void constructorTest() {
        Player CuT = new Player("Test");
        assertEquals("Test", CuT.getName(), NAME_NOT_EQUAL);
        assertNotNull(CuT.getName(), NAME_SHOULD_NOT_BE_NULL);
    }
    /**
     * Test for equality between {@link Player}.
     *
     * Tests that the equal method will properly differentiate 2 objects,
     * and the object equated to itself.
     */
    @Test
    public void equalsTest() {
        Player CuT1 = new Player("Test");
        Player CuT2 = new Player("Test2");
        Player CuT3 = null;
        assertNotEquals(CuT1, CuT2, PLAYERS_NOT_EQUAL);
        assertEquals(CuT1, CuT1, PLAYERS_NOT_EQUAL);
        assertFalse(CuT1.equals(CuT3), PLAYERS_NOT_EQUAL);
    }
    /**
     * Test for getting a username from a {@link Player}.
     *
     * Test that the string gotten from a new player is the same as its name.
     */
    @Test
    public void getNameTest() {
        Player CuT = new Player("Test");
        assertEquals("Test", CuT.getName(), NAME_NOT_EQUAL);
    }
    /**
     * Test for hashing a {@link Player} object.
     *
     * Tests that two different {@link Player} objects have differing hash code values.
     */
    @Test
    public void hashTest() {
        Player CuT1 = new Player("Test");
        Player CuT2 = new Player("Test2");
        int CuTOneHash = CuT1.hashCode();
        int CuTTwoHash = CuT2.hashCode();
        assertNotEquals(CuTOneHash, CuTTwoHash, HASHES_EQUAL);
    }
}
