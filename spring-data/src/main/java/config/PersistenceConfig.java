package config;

import datasource.CustomRoutingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author Kamill Sokol
 */
@Configuration
@EnableJpaRepositories(basePackages = "repositories")
@EnableTransactionManagement
public class PersistenceConfig {

    private final String driverClassName = "org.h2.Driver";
    private final String jdbcUrlTemplate = "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1";
    private final boolean jpaGenerateDdl = true;
    private final String hibernateDialect = "org.hibernate.dialect.H2Dialect";
    private final String username = "sa";
    private final String password = "";

    @Bean
    public EntityManagerFactory entityManagerFactory() throws SQLException {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(new String[]{"entities"});
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        String tenant1 = "tenant1";
        String tenant2 = "tenant2";

        CustomRoutingDataSource customRoutingDataSource = new CustomRoutingDataSource();

        final DataSource tenant1DataSource = createDataSourceForTenant(tenant1);
        final DataSource tenant2DataSource = createDataSourceForTenant(tenant2);

        HashMap<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(tenant1.toUpperCase(), tenant1DataSource);
        targetDataSources.put(tenant2.toUpperCase(), tenant2DataSource);

        customRoutingDataSource.setTargetDataSources(targetDataSources);
        return customRoutingDataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    private DataSource createDataSourceForTenant(String tenant) throws SQLException {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(String.format(jdbcUrlTemplate, tenant));
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan(new String[]{"entities"});
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.afterPropertiesSet();

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource(tenant+".sql"));
        populator.populate(dataSource.getConnection());

        factoryBean.destroy();
        return dataSource;
    }

    final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() {
        {
            setDatabasePlatform(hibernateDialect);
            setGenerateDdl(jpaGenerateDdl);
        }
    };
}
