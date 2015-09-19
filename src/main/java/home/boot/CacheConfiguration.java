package home.boot;

import home.boot.entities.Employee;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.persistence.jpa.configuration.JpaStoreConfigurationBuilder;
import org.infinispan.spring.provider.SpringEmbeddedCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alex on 9/12/2015.
 */
@Configuration
@EnableCaching
public class CacheConfiguration {



    @Bean
    public CacheManager infinispanCacheManager() {
        org.infinispan.configuration.cache.Configuration cacheConfig = new ConfigurationBuilder().persistence()
                .addStore(JpaStoreConfigurationBuilder.class)
                .persistenceUnitName("persistence")
                .entityClass(Employee.class)
                .build();
        EmbeddedCacheManager cacheManager = new DefaultCacheManager();
        cacheManager.defineConfiguration("Employee", cacheConfig);
        return new SpringEmbeddedCacheManager(cacheManager);
    }
}
