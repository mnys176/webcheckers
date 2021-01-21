package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostSignInRouteTest {
    private TemplateEngine templateEngine;
    private Response response;
    private Request request;
    private Session session;
    private PostSignInRoute CuT;
    private String username= "test";
    private String usernameInvalid= "!test";
    private PlayerLobby playerLobby;

    @BeforeEach
    public void stage() {
        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        playerLobby = mock(PlayerLobby.class);
        when(request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);
        CuT = new PostSignInRoute(templateEngine);

    }

    @Test
    public void handleValidName() {
        when(request.queryParams("username")).thenReturn(username);
        when(session.attribute("currentUser")).thenReturn(username);
        TemplateEngineTest tester = new TemplateEngineTest();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        CuT.handle(request, response);
        verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    public void handleInValidName() {
        when(request.queryParams("username")).thenReturn(usernameInvalid);
        when(session.attribute("currentUser")).thenReturn(usernameInvalid);
        TemplateEngineTest tester = new TemplateEngineTest();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        CuT.handle(request, response);
        tester.assertViewModelAttribute("currentUser", null);
        tester.assertViewModelIsaMap();
        tester.assertViewName("signin.ftl");
    }

    @Test
    public void handleDupeName() {
        playerLobby.userSignIn(username);
        when(request.queryParams("username")).thenReturn(usernameInvalid);
        when(session.attribute("currentUser")).thenReturn(usernameInvalid);
        TemplateEngineTest tester = new TemplateEngineTest();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        CuT.handle(request, response);
        tester.assertViewModelAttribute("currentUser", null);
        tester.assertViewModelIsaMap();
        tester.assertViewName("signin.ftl");
    }
}
