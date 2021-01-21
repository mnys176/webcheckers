package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.ReplayLobby;
import com.webcheckers.application.TurnServices;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Color;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

/**
 * The UI Controller to POST /submitTurn.
 *
 * @author Jack Gilbert
 */
public class PostSubmitTurnRoute implements Route {
    // Private Fields
    /**
     * The {@link Gson} JSON interpreter.
     */
    private final Gson gson = new Gson();
    /**
     * The {@link Logger} that tracks {@link PostSubmitTurnRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());
    /**
     * The {@link GameLobby} that holds {@link com.webcheckers.model.Game} objects.
     */
    private final GameLobby gameLobby = WebServer.gameLobby;

    private final ReplayLobby replayLobby = WebServer.replayLobby;

    // Constructors
    /**
     * The constructor for a {@link PostSubmitTurnRoute} object.
     */
    public PostSubmitTurnRoute() {
        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    // Public Methods
    /**
     * Handles POST /submitTurn.
     *
     * @param request
     *         the HTTP request
     * @param response
     *         the HTTP response
     *
     * @return the status message in JSON
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSubmitTurnRoute is invoked.");
        String gameKey = request.queryParams("gameID");
        TurnServices.submitTurn(gameLobby.getGame(gameKey), request.session().attribute("currentUser"));
        boolean stillHaveCapture = TurnServices.isMyTurn(gameLobby.getGame(gameKey), request.session().attribute("currentUser"));
        replayLobby.getReplay(gameKey).addBoardView(gameLobby.getGame(gameKey).getBoard(gameLobby.getGame(gameKey).getRedPlayer()).cloneBoardView(), Color.RED);
        replayLobby.getReplay(gameKey).addBoardView(gameLobby.getGame(gameKey).getBoard(gameLobby.getGame(gameKey).getWhitePlayer()).cloneBoardView(), Color.WHITE);
        if (stillHaveCapture) response.body(Message.info("Submitting move...").toString());
        else response.body(Message.error("You have another capture.").toString());

        return gson.toJson(Message.info("Done"));
    }
}
