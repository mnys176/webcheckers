package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.TurnServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

/**
 * The UI Controller to POST /checkTurn
 *
 * @author Jack Gilbert
 */
public class PostCheckTurnRoute implements Route {
    // Private Fields
    /**
     * The {@link Gson} JSON interpreter.
     */
    private final Gson gson = new Gson();
    /**
     * The {@link Logger} that tracks {@link PostCheckTurnRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());
    /**
     * The {@link GameLobby} that holds {@link com.webcheckers.model.Game} objects.
     */
    private final GameLobby gameLobby = WebServer.gameLobby;

    // Constructors
    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /checkTurn} HTTP requests.
     */
    public PostCheckTurnRoute() {
        LOG.config("PostCheckTurnRoute is initialized.");
    }

    // Public Methods
    /**
     * Check the turn of a user.
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
        LOG.finer("PostCheckTurnRoute is invoked.");
        String gameKey = request.queryParams("gameID");
        boolean isMyTurn = TurnServices.isMyTurn(gameLobby.getGame(gameKey), request.session().attribute("currentUser"));

        //if it is your turn or the game is over return TRUE
        String messageText = gameLobby.getGame(gameKey).getGameOver() || isMyTurn ? "true" : "false";
        response.body(Message.info(messageText).toString());
        return gson.toJson(Message.info(messageText));
    }
}

