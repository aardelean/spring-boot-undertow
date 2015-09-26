package home.boot.fibers;

import co.paralleluniverse.fibers.SuspendExecution;
import home.boot.blocking.EmployeeDao;
import home.boot.entities.Employee;
import home.boot.services.ComplexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by alex on 9/25/2015.
 */
@RestController
@RequestMapping("/fibers")
public class ComplexController {
    private static final  String responseString = "{name: 'Max, lastname: 'Mustermann', occupation: 'developer'}";

    @Autowired
    private ComplexService service;

    @RequestMapping("/json")
     public String getSimpleJson(){
        return responseString;
    }

    @RequestMapping("/complex")
    public List<String> complexShit() throws SuspendExecution, ExecutionException, InterruptedException {
        return service.calculateSomething();
    }
}
