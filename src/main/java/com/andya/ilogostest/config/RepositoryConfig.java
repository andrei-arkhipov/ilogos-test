package com.andya.ilogostest.config;

import java.util.Properties;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Created by AndyA on 15.12.2016.
 */
@Configuration
public class RepositoryConfig {
    private static final Logger logger = Logger.getLogger(RepositoryConfig.class);

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        Properties dsProps = new Properties();
        dsProps.put("url", environment.getRequiredProperty("spring.datasource.url"));
        dsProps.put("user", environment.getRequiredProperty("spring.datasource.username"));
        dsProps.put("password", environment.getRequiredProperty("spring.datasource.password"));

        Properties configProps = new Properties();
        configProps.put("dataSourceClassName", environment.getRequiredProperty("spring.jpa.databasePlatform"));
        configProps.put("poolName", environment.getRequiredProperty("spring.datasource.poolName"));
        configProps.put("maximumPoolSize", environment.getRequiredProperty("spring.datasource.maximumPoolSize"));
        configProps.put("minimumIdle", environment.getRequiredProperty("spring.datasource.minimumIdle"));
        configProps.put("maxLifetime", environment.getRequiredProperty("spring.datasource.maxLifetime"));
        configProps.put("connectionTimeout", environment.getRequiredProperty("spring.datasource.connectionTimeout"));
        configProps.put("idleTimeout", environment.getRequiredProperty("spring.datasource.idleTimeout"));
        configProps.put("dataSourceProperties", dsProps);

        HikariConfig hc = new HikariConfig(configProps);
        HikariDataSource ds = new HikariDataSource(hc);

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.andya.ilogostest.model");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    /*
     * Provider specific adapter.
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

        return hibernateJpaVendorAdapter;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
