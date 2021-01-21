package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameLobby;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

/**
 * The UI Controller to POST /resignGame
 *
 * @author Jack Gilbert
 */
public class PostResignGameRoute implements Route {
    // Private Fields
    /**
     * The {@link Gson} JSON interpreter.
     */
    private final Gson gson = new Gson();
    /**
     * The {@link Logger} that tracks {@link PostResignGameRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(PostResignGameRoute.class.getName());
    /**
     * The {@link GameLobby} that holds {@link com.webcheckers.model.Game} objects.
     */
    private final GameLobby gameLobby = WebServer.gameLobby;

    // Constructors

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /resignGame} HTTP requests.
     */
    public PostResignGameRoute() {
        LOG.config("PostResignGameRoute is initialized.");
    }

    // Public Methods

    /**
     * Handles POST /resignGame.
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
        LOG.finer("PostResignGameRoute is invoked.");
        String gameKey = request.queryParams("gameID");
        boolean canResignGame = gameLobby.getGame(gameKey).canResignGame(request.session().attribute("currentUser"));
        Message message = canResignGame ? Message.info("Successful resignation.") : Message.error("You cannot resign when its not your turn.");
        response.body(message.toString());
        return gson.toJson(message);
    }
}
