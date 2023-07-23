package core.web.config;

import core.web.mvc.AnnotationHandlerMapping;
import core.web.mvc.DispatcherServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MyWebApplicationInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(MyWebApplicationInitializer.class);

    @Override
    public void onStartUp(ServletContext servletContext) throws ServletException {
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("next", "core");
        ahm.initialize();
        ServletRegistration.Dynamic dispatcher
                = servletContext.addServlet("dispatcher", new DispatcherServlet(ahm));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        logger.info("Start MyWebApplication Initializer");
    }
}
