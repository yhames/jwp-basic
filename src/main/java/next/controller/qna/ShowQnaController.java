package next.controller.qna;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowQnaController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(ShowQnaController.class);

    private final QuestionDao questionDao = new QuestionDao();
    private final AnswerDao answerDao = new AnswerDao();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String param = req.getParameter("questionId");
        if (param == null) return "redirect:/";
        Long questionId = Long.parseLong(param);

        Question question = questionDao.findById(questionId);
        req.setAttribute("question", question);

        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        req.setAttribute("answers", answers);

        return "/qna/show.jsp";
    }
}
