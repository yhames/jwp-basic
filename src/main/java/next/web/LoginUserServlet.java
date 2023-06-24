package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/user/login")
public class LoginUserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginUserServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User findUser = DataBase.findUserById(req.getParameter("userId"));
        if (findUser != null
                && findUser.getPassword().equals(req.getParameter("password"))) {
            HttpSession session = req.getSession();
            session.setAttribute("user", findUser);
            log.debug("user : {}", findUser);
            resp.sendRedirect("/index.jsp");
            return;
        }
        resp.sendRedirect("/user/login_failed.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/user/login.jsp");
        rd.forward(req, resp);
    }
}
