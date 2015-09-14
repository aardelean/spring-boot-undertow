package home.boot.nio;

import home.boot.HandlerPath;
import home.boot.blocking.EmployeeDao;
import home.boot.entities.Employee;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 9/14/2015.
 */
@Component
@HandlerPath(path="/nio/employee")
public class EmployeeFacadeHandler implements HttpHandler {


    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Employee result = null;
//        System.out.print(" hit database");
        result = employeeDao.findOne(1l);
        exchange.getResponseSender().send(result.toString());
    }
}
