package home.boot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class PersistenceJPAConfig {

	@Value("${spring.datasource.driver}")
	private String driverClass;

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan("home.boot.entities");
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter.
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean
	@Qualifier("dataSource")
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
		datasource.setDriverClassName(driverClass);
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setMaxActive(256);//important
		datasource.setMinIdle(10);
		datasource.setMaxIdle(3000);//release time, important
		datasource.setInitialSize(50);//skip the warm up, we have ram
		return datasource;
	}

	@Bean
	@Qualifier("transactionManager")
	public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf.getObject());
		return transactionManager;
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.jdbc.batch_size", "20");
		properties.setProperty("hibernate.id.new_generator_mappings", "false");
//		properties.setProperty("hibernate.connection.release_mode","after_statement");

		// second level cache configurations
//		properties.setProperty("hibernate.use_second_level_cache", "true");
//		properties.setProperty("hibernate.cache.use_query_cache", "true");
//		properties.setProperty("hibernate.cache.region.factory_class",
//				"org.hibernate.cache.infinispan.InfinispanRegionFactory");
//		properties.setProperty("hibernate.cache.infinispan.cachemanager",
//				"java:CacheManager/Employee");
//		properties.setProperty("hibernate.naming-strategy", "org.springframework.boot.orm.jpa.SpringNamingStrategy");
//		properties.setProperty("hibernate.generate_statistics", "true");
//		properties.setProperty("hibernate.transaction.manager_lookup_class",
//				"org.infinispan.transaction.lookup.GenericTransactionManagerLookup");

		return properties;
	}
}
