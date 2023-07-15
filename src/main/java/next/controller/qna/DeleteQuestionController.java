package next.controller.qna;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.model.User;
import next.service.QnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteQuestionController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(DeleteQuestionController.class);

    private final QnaService questionService = QnaService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLogined(session)) {
            return jspView("redirect:/users/loginForm");
        }

        try {
            long questionId = Long.parseLong(request.getParameter("questionId"));
            User user = UserSessionUtils.getUserFromSession(session);
            questionService.deleteQuestion(questionId, user);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }

        return jspView("redirect:/");
    }
}
