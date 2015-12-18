package com.donishchenko.contactmanager.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    /* application.properties */
    @Value("${dataSource.driverClassName}") private String driver;
    @Value("${dataSource.url}")             private String url;
    @Value("${dataSource.username}")        private String username;
    @Value("${dataSource.password}")        private String password;
    @Value("${hibernate.dialect}")          private String dialect;
    @Value("${hibernate.hbm2ddl.auto}")     private String hbm2ddlAuto;

    /* Hikari DataSource */
    @Bean
    public DataSource configureDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        return new HikariDataSource(config);
    }

    /* Hibernate SessionFactory */
    @Bean
    public SessionFactory getSessionFactory() {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(configureDataSource());
        /* Scan for Entities */
        sessionBuilder.scanPackages("com.donishchenko.contactmanager");

        /* Set properties */
        Properties props = new Properties();
        props.put(Environment.DIALECT, dialect);
        props.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        props.put(Environment.SHOW_SQL, true);

        sessionBuilder.addProperties(props);

        return sessionBuilder.buildSessionFactory();
    }

    /* HibernateTransactionManager */
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager() {
        return new HibernateTransactionManager(getSessionFactory());
    }

}
