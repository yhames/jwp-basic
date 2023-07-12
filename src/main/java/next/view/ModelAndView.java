package next.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private View view;

    private Map<String, Object> model = new HashMap<>();

    public void addModel(String name, Object attribute) {
        model.put(name, attribute);
    }

    public void setView(View view) {
        this.view = view;
    }

    public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        view.render(model, req, resp);
    }

}
