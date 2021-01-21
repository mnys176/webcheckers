package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.ReplayLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.ViewMode;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the game page.
 *
 * @author Mike Nystoriak
 * @author Jack Gilbert
 * @author Aaron Segal
 * @author Caleb Eldridge
 */
public class GetGameRoute implements Route {
    // Private Fields
    /**
     * The title of the view.
     */
    private final String VIEW_TITLE = "WebCheckers | Game View";
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
     * The {@link Logger} that tracks {@link GetGameRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());
    /**
     * The {@link GameLobby} that holds {@link Game} objects.
     */
    private final GameLobby gameLobby = WebServer.gameLobby;

    private final ReplayLobby replayLobby = WebServer.replayLobby;

    // Constructors

    /**
     * Create the Spark Route (UI controller) to handle all GET HTTP requests.
     *
     * @param templateEngine
     *         the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        LOG.config("GetGameRoute is initialized.");
    }

    // Private Methods

    /**
     * Check if game is over and make changes to vm if needed
     *
     * @param gameKey
     *         the string that identifies the corresponding game
     * @param vm
     *         the view-Model being filled
     */
    private void isGameOver(String gameKey, Map<String, Object> vm) {
        Game game = gameLobby.getGame(gameKey);
        if (game.getGameOver()) {
            final Map<String, Object> modeOptions = new HashMap<>(2);
            modeOptions.put("isGameOver", true);
            game.getLoser().modifyELO(false,game.getWinner());
            game.getWinner().modifyELO(true,game.getLoser());
            game.getLoser().addLoss();
            game.getWinner().addWin();
            game.setActive(false);
            game.getStats().setWinner(game.getWinner());
            if (game.getGameOverReason().equals("resignation")) {
                game.getStats().winByResignation();
                game.getStats().setWinnerPiecesLeft(game.getWinnerPiecesLeft());
                modeOptions.put("gameOverMessage", game.getLoser().getName() + " resigned, " + game.getWinner().getName() + " is victorious!");
            } else if (game.getGameOverReason().equals("pieces")) {
                modeOptions.put("gameOverMessage", game.getLoser().getName() + " was defeated, " + game.getWinner().getName() + " is victorious!");
            }
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        }
    }

    /**
     * Check if game is over and make changes to vm if needed
     *
     * @param game
     *         the {@link Game} object being used
     * @param currentUser
     *         the {@link Player} that represents the current user
     * @param vm
     *         the view-Model being filled
     */
    private void fillVM(Game game, Player currentUser, Map<String, Object> vm) {
        vm.put("currentUser", currentUser);
        vm.put("title", "Let's Play!");
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getActiveColor());
        vm.put("viewMode", ViewMode.PLAY);
        vm.put("board", game.getBoard(currentUser));
    }

    // Public Methods

    /**
     * Render the WebCheckers Game Page.
     *
     * @param request
     *         the HTTP request
     * @param response
     *         the HTTP response
     *
     * @return the rendered HTML for the Game Page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", VIEW_TITLE);
        Player currentUser = request.session().attribute("currentUser");
        vm.put("active",this.gameLobby.getMyGames(currentUser,false));
        String gameKey = null;

        if (request.queryParams("gameID") == null) {
            Player opponent = playerLobby.getUser(request.queryParams("opponent"));
            gameLobby.createGameIfNoGameExists(currentUser, opponent);
            gameKey = gameLobby.getKey(currentUser, opponent);
        } else if (gameLobby.getGame(request.queryParams("gameID")) == null) {
            response.status(404);
            return null;
        } else
            gameKey = request.queryParams("gameID");

        Game game = gameLobby.getGame(gameKey);
        try{
            replayLobby.getReplay(gameKey).size();
            System.out.println("Replay exists!");
        }
        catch (NullPointerException npe){
            replayLobby.addReplay(game.getRedPlayer(),game.getWhitePlayer(),gameKey);
            System.out.println("Replay created!");
        }
        this.fillVM(game, currentUser, vm);
        vm.put("gameID", gameKey);
        this.isGameOver(gameKey, vm);
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}
