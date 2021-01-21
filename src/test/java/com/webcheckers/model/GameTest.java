package com.webcheckers.model;

import com.webcheckers.util.BoardSeed;
import com.webcheckers.util.Color;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for the {@link Game} class.
 *
 * @author Aaron Segal
 * @author Mike Nystoriak
 * @author Caleb Eldridge
 */
@Tag("Model-tier")
public class GameTest {
    // Private Fields
    /**
     * This {@link Game} should not be NULL.
     */
    private final String SHOULD_NOT_BE_NULL = "This GameLobby should not be NULL.";
    /**
     * This {@link Game} should not be NULL.
     */
    private final String BOARDVIEW_SHOULD_NOT_BE_NULL = "This BoardView should not be NULL.";
    /**
     * The {@link Player} should be the RED {@link Player}.
     */
    private final String PLAYER_SHOULD_BE_RED = "The Player should be the RED Player.";
    /**
     * The {@link Player} should be the WHITE {@link Player}.
     */
    private final String PLAYER_SHOULD_BE_WHITE = "The Player should be the WHITE Player.";
    /**
     * This {@link BoardView} did not update correctly.
     */
    private final String BOARDVIEW_DID_NOT_UPDATE = "This BoardView did not update correctly.";
    /**
     * It should be the red {@link Player}'s turn.
     */
    private final String SHOULD_BE_RED_PLAYERS_TURN = "It should be the red Player's turn.";
    /**
     * It should be the white {@link Player}'s turn.
     */
    private final String SHOULD_BE_WHITE_PLAYERS_TURN = "It should be the white Player's turn.";
    /**
     * Active {@link Color} should be RED.
     */
    private final String ACTIVE_COLOR_SHOULD_BE_RED = "Active Color should be RED.";
    /**
     * Active {@link Color} should be WHITE.
     */
    private final String ACTIVE_COLOR_SHOULD_BE_WHITE = "Active Color should be WHITE.";
    /**
     * It should be game over.
     */
    private final String SHOULD_BE_GAME_OVER = "It should be game over.";
    /**
     * It should not be game over.
     */
    private final String SHOULD_NOT_BE_GAME_OVER = "It should not be game over.";
    /**
     * This {@link Player} should be the winner.
     */
    private final String PLAYER_SHOULD_BE_THE_WINNER = "This Player should be the winner.";
    /**
     * This {@link Player} should be the loser.
     */
    private final String PLAYER_SHOULD_BE_THE_LOSER = "This Player should be the loser.";
    /**
     * This {@link Player} should not be able to resign before their turn.
     */
    private final String PLAYER_SHOULD_NOT_BE_ABLE_TO_RESIGN = "This Player should not be able to resign before their turn.";
    /**
     * This {@link Player} should be able to resign when it is their turn.
     */
    private final String PLAYER_SHOULD_BE_ABLE_TO_RESIGN = "This Player should be able to resign when it is their turn.";
    /**
     * The winner should be NULL.
     */
    private final String WINNER_SHOULD_BE_NULL = "The winner should be NULL.";
    /**
     * The winner should not be NULL.
     */
    private final String WINNER_SHOULD_NOT_BE_NULL = "The winner should not be NULL.";
    /**
     * The loser should be NULL.
     */
    private final String LOSER_SHOULD_BE_NULL = "The loser should be NULL.";
    /**
     * The winner should not be NULL.
     */
    private final String LOSER_SHOULD_NOT_BE_NULL = "The loser should not be NULL.";

    // Public Methods
    /**
     * The constructor test for {@link Game}.
     *
     * Tests construction and instantiation.
     */
    @Test
    public void constructorTest() {
        //test construction
        final Game CuT = new Game(new Player("User1"), new Player("User2"));
        assertNotNull(CuT, SHOULD_NOT_BE_NULL);
    }
    /**
     * The test for the {@link Player} fields in a {@link Game} object.
     *
     * Tests proper field assignments.
     */
    @Test
    public void getPlayersTest() {
        final Player whitePlayer = new Player("White");
        final Player redPlayer = new Player("Red");
        final Game CuT = new Game(redPlayer, whitePlayer);

        //test current player
        assertEquals(redPlayer, CuT.getRedPlayer(), PLAYER_SHOULD_BE_RED);
        assertEquals(whitePlayer, CuT.getWhitePlayer(), PLAYER_SHOULD_BE_WHITE);

        //test other player
        assertEquals(whitePlayer, CuT.getOtherPlayer(redPlayer), PLAYER_SHOULD_BE_WHITE);
        assertEquals(redPlayer, CuT.getOtherPlayer(whitePlayer), PLAYER_SHOULD_BE_RED);
    }
    /**
     * The test for the isRedTurn field in a {@link Game} object.
     *
     * Tests proper field assignments and modification.
     */
    @Test
    public void toggleTurnsTest() {
        final Player whitePlayer = new Player("White");
        final Player redPlayer = new Player("Red");
        final Game CuT = new Game(redPlayer, whitePlayer);

        //test turns before toggle
        assertTrue(CuT.isPlayersTurn(redPlayer), SHOULD_BE_RED_PLAYERS_TURN);
        assertFalse(CuT.isPlayersTurn(whitePlayer), SHOULD_BE_RED_PLAYERS_TURN);
        assertEquals(CuT.getActiveColor(), Color.RED, ACTIVE_COLOR_SHOULD_BE_RED);

        CuT.toggle();

        //test turns after toggle
        assertTrue(CuT.isPlayersTurn(whitePlayer), SHOULD_BE_WHITE_PLAYERS_TURN);
        assertFalse(CuT.isPlayersTurn(redPlayer), SHOULD_BE_WHITE_PLAYERS_TURN);
        assertEquals(CuT.getActiveColor(), Color.WHITE, ACTIVE_COLOR_SHOULD_BE_WHITE);
    }
    /**
     * The test for the {@link BoardView} fields in a {@link Game} object.
     *
     * Tests proper field assignment.
     */
    @Test
    public void getBoardTest() {
        final Player whitePlayer = new Player("White");
        final Player redPlayer = new Player("Red");
        final Game CuT = new Game(redPlayer, whitePlayer);

        //test board assignments
        assertNotNull(CuT.getBoard(redPlayer), BOARDVIEW_SHOULD_NOT_BE_NULL);
        assertNotNull(CuT.getBoard(whitePlayer), BOARDVIEW_SHOULD_NOT_BE_NULL);
    }
    /**
     * The test for the submitMove() method in a {@link Game} object.
     *
     * Tests that a {@link Move} is properly translated to the {@link BoardView}
     * of each {@link Player}.
     */
    @Test
    public void submitMoveTest() {
        final Player whitePlayer = new Player("White");
        final Player redPlayer = new Player("Red");
        final Game CuT = new Game(redPlayer, whitePlayer);

        //generate white move
        final Position whiteStart = new Position(5, 4);
        final Position whiteEnd = new Position(4, 3);
        final Move whiteMove = new Move(whiteStart, whiteEnd);
        BoardView whiteBoard = CuT.getBoard(whitePlayer);
        BoardView redBoard = CuT.getBoard(redPlayer);
        CuT.setStagedMove(whiteMove);

        //test white move
        CuT.submit(whitePlayer);
        assertEquals(whiteBoard, CuT.getBoard(whitePlayer), BOARDVIEW_DID_NOT_UPDATE);
        assertEquals(redBoard, CuT.getBoard(redPlayer), BOARDVIEW_DID_NOT_UPDATE);

        //generate red move
        final Position redStart = new Position(5, 2);
        final Position redEnd = new Position(4, 1);
        final Move redMove = new Move(redStart, redEnd);
        redBoard = CuT.getBoard(redPlayer);
        whiteBoard = CuT.getBoard(whitePlayer);
        CuT.setStagedMove(redMove);

        //test red move
        CuT.submit(redPlayer);
        assertEquals(whiteBoard, CuT.getBoard(whitePlayer), BOARDVIEW_DID_NOT_UPDATE);
        assertEquals(redBoard, CuT.getBoard(redPlayer), BOARDVIEW_DID_NOT_UPDATE);
    }
    /**
     * The test for the resignGame() method in a {@link Game} object.
     *
     * Tests that a {@link Player} is able to end a game early with them
     * as the loser and their opponent as a winner only if it is their turn.
     */
    @Test
    public void resignGameTest() {
        final Player whitePlayer = new Player("White");
        final Player redPlayer = new Player("Red");
        final Game CuT = new Game(redPlayer, whitePlayer);

        CuT.toggle();

        //test that the player cannot resign when it is not their turn
        assertFalse(CuT.canResignGame(redPlayer), PLAYER_SHOULD_NOT_BE_ABLE_TO_RESIGN);
        assertNull(CuT.getWinner(), WINNER_SHOULD_BE_NULL);
        assertNull(CuT.getLoser(), LOSER_SHOULD_BE_NULL);
        assertFalse(CuT.getGameOver(), SHOULD_NOT_BE_GAME_OVER);

        //test that the player can resign when it is their turn
        assertTrue(CuT.canResignGame(whitePlayer), PLAYER_SHOULD_BE_ABLE_TO_RESIGN);
        assertNotNull(CuT.getWinner(), WINNER_SHOULD_NOT_BE_NULL);
        assertNotNull(CuT.getLoser(), LOSER_SHOULD_NOT_BE_NULL);
        assertTrue(CuT.getGameOver(), SHOULD_BE_GAME_OVER);

        //test the winner and loser
        assertEquals(redPlayer, CuT.getWinner(), PLAYER_SHOULD_BE_THE_WINNER);
        assertEquals(whitePlayer, CuT.getLoser(), PLAYER_SHOULD_BE_THE_LOSER);

        //checks for correct game over reason
        assertEquals(CuT.getGameOverReason(),"resignation");

        //Test players leaving tracking
        CuT.playerLeft();
        assertEquals(CuT.playersLeft(),1);
    }

    @Test
    public void gameOverTest() {
        final Player whitePlayer = new Player("White");
        final Player redPlayer = new Player("Red");
        final Game CuT = new Game(redPlayer, whitePlayer, BoardSeed.SIMPLE_MOVE);
        Position start = new Position(5,4);
        Position end = new Position(4,3);
        Move move1 = new Move(start,end);
        start = new Position(4,3);
        end = new Position(3,2);
        Move move2 = new Move(start,end);
        start = new Position(3,2);
        end = new Position(1,4);
        Move move3 = new Move(start,end);
        CuT.setStagedMove(move1);
        CuT.submit(redPlayer);
        CuT.setStagedMove(move2);
        CuT.submit(redPlayer);
        CuT.setStagedMove(move3);
        CuT.submit(redPlayer);
        assertTrue(CuT.getGameOver(), SHOULD_BE_GAME_OVER);
        assertEquals(CuT.getGameOverReason(),"pieces");
    }
}