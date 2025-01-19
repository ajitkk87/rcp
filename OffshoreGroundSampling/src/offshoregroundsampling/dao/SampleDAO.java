package offshoregroundsampling.dao;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Creatable;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import offshoregroundsampling.config.JpaUtil;
import offshoregroundsampling.model.Sample;

@Creatable
@Singleton
public class SampleDAO {

	public void createSample(Sample sample) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(sample); // Persist the entity
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
	}

	public Sample findSampleById(String sampleId) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		Sample sample = null;
		try {
			sample = em.find(Sample.class, sampleId); // Find entity by primary key
		} finally {
			em.close();
		}
		return sample;
	}

	public List<Sample> getAllSamples() {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Create a JPQL query to retrieve all records of the Sample entity
            TypedQuery<Sample> query = em.createQuery("SELECT s FROM Sample s", Sample.class);
            return query.getResultList();
        } finally {
            em.close();
        }
	}

	public void updateSample(Sample sample) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.merge(sample);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}

	public void deleteSample(Sample sample) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        // Replace with the actual id 
	        Sample sampleToRemove = em.find(Sample.class, sample.getSampleId()); 
	        if (sampleToRemove != null) { // Remove the entity 
	        	em.remove(sampleToRemove);
	        }	        
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}

	public void deleteAllSamples() {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaDelete<Sample> delete = cb.createCriteriaDelete(Sample.class);
            delete.from(Sample.class);
            // No conditions, delete all
            em.createQuery(delete).executeUpdate();

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
	}

}
