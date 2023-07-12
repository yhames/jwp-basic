package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.view.JspView;
import next.view.ModelAndView;
import next.view.View;

public class ListUserController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String viewName = "redirect:/users/loginForm";
        ModelAndView mv = new ModelAndView();

        if (UserSessionUtils.isLogined(req.getSession())) {
            viewName = "/user/list.jsp";
            UserDao userDao = new UserDao();
            mv.addModel("users", userDao.findAll());
        }

        mv.setView(new JspView(viewName));

        return mv;
    }
}
