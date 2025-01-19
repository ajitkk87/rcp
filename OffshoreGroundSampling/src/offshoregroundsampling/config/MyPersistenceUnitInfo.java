package offshoregroundsampling.config;

import java.net.URL;
import java.util.List;
import java.util.Properties;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import offshoregroundsampling.constants.Constants;
import offshoregroundsampling.model.Sample;

import javax.sql.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;

public class MyPersistenceUnitInfo implements PersistenceUnitInfo {

    @Override
    public String getPersistenceUnitName() {
        return "OffShoreGroundSamplingJpaUnit";
    }
    
    @Override
    public List<String> getManagedClassNames() {
        return List.of(Sample.class.getCanonicalName());
    }

    @Override
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("jakarta.persistence.jdbc.url", Constants.H2_DB_JDBC_URL);
        properties.setProperty("jakarta.persistence.jdbc.user", Constants.H2_DB_USER);
        properties.setProperty("jakarta.persistence.jdbc.password", Constants.H2_DB_PASSWORD);
        properties.setProperty("jakarta.persistence.jdbc.driver", Constants.H2_DB_DRIVER);
        properties.setProperty("hibernate.dialect", Constants.H2_DB_HIBERNATE_DIALECT);
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        return properties;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return "3.0";
    }
    
    @Override
    public String getPersistenceProviderClassName() {
        return HibernatePersistenceProvider.class.getName();
    }

    @Override
    public DataSource getJtaDataSource() {
        return null;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return null;
    }

    @Override
    public List<String> getMappingFileNames() {
        return null;
    }

    @Override
    public List<URL> getJarFileUrls() {
        return null;
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.UNSPECIFIED;
    }

    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }


    @Override
    public ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {
    }

    @Override
    public ClassLoader getNewTempClassLoader() {
    	return getClass().getClassLoader();
    }

	@Override
	public List<String> getQualifierAnnotationNames() {
		return null;
	}

	@Override
	public String getScopeAnnotationName() {
		return null;
	}
	
	@Override
	@Deprecated
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }
}
