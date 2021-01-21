package com.webcheckers.ui;
import com.webcheckers.application.GameLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostResignGameRouteTest {
    private TemplateEngine templateEngine;
    private Response response;
    private Request request;
    private Session session;
    private PostResignGameRoute CuT;
    private GameLobby gameLobby;
    private Player player;


    @BeforeEach
    public void stage() {
        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        gameLobby = mock(GameLobby.class);
        player = mock(Player.class);
        when(request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);
        CuT = new PostResignGameRoute();

    }

//    @Test
//    public void handleTest() {
//        when(player.getGameLobby()).thenReturn(gameLobby);
//        TemplateEngineTest tester = new TemplateEngineTest();
//        when(request.session().attribute("currentUser")).thenReturn(player);
//        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
//        CuT.handle(request, response);
//    }
}