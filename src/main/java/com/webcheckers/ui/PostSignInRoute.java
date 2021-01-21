package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The UI Controller to POST /signin.
 *
 * @author Jack Gilbert
 */
public class PostSignInRoute implements Route {
    // Private Fields
    /**
     * The {@link TemplateEngine} that renders the view.
     */
    private final TemplateEngine templateEngine;
    /**
     * The {@link PlayerLobby} for the {@link WebServer}.
     */
    private final PlayerLobby playerLobby = WebServer.playerLobby;
    /**
     * You have entered a duplicate username.
     */
    private final Message DUPE_MSG = Message.error("You have entered a duplicate username, please enter a new one");
    /**
     * Enter a proper username.
     */
    private final Message INVALID_MSG = Message.error("Enter an alphanumeric name that is no more than 13 characters, the first character must be a letter with no leading or trailing spaces");
    /**
     * HTTP POST parameter.
     */
    private final String USERNAME_PARAM = "username";

    // Constructors
    /**
     * constructor for after user types in username
     *
     * @param templateEngine
     *         the templateEngine that interprets the template for this route
     */
    public PostSignInRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    }

    // Public Methods
    /**
     * Handles and stores the player's preferred username or gives player and error for issues with username.
     *
     * @param request
     *         the HTTP request
     * @param response
     *         the HTTP response
     *
     * @return either a sign in page with an error or a redirect to the home page
     */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        String userName = request.queryParams(USERNAME_PARAM); // gets the username from user input
        String nameResponse = playerLobby.userSignIn(userName); // adds user to lobby and gets response of what happened
        switch (nameResponse) { // handles codes sent by player lobby about the login
            case "VALID":
                final Session session = request.session(); // creates a session to store user's name
                session.attribute("currentUser", playerLobby.getUser(userName));
                response.redirect("/"); // redirects to home page with user info
                break;
            case "DUPE_INVALID": // handles duplicate names in lobbies, generates sign in page with error msg
                vm.put("title", "");
                vm.put("message", DUPE_MSG);
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
            case "NAME_INVALID": // handles invalid inputted in sign in page, generates sign in page with error msg
                vm.put("title", "");
                vm.put("message", INVALID_MSG);
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
            default:
                return null;
        }
        return null;
    }
}
