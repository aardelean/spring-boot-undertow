package home.spring.react;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerException;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;

/**
 * Created by alex on 9/6/2015.
 */
public class SpringWebInitializer  extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringWebInitializer.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringWebInitializer.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory(){
        UndertowEmbeddedServletContainerFactory undertowEmbeddedServletContainerFactory = new UndertowEmbeddedServletContainerFactory();
        undertowEmbeddedServletContainerFactory.addBuilderCustomizers(new UndertowBuilderCustomizer() {

            @Override
            public void customize(Undertow.Builder builder) {
                builder.setHandler(new HttpHandler() {
                    @Override
                    public void handleRequest(HttpServerExchange exchange) throws Exception {
                        exchange.getResponseSender().send("test");
                    }
                });
                builder.addHttpListener(6020, "localhost");
            }
        });
        return undertowEmbeddedServletContainerFactory;
    }

    @Bean
    public HelloHandler helloNio(){
        return new HelloHandler();
    }

    @Bean
    public EmployeeController ioController(){
        return new EmployeeController();
    }
}
