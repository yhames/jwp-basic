package next.controller.qna;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public class CreateQnaController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Question question = new Question(
                null,
                req.getParameter("writer"),
                req.getParameter("title"),
                req.getParameter("contents"),
                LocalDateTime.parse(req.getParameter("createdDate")),
                0);
        QuestionDao qnaDao = new QuestionDao();
        qnaDao.insert(question);
        return "redirect:/";
    }
}
