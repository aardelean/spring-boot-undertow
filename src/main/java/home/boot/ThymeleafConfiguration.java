package home.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * Created by alex on 9/19/2015.
 */

/**
 * No Need for this as it does exactly what spring boot auto config does. This served just an example.
 */
//@Configuration
public class ThymeleafConfiguration {

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(SpringTemplateEngine templateEngine){
        ThymeleafViewResolver result = new ThymeleafViewResolver();
        result.setOrder(1);
        result.setTemplateEngine(templateEngine);
        return result;
    }

    @Bean
    public SpringTemplateEngine templateEngine(TemplateResolver templateResolver){
        SpringTemplateEngine result = new SpringTemplateEngine();
        result.setTemplateResolver(templateResolver);
        return result;
    }

    @Bean
    public TemplateResolver templateResolver(){
        TemplateResolver resolver = new TemplateResolver();
        resolver.setResourceResolver(thymeleafResourceResolver());
        resolver.setSuffix(".html");
        resolver.setPrefix("/templates/");
        resolver.setTemplateMode("HTML5");
        return resolver;
    }
    @Bean
    public SpringResourceResourceResolver thymeleafResourceResolver() {
        return new SpringResourceResourceResolver();
    }
}
