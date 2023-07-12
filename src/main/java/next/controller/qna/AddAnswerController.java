package next.controller.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.view.JsonView;
import next.view.ModelAndView;
import next.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;

public class AddAnswerController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("answer : {}", answer);

        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.insert(answer);

        ModelAndView mv = new ModelAndView();
        mv.addModel("answerId", savedAnswer.getAnswerId());
        mv.addModel("writer", savedAnswer.getWriter());
        mv.addModel("contents", savedAnswer.getContents());
        mv.addModel("createdDate", savedAnswer.getCreatedDate());
        mv.addModel("questionId", savedAnswer.getQuestionId());
        mv.setView(new JsonView());
        return mv;
    }
}
