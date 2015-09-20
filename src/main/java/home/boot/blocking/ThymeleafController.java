package home.boot.blocking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by alex on 9/19/2015.
 */
@Controller
@RequestMapping("/template")
public class ThymeleafController {

    @RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
    public String getThymeleafTemplate(@PathVariable("file_name")String fileName, Model model){
        return fileName;
    }


}
