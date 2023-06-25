package next.servlet;

import next.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    private Map<String, Controller> map = new HashMap<>();

    {
        map.put("/", new HomeController());
        map.put("/users/form", new ForwardController("/user/form.jsp"));
        map.put("/users/create", new CreateUserController());
        map.put("/users", new ListUserController());
        map.put("/users/login", new LoginController());
        map.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        map.put("/users/logout", new LogoutController());
        map.put("/users/profile", new ProfileController());
        map.put("/users/update", new UpdateUserController());
        map.put("/users/updateForm", new UpdateFormUserController());

        log.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return map.get(url);
    }

    void put(String url, Controller controller) {
        map.put(url, controller);
    }
}
