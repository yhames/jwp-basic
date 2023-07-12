package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;
import next.view.JspView;
import next.view.ModelAndView;
import next.view.View;

public class LoginController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String viewName = "/user/login.jsp";
        ModelAndView mv = new ModelAndView();

        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (user == null) {
            mv.addModel("loginFailed", true);
        }

        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            viewName = "redirect:/";
        } else {
            mv.addModel("loginFailed", true);
        }

        mv.setView(new JspView(viewName));

        return mv;
    }
}
