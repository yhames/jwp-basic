package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.view.JspView;
import next.view.ModelAndView;

public class HomeController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        ModelAndView mv = new ModelAndView();
        mv.addModel("questions", questionDao.findAll());
        mv.setView(new JspView("home.jsp"));
        return mv;
    }
}
