package offshoregroundsampling.config;

import org.hibernate.jpa.HibernatePersistenceProvider;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;

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
}
