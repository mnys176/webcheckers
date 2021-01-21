package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.ReplayLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Color;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.logging.Logger;

public class PostReplayLastTurnRoute implements Route {
    // Private Fields
    /**
     * The {@link Gson} JSON interpreter.
     */
    private final Gson gson = new Gson();
    /**
     * The {@link Logger} that tracks {@link PostReplayLastTurnRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(PostReplayLastTurnRoute.class.getName());
    /**
     * The {@link GameLobby} that holds {@link com.webcheckers.model.Game} objects.
     */
    private final GameLobby gameLobby = WebServer.gameLobby;

    private final ReplayLobby replayLobby = WebServer.replayLobby;


    // Constructors
    /**
     * The constructor for a {@link PostReplayLastTurnRoute} object.
     */
    public PostReplayLastTurnRoute() {
        LOG.config("PostReplayLastTurnRoute is initialized.");
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String gameKey = request.queryParams("gameID");
        Player currentUser = request.session().attribute("currentUser"); // grabs the user of the session
        Replay currentReplay = replayLobby.getReplay(gameKey);
        Color userColor = currentUser.equals(currentReplay.getWhitePlayer()) ? Color.WHITE : Color.RED;
        replayLobby.getReplay(gameKey).incrementCurrentPos(false,userColor);
        replayLobby.getReplay(gameKey).setFrame(userColor);
        response.body(Message.info("true").toString());
        return gson.toJson(Message.info("true"));
    }
}
