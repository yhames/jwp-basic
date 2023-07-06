package next.controller.qna;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQuestionController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(CreateQuestionController.class);

    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        questionDao.insert(new Question(
                req.getParameter("writer"),
                req.getParameter("title"),
                req.getParameter("contents")
        ));
        return "redirect:/";
    }
}
