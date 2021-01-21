package com.webcheckers.ui;

import com.webcheckers.application.GameLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.ReplayLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Home page.
 *
 * @author Mike Nystoriak
 * @author Jack Gilbert
 * @author Aaron Segal
 * @author Caleb Eldridge
 */
public class GetHomeRoute implements Route {
    // Private fields
    /**
     * The title attribute for FreeMarker.
     */
    private final String TITLE_ATTR = "title";
    /**
     * The message attribute for FreeMarker.
     */
    private final String MSG_ATTR = "message";
    /**
     * The currentUser attribute for FreeMarker.
     */
    private final String CURRENT_USER_ATTR = "currentUser";
    /**
     * The playersOnline attribute for FreeMarker.
     */
    private final String USER_COUNT_ATTR = "playersOnline";
    /**
     * Welcome to the world of online Checkers.
     */
    private final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
    /**
     * The title for the view.
     */
    private final String TITLE = "Welcome!";
    /**
     * The filename for the view.
     */
    private final String VIEW_NAME = "home.ftl";
    /**
     * The {@link TemplateEngine} that renders the view.
     */
    private final TemplateEngine templateEngine;
    /**
     * The {@link PlayerLobby} for the {@link WebServer}.
     */
    private final PlayerLobby playerLobby = WebServer.playerLobby;
    /**
     * The {@link Logger} that tracks {@link GetHomeRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final GameLobby gameLobby = WebServer.gameLobby;

    private final ReplayLobby replayLobby = WebServer.replayLobby;



    // Constructors
    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *         the HTML template rendering engine
     */
    public GetHomeRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        LOG.config("GetHomeRoute is initialized.");
    }

    // Public methods
    /**
     * Render the WebCheckers Home page.
     *
     * @param request
     *         the HTTP request
     * @param response
     *         the HTTP response
     *
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        Session session = request.session();

        // display a user message in the Home page
        vm.put(MSG_ATTR, WELCOME_MSG);
        vm.put(TITLE_ATTR, TITLE);

        //determine what the home view should display
        if (session.attribute("currentUser") == null) {
            int playerCount = playerLobby.getNumberOfUsers();
            vm.put(USER_COUNT_ATTR, playerCount);
        } else {
            Player player = session.attribute("currentUser");
            vm.put(CURRENT_USER_ATTR, player);
            vm.put("users", this.playerLobby.getUsersSorted());
            vm.put("replays",this.replayLobby.getReplays(true));
            vm.put("active",this.gameLobby.getMyGames(player,false));
            vm.put("friend",this.replayLobby.getFavoriteOpponent(player));
        }

        // render the View
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
