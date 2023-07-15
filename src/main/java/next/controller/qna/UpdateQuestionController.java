package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQuestionController extends AbstractController {

    private final QuestionDao questionDao = QuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String questionId = request.getParameter("questionId");
        Question updateQuestion = new Question(
                Long.parseLong(questionId),
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents"), null, 0);
        questionDao.update(updateQuestion);

        return jspView("redirect:/qna/show?questionId=" + questionId);
    }
}
