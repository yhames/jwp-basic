package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractController implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        return req.getMethod().equals("POST") ? doPost(req, res) : doGet(req, res);
    }

    protected String doPost(HttpServletRequest req, HttpServletResponse res) {
        return null;
    }

    protected String doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        return null;
    }

}
