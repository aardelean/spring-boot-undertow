package home.spring.react;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by alex on 9/6/2015.
 */
@Controller
public class EmployeeController {
    @RequestMapping("/test")
    public ModelAndView testController(){
        String message = "<br><div style='text-align:center;'>"
                + "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
        return new ModelAndView("welcome", "message", message);
    }
}
