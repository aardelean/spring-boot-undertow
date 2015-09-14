package home.boot.nio;

import home.boot.HandlerPath;
import home.boot.blocking.EmployeeDao;
import home.boot.entities.Employee;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 9/14/2015.
 */
@Component
@HandlerPath(path="/nio/caching/employee")
public class EmployeeCacheHandler implements HttpHandler {


    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Cache cache = cacheManager.getCache("employee");
        Cache.ValueWrapper wrapper = cache.get(1l);
        Employee result = null;
        if(wrapper==null){
            result = employeeDao.findOne(1l);
            cache.put(1l, result);
        }else{
            result = (Employee)wrapper.get();
        }
        exchange.getResponseSender().send(result.toString());
    }
}
