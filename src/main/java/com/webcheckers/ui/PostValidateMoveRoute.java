package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameLobby;
import com.webcheckers.application.TurnServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    // Private Fields
    /**
     * The {@link Gson} JSON interpreter.
     */
    private final Gson gson = new Gson();
    /**
     * The valid move {@link Message} object.
     */
    private final Message VALID_MSG = Message.info("Valid Move");
    private final Message DOUBLE_MSG = Message.info("Submit your turn, then finish your captures!");
    /**
     * The invalid move {@link Message} object.
     */
    private final Message INVALID_MSG = Message.error("Invalid Move");
    private final Message SUBMIT_MSG = Message.error("Submit your turn first!");
    private final Message FORCE_MSG = Message.error("You must make a capture!");
    /**
     * The {@link Logger} that tracks {@link PostValidateMoveRoute} objects.
     */
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());
    /**
     * The {@link GameLobby} that holds {@link com.webcheckers.model.Game} objects.
     */
    private final GameLobby gameLobby = WebServer.gameLobby;

    // Constructors

    /**
     * The constructor for a {@link PostValidateMoveRoute} object.
     */
    public PostValidateMoveRoute() {
        LOG.config("PostValidateMoveRoute is initialized.");
    }

    // Public Methods

    /**
     * Handles POST /validateMove depending on the status code of validateMove.
     * If the code indicates an error, does not set a staged {@link com.webcheckers.model.Move} and
     * returns a correct error message,
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
        LOG.finer("PostValidateMoveRoute is invoked.");
        String actionData = request.queryParams("actionData");
        String gameKey = request.queryParams("gameID");

        int status = TurnServices.validateTurn(
                gameLobby.getGame(gameKey),
                request.session().attribute("currentUser"),
                gson.fromJson(actionData, com.webcheckers.model.Move.class)
        );

        switch (status) {
            case 0:
                response.body(VALID_MSG.toString());
                return gson.toJson(VALID_MSG);
            case 1:
                response.body(DOUBLE_MSG.toString());
                return gson.toJson(DOUBLE_MSG);
            case 2:
                response.body(SUBMIT_MSG.toString());
                return gson.toJson(SUBMIT_MSG);
            case 4:
                response.body(FORCE_MSG.toString());
                return gson.toJson(FORCE_MSG);
            default:
                response.body(INVALID_MSG.toString());
                return gson.toJson(INVALID_MSG);

        }
    }
}
