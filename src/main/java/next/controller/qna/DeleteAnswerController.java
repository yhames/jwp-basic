package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class DeleteAnswerController implements Controller {

    private final AnswerDao answerDao = new AnswerDao();

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        answerDao.delete(Long.parseLong(req.getParameter("answerId")));

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(Result.ok()));

        return null;
    }
}
