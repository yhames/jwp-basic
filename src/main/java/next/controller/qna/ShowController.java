package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.view.JspView;
import next.view.ModelAndView;
import next.view.View;

public class ShowController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        ModelAndView mv = new ModelAndView();
        mv.addModel("question", questionDao.findById(questionId));
        mv.addModel("answers", answerDao.findAllByQuestionId(questionId));
        mv.setView(new JspView("/qna/show.jsp"));
        return mv;
    }
}
