package core.mvc.handleradapter;

import core.mvc.Controller;
import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerHandlerAdapter implements HandlerAdapter {

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return ((Controller) handler).execute(request, response);
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof ControllerHandlerAdapter;
    }
}
