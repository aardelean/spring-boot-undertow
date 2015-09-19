package home.boot.nio;

import home.boot.HandlerPath;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 9/7/2015.
 */
@Component
@HandlerPath(path="/json")
public class JsonHandler implements HttpHandler {
    private static final  String responseString = "{name: 'Max, lastname: 'Mustermann', occupation: 'developer'}";

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
       exchange.getResponseSender().send(responseString);
    }
}
