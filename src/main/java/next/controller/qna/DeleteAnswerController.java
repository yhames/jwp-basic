package next.controller.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Result;
import next.view.JsonView;
import next.view.ModelAndView;
import next.view.View;

public class DeleteAnswerController implements Controller {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.parseLong(req.getParameter("answerId"));
        AnswerDao answerDao = new AnswerDao();
        answerDao.delete(answerId);

        Result ok = Result.ok();

        ModelAndView mv = new ModelAndView();
        mv.addModel("status", ok.isStatus());
        mv.addModel("message", ok.getMessage());
        mv.setView(new JsonView());
        return mv;
    }
}
