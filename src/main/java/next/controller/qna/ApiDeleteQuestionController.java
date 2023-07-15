package next.controller.qna;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.model.Result;
import next.model.User;
import next.service.QnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ApiDeleteQuestionController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(ApiDeleteQuestionController.class);

    private final QnaService questionService = QnaService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = jsonView();

        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLogined(session)) {
            return mv.addObject("result", Result.fail("로그인 필요"));
        }

        try {
            long questionId = Long.parseLong(request.getParameter("questionId"));
            User user = UserSessionUtils.getUserFromSession(session);
            questionService.deleteQuestion(questionId, user);
            mv.addObject("result", Result.ok());
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            mv.addObject("result", Result.fail(e.getMessage()));
        }
        return mv;
    }
}
