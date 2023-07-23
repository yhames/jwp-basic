package core.web.mvc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.mock.web.MockServletConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class AnnotationHandlerMappingTest {

    private AnnotationHandlerMapping ahm;

    @Before
    public void setup() {
        ahm = new AnnotationHandlerMapping("next", "core");
        ahm.initialize();
    }

    @Test
    public void getHandler() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/findUserId");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = ahm.getHandler(request);
        execution.handle(request, response);
    }
}
