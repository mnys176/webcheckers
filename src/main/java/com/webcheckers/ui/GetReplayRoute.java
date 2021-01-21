package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.ReplayLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.util.Color;
import com.webcheckers.util.ViewMode;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetReplayRoute implements Route {
    private final String VIEW_TITLE = "WebCheckers | Replay View";
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
     * The {@link Logger} that tracks {@link GetReplayRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(GetReplayRoute.class.getName());
    /**
     * The {@link GameLobby} that holds {@link Game} objects.
     */
    private final GameLobby gameLobby = WebServer.gameLobby;

    private final ReplayLobby replayLobby = WebServer.replayLobby;


    public GetReplayRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        LOG.config("GetReplayRoute is initialized.");
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetReplayRoute is invoked.");
        String gameKey = request.queryParams("gameID");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", VIEW_TITLE);
        Player currentUser = request.session().attribute("currentUser"); // grabs the user of the session
        Replay currentReplay = replayLobby.getReplay(gameKey);
        Color userColor = currentUser.equals(currentReplay.getWhitePlayer()) ? Color.WHITE : Color.RED;
        fillVM(currentReplay,currentUser,vm,userColor);
        vm.put("gameID",request.queryParams("gameID"));
        vm.put("viewMode","REPLAY");
        Map<String,Object> modeOptionsAsJSON = new HashMap<>();
        modeOptionsAsJSON.put("hasNext",currentReplay.hasNext(userColor));
        modeOptionsAsJSON.put("hasPrevious",currentReplay.hasPrevious(userColor));
        vm.put("modeOptionsAsJSON",gson.toJson(modeOptionsAsJSON));
        vm.put("stats",gameLobby.getGame(gameKey).getStats().getData());
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }

    private void fillVM(Replay replay, Player currentUser, Map<String, Object> vm,Color userColor) {
        vm.put("currentUser", currentUser);
        vm.put("title", "Let's Play!");
        vm.put("redPlayer", replay.getRedPlayer());
        vm.put("whitePlayer", replay.getWhitePlayer());
        vm.put("activeColor", replay.getActiveColor());
        vm.put("viewMode", ViewMode.PLAY);
        vm.put("board",replay.getReplayBoard(userColor));
    }
}
