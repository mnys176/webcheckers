package com.webcheckers.ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostBackupMoveRouteTest {
    private TemplateEngine templateEngine;
    private Response response;
    private Request request;
    private Session session;
    private PostBackupMoveRoute CuT;


    @BeforeEach
    public void stage() {
        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);
        CuT = new PostBackupMoveRoute();

    }

//    @Test
//    public void handleTest() {
//        TemplateEngineTest tester = new TemplateEngineTest();
//        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
//        CuT.handle(request, response);
//    }
}