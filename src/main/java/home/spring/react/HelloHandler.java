package home.spring.react;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 9/6/2015.
 */
@Component
@HandlerPath(path = "/hello-nio")
public class HelloHandler implements HttpHandler {

//    @Autowired
//    EmployeeDao employeeDao;

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getResponseSender().send("haha, nio!");
    }
}
