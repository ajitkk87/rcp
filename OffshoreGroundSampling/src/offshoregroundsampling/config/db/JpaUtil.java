package offshoregroundsampling.config.db;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;

@Configuration
public class JpaUtil {

	private static final PersistenceUnitInfo persistenceUnitInfo = new MyPersistenceUnitInfo();
	private static final EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(persistenceUnitInfo, null);
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

	public static void closeEntityManagerFactory() {
		try {
			emf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("offshoregroundsampling");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        // Explicitly set the EntityManagerFactory interface to avoid conflict between
        // the EntityManagerFactory interfaces used by Spring and Hibernate.
        em.setEntityManagerFactoryInterface(EntityManagerFactory.class);

        return em;
    }

}
