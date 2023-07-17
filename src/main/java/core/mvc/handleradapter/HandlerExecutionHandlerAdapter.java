package core.mvc.handleradapter;

import core.mvc.ModelAndView;
import core.nmvc.HandlerExecution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter{
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return ((HandlerExecution) handler).handle(request, response);
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecutionHandlerAdapter;
    }
}
