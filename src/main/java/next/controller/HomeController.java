package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;

import java.util.List;

public class HomeController implements Controller {

    private final UserDao userDao = new UserDao();
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDao userDao = new UserDao();
        List<Question> questions = questionDao.findAll();

        req.setAttribute("users", userDao.findAll());
        req.setAttribute("questions", questions);

        return "home.jsp";
    }
}
