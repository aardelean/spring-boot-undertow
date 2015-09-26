package home.boot;

//import co.paralleluniverse.springframework.boot.autoconfigure.web.FiberSpringBootApplication;
//import co.paralleluniverse.springframework.web.servlet.config.annotation.FiberWebMvcConfigurationSupport;
import co.paralleluniverse.springframework.boot.autoconfigure.web.FiberSpringBootApplication;
import co.paralleluniverse.springframework.web.servlet.config.annotation.FiberWebMvcConfigurationSupport;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
//import org.infinispan.persistence.jpa.configuration.JpaStoreConfigurationBuilder;
import org.infinispan.spring.provider.SpringEmbeddedCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.WebApplicationInitializer;

/**
 * Created by alex on 9/5/2015.
 */
@FiberSpringBootApplication
@ComponentScan("home.boot")
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, WebMvcAutoConfiguration.class})
@Import({MongoConfiguration.class, PersistenceJPAConfig.class, CacheConfiguration.class, RestConfiguration.class, FiberWebMvcConfigurationSupport.class/*ThymeleafConfiguration.class*/})
public class Starter extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Starter.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory(){
        UndertowEmbeddedServletContainerFactory result = new UndertowEmbeddedServletContainerFactory();
        return result;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
