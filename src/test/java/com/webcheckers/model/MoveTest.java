package com.webcheckers.model;

import com.webcheckers.application.TurnServices;
import com.webcheckers.util.Color;
import com.webcheckers.util.Type;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for the {@link Move} class.
 *
 * @author Mike Nystoriak
 */
@Tag("Model-tier")
public class MoveTest {
    // Private Fields
    /**
     * This {@link Move} should not be NULL.
     */
    private final String SHOULD_NOT_BE_NULL = "This Move should not be NULL.";
    /**
     * This {@link Move} has the wrong starting {@link Position}.
     */
    private final String WRONG_STARTING_POSITION = "This Move has the starting Position.";
    /**
     * This {@link Move} has the wrong ending {@link Position}.
     */
    private final String WRONG_ENDING_POSITION = "This Move has the wrong ending Position.";
    private final String SHOULD_BE_KING = "Should be a king.";
    private final String SHOULD_NOT_BE_KING = "Should not be a king.";
    private final String SHOULD_BE_VALID = "Move should be valid.";
    private final String SHOULD_BE_INVALID = "Move should be invalid.";
    private final String SHOULD_BE_CAPTURE = "Move should be a capture.";
    private final String SHOULD_NOT_BE_CAPTURE = "Move should not be a capture.";
    private final String SHOULD_BE_CAN_CAPTURE = "Should be able to capture.";
    private final String SHOULD_BE_CANNOT_CAPTURE = "Should not be able to capture.";
    private final String SHOULD_BE_VALID_CAPTURE = "Move should be valid capture.";
    private final String SHOULD_NOT_BE_VALID_CAPTURE = "Move should not be valid capture.";
    private final String SHOULD_BE_CAPTURE_AVAILABLE = "Should be capture available on board.";
    private final String SHOULD_BE_NO_CAPTURE_AVAILABLE = "Should be no capture available on board.";

    @Test
    public void multiCaptureTest(){
        Player redPlayer = new Player("Red");
        Player whitePlayer = new Player("White");
        Game game = new Game(redPlayer,whitePlayer);
        TurnServices CuT = new TurnServices();
        BoardView redBoard = BoardView.generateBoard(Color.RED);
        Piece victim = new Piece(Type.SINGLE,Color.WHITE);
        Piece redKing = new Piece(Type.KING,Color.RED);
        Piece redBystander = new Piece(Type.SINGLE,Color.RED);
        Move CuTA = new Move(new Position(5,4),new Position(3,2));
        Move CuTB = new Move(new Position(3,2),new Position(1,4));

        redBoard.getSpace(4,3).placePiece(victim);
        redBoard.getSpace(1,4).liftPiece();
        assertEquals(1,CuTA.validate(redBoard,game),SHOULD_BE_VALID_CAPTURE);
        redBoard.getSpace(3,2).placePiece(redBystander);
        redBoard.getSpace(5,4).liftPiece();
        game.toggle();
        assertEquals(0,CuTB.validate(redBoard,game),SHOULD_BE_VALID_CAPTURE);

        redBoard.getSpace(3,2).liftPiece();
        redBoard.getSpace(1,4).placePiece(redKing);
        Move CuTC = new Move(new Position(1,4),new Position(3,2));
        Move CuTD = new Move(new Position(3,2),new Position(5,4));
        assertEquals(1,CuTC.validate(redBoard,game),SHOULD_BE_VALID_CAPTURE);

        game.toggle();
        redBoard.getSpace(3,2).placePiece(redKing);
        assertEquals(0,CuTD.validate(redBoard,game),SHOULD_BE_VALID_CAPTURE);
    }

    @Test
    public void validateMoveTest(){
        Player redPlayer = new Player("Red");
        Player whitePlayer = new Player("White");
        Game game = new Game(redPlayer,whitePlayer);
        //MoveServices CuT = new MoveServices();
        BoardView redBoard = BoardView.generateBoard(Color.RED);
        Piece victim = new Piece(Type.SINGLE,Color.WHITE);

        //Move validMove = new Move(new Position(5,2),new Position(4,3));
        final Move CuTA = new Move(new Position(5,2),new Position(4,3));
        assertEquals(0,CuTA.validate(redBoard,game),SHOULD_BE_VALID);

        final Move CuTB = new Move(new Position(5,2),new Position(3,3));
        assertEquals(3,CuTB.validate(redBoard,game),SHOULD_BE_INVALID);

        redBoard.getRow(4).getSpace(3).placePiece(victim);
        redBoard.getRow(1).getSpace(2).liftPiece();

        final Move CuTC = new Move(new Position(5,2),new Position(3,4));
        assertEquals(1,CuTC.validate(redBoard,game),SHOULD_BE_VALID);

        game.toggle();
        game.setStagedMove(CuTC);
        assertEquals(2,CuTC.validate(redBoard,game),SHOULD_BE_INVALID);

        final Move CuTD = new Move(new Position(5,2),new Position(4,1));
        game.setStagedMove(null);
        assertEquals(0,CuTD.validate(redBoard,game),SHOULD_BE_CAPTURE);
    }

    /**
     * The constructor test for {@link Move}.
     *
     * Tests construction, instantiation, and field assignments.
     */
    @Test
    public void constructorTest() {
        final Position startingPositionA = new Position(2, 5);
        final Position endingPositionA = new Position(3, 4);
        final Position startingPositionB = new Position(5, 2);
        final Position endingPositionB = new Position(4, 3);

        final Move CuTA = new Move(startingPositionA, endingPositionA);
        final Move CuTB = new Move(startingPositionB, endingPositionB);

        //test construction
        assertNotNull(CuTA, SHOULD_NOT_BE_NULL);
        assertNotNull(CuTB, SHOULD_NOT_BE_NULL);

        //test starting position
        assertEquals(startingPositionA.getRow(), CuTA.getStart().getRow(), WRONG_STARTING_POSITION);
        assertEquals(startingPositionA.getCell(), CuTA.getStart().getCell(), WRONG_STARTING_POSITION);
        assertEquals(startingPositionB.getRow(), CuTB.getStart().getRow(), WRONG_STARTING_POSITION);
        assertEquals(startingPositionB.getCell(), CuTB.getStart().getCell(), WRONG_STARTING_POSITION);

        //test ending position
        assertEquals(endingPositionA.getRow(), CuTA.getEnd().getRow(), WRONG_ENDING_POSITION);
        assertEquals(endingPositionA.getCell(), CuTA.getEnd().getCell(), WRONG_ENDING_POSITION);
        assertEquals(endingPositionB.getRow(), CuTB.getEnd().getRow(), WRONG_ENDING_POSITION);
        assertEquals(endingPositionB.getCell(), CuTB.getEnd().getCell(), WRONG_ENDING_POSITION);
    }
}
