package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET to Sign in page.
 *
 * @author Mike Nystoriak
 * @author Jack Gilbert
 * @author Aaron Segal
 * @author Caleb Eldridge
 */
public class GetSignInRoute implements Route {
    // Private Fields
    /**
     * The {@link TemplateEngine} that renders the view.
     */
    private final TemplateEngine templateEngine;
    /**
     * The title attribute for FreeMarker.
     */
    private final String TITLE_ATTR = "title";
    /**
     * The message attribute for FreeMarker.
     */
    private final String SIGN_IN_ATTR = "message";
    /**
     * Enter a new username.
     */
    private final Message SIGN_IN_MESSAGE = Message.info("Enter a new username");
    /**
     * The {@link Logger} that tracks {@link GetSignInRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    // Constructor
    /**
     * Create the Spark Route (UI controller) to handle all HTTP requests.
     *
     * @param templateEngine
     *         the HTML template rendering engine
     */
    public GetSignInRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        LOG.config("GetSignInRoute is initialized.");
    }

    // Public Methods
    /**
     * Render the WebCheckers sign in page.
     *
     * @param request
     *         the HTTP request
     * @param response
     *         the HTTP response
     *
     * @return the rendered HTML for the sign in page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignInRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, "");

        // display a user message in the Home page
        vm.put(SIGN_IN_ATTR, SIGN_IN_MESSAGE);
        
        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
}
