package com.webcheckers.application;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for the {@link GameLobby} class.
 *
 * @author Caleb Eldridge
 */
@Tag("Appl-tier")
public class GameLobbyTest {
    // Private Fields
    /**
     * This {@link GameLobby} should not be NULL.
     */
    private final String SHOULD_NOT_BE_NULL = "This GameLobby should not be NULL.";

    // Public Methods
    /**
     * The constructor test for {@link GameLobby}.
     *
     * Tests construction and instantiation.
     */
    @Test
    public void constructorTest() {
        //test construction
        final GameLobby CuT = new GameLobby();
        assertNotNull(CuT, SHOULD_NOT_BE_NULL);
    }

    /**
     * The getKey test for {@link GameLobby}.
     *
     * Tests the generation of keys based on {@Link Player}
     */
    @Test
    public void getKeyTest(){
        final GameLobby CuT = new GameLobby();
        Player player1 = new Player("Test1");
        Player player2 = new Player("Test2");
        String gameKey = CuT.getKey(player1, player2);
        assertEquals( gameKey, CuT.getKey(player1,player2));
    }

    /**
     * The test for getters and setters of {@link GameLobby}.
     *
     * Tests the addition and retrieval of games.
     */
    @Test
    public void getAndAddGameTest(){
        final GameLobby CuT = new GameLobby();
        Player player1 = new Player("Test1");
        Player player2 = new Player("Test2");
        CuT.createGameIfNoGameExists(player1,player2);
        String gameKey = CuT.getKey(player1, player2);
        assertNotNull(CuT.getGame(gameKey));
    }

    @Test
    public void removeGameTest(){
        final GameLobby CuT = new GameLobby();
        Player player1 = new Player("Test1");
        Player player2 = new Player("Test2");
        CuT.createGameIfNoGameExists(player1,player2);
        //assertTrue(CuT.doesGameExist(player1,player2));
        //CuT.removeGame(CuT.getKey(player1,player2));
        //assertFalse(CuT.doesGameExist(player1,player2));
    }
}