package home.boot.blocking;

import home.boot.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alex on 9/14/2015.
 */
@RestController
@RequestMapping("/blocking/employee")
public class EmployeeFacadeController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CacheManager cacheManager;

    /**
     * useless, as the one down does the same, somehow faster and more elegant and less code.
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEmployee(@PathVariable("id") Long id){
        Cache cache = cacheManager.getCache("employee");
        Cache.ValueWrapper  wrapper = cache.get(id);
        Employee result = null;
        if(wrapper==null){
            result = employeeDao.findOne(id);
            cache.put(id, result);
        }else{
            result = (Employee)wrapper.get();
        }
        return result.toString();
    }

    @RequestMapping(value = "/cached/{id}", method = RequestMethod.GET)
    @Cacheable(value = "employee", key = "#id")
    public Employee getEmployeeCached(@PathVariable("id") Long id){
        System.out.println("hit database");
        Employee result = employeeDao.findOne(id);
        return result;
    }
}
