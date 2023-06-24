package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/user/login")
public class LoginUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User findUser = DataBase.findUserById(req.getParameter("userId"));
        if (findUser != null
                && findUser.getPassword().equals(req.getParameter("password"))) {
            resp.addCookie(new Cookie("logined", "true"));
            resp.sendRedirect("/");
            return;
        }
        resp.sendRedirect("/user/login_failed.html");
    }
}
