package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameLobby;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

public class PostBackupMoveRoute implements Route {
    // Private Fields
    /**
     * The {@link Gson} JSON interpreter.
     */
    private final Gson gson = new Gson();
    /**
     * The {@link Logger} that tracks {@link PostBackupMoveRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());

    // Constructors

    /**
     * The constructor for a {@link PostBackupMoveRoute} object.
     */
    public PostBackupMoveRoute() {
        LOG.config("PostBackupMoveRoute is initialized.");
    }

    /**
     * The {@link GameLobby} that holds {@link com.webcheckers.model.Game} objects.
     */
    private final GameLobby gameLobby = WebServer.gameLobby;

    /**
     * Handles POST /backupMove.
     *
     * @param request
     *         the HTTP request
     * @param response
     *         the HTTP response
     *         Sets the active player's staged {@link com.webcheckers.model.Move} to NULL.
     *
     * @return the status message in JSON
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostBackupMoveRoute is invoked.");
        String gameKey = request.queryParams("gameID");
        gameLobby.getGame(gameKey).setStagedMove(null);
        response.body(Message.info("Move was undone.").toString());
        return gson.toJson(Message.info("Move was undone."));
    }
}
