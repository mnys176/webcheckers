package com.webcheckers.application;

import com.webcheckers.model.*;
import com.webcheckers.util.Color;
import com.webcheckers.util.Type;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TurnServicesTest {
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
        final Move moveA = new Move(new Position(5,2),new Position(4,3));
        //assertEquals(0,CuTA.validate(redBoard,game),SHOULD_BE_VALID);
        assertEquals(0, TurnServices.validateTurn(game, redPlayer, moveA));
        assertTrue(TurnServices.isMyTurn(game, redPlayer));
        if (TurnServices.isMyTurn(game, redPlayer)) TurnServices.submitTurn(game, redPlayer);

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
        assertEquals(4,CuTD.validate(redBoard,game),SHOULD_BE_CAPTURE);
    }

}
