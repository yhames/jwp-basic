package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AddAnswerController implements Controller {

    private final AnswerDao answerDao = new AnswerDao();

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Answer answer = answerDao.insert(new Answer(req.getParameter("writer"),
                req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId"))));

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(answer));

        return null;
    }
}
