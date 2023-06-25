package next.servlet;

import next.controller.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)  // loadOnStartup : 컨테이너 실행 시 서블릿이 로드되는 순서 지정
public class DispatcherServlet extends HttpServlet {

    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private RequestMapping requestMapping;

    @Override
    public void init() throws ServletException {
        requestMapping = new RequestMapping();
    }

    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Controller controller = requestMapping.findController(req.getRequestURI());
        try {
            String url = controller.execute(req, res);
            forward(req, res, url);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void forward(HttpServletRequest req, HttpServletResponse res, String viewName) throws IOException, ServletException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            res.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }
        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, res);
    }
}
