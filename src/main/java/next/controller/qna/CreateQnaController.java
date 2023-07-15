package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQnaController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(CreateQnaController.class);

    private final QuestionDao questionDao = QuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.debug("==== AddQuestionController ====");
        questionDao.insert(new Question(
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents")
        ));
        return jspView("redirect:/");
    }
}
