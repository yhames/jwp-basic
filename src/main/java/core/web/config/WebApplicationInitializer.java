package core.web.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public interface WebApplicationInitializer {
    void onStartUp(ServletContext servletContext) throws ServletException;
}
