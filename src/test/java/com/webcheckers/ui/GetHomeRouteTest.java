package com.webcheckers.ui;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tester for {@link GetHomeRoute} class.
 *
 * @author Caleb Eldridge
 */
@Tag("UI-tier")
public class GetHomeRouteTest {
    // Private Fields
    private static final String TITLE_ATTR = "title";
    private static final String CURRENT_USER_ATTR = "currentUser";
    private TemplateEngine templateEngine;
    private Response response;
    private Request request;
    private Session session;
    private GetHomeRoute CuT;

    // Public Methods
    /**
     * The test for handle in {@link GetHomeRoute}.
     *
     * Ensures that the webserver handles this transaction
     * correctly.
     */
    @Test
    public void handleTest() {
        // Create mock session, response, request, and templateEngine
        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        templateEngine = mock(TemplateEngine.class);

        //Stage mock session
        when(request.session()).thenReturn(session);
        CuT = new GetHomeRoute(templateEngine);
        TemplateEngineTest tester = new TemplateEngineTest();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        CuT.handle(request, response);

        //Test ViewModel and its attributes
        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewModelAttribute(TITLE_ATTR, "Welcome!");
        tester.assertViewModelAttribute(CURRENT_USER_ATTR, null);
    }
}
