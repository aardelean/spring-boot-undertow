package home.boot.blocking;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by alex on 9/19/2015.
 */
@Controller
@RequestMapping("/template")
public class ThymeleafController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getThymeleafTemplate(Model model){
        return "thymeleaf";
    }
}
