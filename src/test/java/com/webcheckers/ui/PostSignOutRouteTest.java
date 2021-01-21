package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.Iterator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostSignOutRouteTest {
    private TemplateEngine templateEngine;
    private Response response;
    private Request request;
    private Session session;
    private PostSignOutRoute CuT;
    private Player currentUser;
    private Player testUser = new Player("test");
    /**
     * stages all need mocked objects
     */
    @BeforeEach
    public void stage() {
        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        currentUser = mock(Player.class);
        when(request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);
        CuT = new PostSignOutRoute(templateEngine);
    }
    /**
     * The test for the handle method in {@link PostSignOutRoute}.
     *
     * tests general building of the route. Reroutes to home.
     */
//    @Test
//    public void handleTest() {
//        TemplateEngineTest tester = new TemplateEngineTest();
//        session.attribute("currentUser", testUser);
//        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
//        when(session.attribute("currentUser")).thenReturn(testUser);
//        //CuT.handle(request, response);
//        //Test ViewModel and its attributes
//        tester.assertViewModelExists();
//        tester.assertViewModelIsaMap();
//        tester.assertViewModelAttribute("currentUser", null);
//    }
}