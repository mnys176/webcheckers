package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;

public class TurnServices {
    public static void submitTurn(Game game, Player player) {
        Position end = game.getStagedMove().getEnd();
        game.submit(player);
        if (!game.getMultiCapture()) {
            game.toggle();
            game.setOnlyValidAttacker(null);
        }else{
            game.setOnlyValidAttacker(end);
        }
        game.getStats().addMove();
    }

    public static boolean isMyTurn(Game game, Player player) {
        return game.isPlayersTurn(player);
    }

    public static int validateTurn(Game game, Player player, Move move) {
        int status = move.validate(game.getBoard(player), game);
        if (status == 0 || status == 1) game.setStagedMove(move);
        return status;
    }
}
