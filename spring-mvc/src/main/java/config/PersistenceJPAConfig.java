package config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@PropertySource("classpath:config.properties")
@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {

    @Value("${driverClass}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    private boolean jpaGenerateDdl = true;

    @Value("${dialect}")
    private String hibernateDialect;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${hibernateHbm2ddlAuto:false}")
    private String hibernateHbm2ddlAuto;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(new String[] { "entities" });

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() {{
                setDatabasePlatform(hibernateDialect);
                setGenerateDdl(jpaGenerateDdl);
                setShowSql(true);
            }
        };

        factoryBean.setJpaVendorAdapter(vendorAdapter);
        //factoryBean.setJpaProperties(additionlProperties());

        return factoryBean;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

        return transactionManager;
    }

    // @Bean
    // public PersistenceExceptionTranslationPostProcessor
    // persistenceExceptionTranslationPostProcessor() {
    // return new PersistenceExceptionTranslationPostProcessor();
    // }

    final Properties additionlProperties() {
        return new Properties() {{
                setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
            } };
    }

}