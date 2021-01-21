package com.webcheckers.ui;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class GetSignInRouteTest {
    private static final String TITLE_ATTR = "title";
    private TemplateEngine templateEngine;
    private Response response;
    private Request request;
    private Session session;
    private GetSignInRoute CuT;


    @Test
    public void handleTest() {
        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        templateEngine = mock(TemplateEngine.class);
        when(request.session()).thenReturn(session);
        CuT = new GetSignInRoute(templateEngine);
        TemplateEngineTest tester = new TemplateEngineTest();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        CuT.handle(request, response);
        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewModelAttribute(TITLE_ATTR, "");
    }
}
