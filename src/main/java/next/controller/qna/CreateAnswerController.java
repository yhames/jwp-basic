package next.controller.qna;

import core.mvc.Controller;
import next.controller.user.UserSessionUtils;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateAnswerController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(CreateAnswerController.class);

    private final AnswerDao answerDao = new AnswerDao();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }

        HttpSession session = req.getSession();
        User user = UserSessionUtils.getUserFromSession(session);
        String questionId = req.getParameter("questionId");
        answerDao.insert(new Answer(
                user.getName(),
                req.getParameter("contents"),
                Long.parseLong(questionId)
        ));

        return "redirect:/qna/show?questionId=" + questionId;
    }
}
