package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.ReplayLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.util.Color;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;


public class GetStopWatchingRoute implements Route {
    /**
     * The {@link TemplateEngine} that renders the view.
     */
    private final TemplateEngine templateEngine;
    /**
     * The {@link PlayerLobby} for the {@link WebServer}.
     */
    private final PlayerLobby playerLobby = WebServer.playerLobby;
    /**
     * The {@link Gson} JSON interpreter.
     */
    private final Gson gson = new Gson();
    /**
     * The {@link Logger} that tracks {@link GetStopWatchingRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(GetStopWatchingRoute.class.getName());

    private final ReplayLobby replayLobby = WebServer.replayLobby;

    /**
     * The {@link GameLobby} that holds {@link Game} objects.
     */
    private final GameLobby gameLobby = WebServer.gameLobby;


    public GetStopWatchingRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        LOG.config("GetStopWatchingRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String gameKey = request.queryParams("gameID");
        Player currentUser = request.session().attribute("currentUser"); // grabs the user of the session
        Replay currentReplay = replayLobby.getReplay(gameKey);
        Color userColor = currentUser.equals(currentReplay.getWhitePlayer()) ? Color.WHITE : Color.RED;
        replayLobby.getReplay(gameKey).reset(userColor);
        response.redirect("/");
        return null;
    }
}
