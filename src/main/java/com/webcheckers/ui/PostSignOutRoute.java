package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostSignOutRoute implements Route {
    // Private Fields
    /**
     * The {@link TemplateEngine} that renders the view.
     */
    private final TemplateEngine templateEngine;
    /**
     * The {@link Logger} that tracks {@link GetSignInRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());
    /**
     * The {@link PlayerLobby} for the {@link WebServer}.
     */
    private final PlayerLobby playerLobby = WebServer.playerLobby;

    // Constructors
    /**
     * constructor for after user presses the nav bar sign out
     *
     * @param templateEngine
     *         the templateEngine that interprets the template for this route
     */
    public PostSignOutRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        LOG.config("PostSignOutRoute is initialized.");
    }

    // Public Methods
    /**
     * Render the WebCheckers sign out page.
     *
     * @param request
     *         the HTTP request
     * @param response
     *         the HTTP response
     *
     * @return a redirect to an inactive homepage
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSignOutRoute is invoked.");
        Player currentUser = request.session().attribute("currentUser");
        playerLobby.userSignOut(currentUser.getName());
        request.session().removeAttribute("currentUser");
        response.redirect("/");
        return null;
    }
}
