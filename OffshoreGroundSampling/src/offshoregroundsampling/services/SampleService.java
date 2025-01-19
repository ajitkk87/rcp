package offshoregroundsampling.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.e4.core.di.annotations.Creatable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import offshoregroundsampling.dao.SampleDAO;
import offshoregroundsampling.model.Sample;

/**
 * This class is service layer for samples. 
 */
@Creatable
@Singleton
public class SampleService {

	SampleDAO sampleDAO;
	
	@Inject
	public SampleService(SampleDAO sampleDAO) {
		this.sampleDAO = sampleDAO;
		initialize();
	}
	
	public void initialize() {
		createSample(new Sample("S001", "Location1", new Date(), 20.5, 35.0, 150.0));
		createSample(new Sample("S002", "Location2", new Date(), 19.5, 30.0, 140.0));
		createSample(new Sample("S003", "Location3", new Date(), 20.5, 35.0, 150.0));
		createSample(new Sample("S004", "Location4", new Date(), 19.5, 30.0, 140.0));
		createSample(new Sample("S005", "Location5", new Date(), 20.5, 35.0, 150.0));
		createSample(new Sample("S006", "Location6", new Date(), 19.5, 30.0, 140.0));
	}

	// This method would interact with the backend to save the sample data
	public List<Sample> save(List<Sample> samples) {
		return samples;
	}

	public List<Sample> getAllSamples() {
		return sampleDAO.getAllSamples();
	}

	public Sample createSample(Sample sample) {
		sampleDAO.createSample(sample);
		return sample;
	}

	public Sample updateSample(Sample sample) {
		sampleDAO.updateSample(sample);
		return sample;
	}

	public void deleteSample(Sample sample) {
		sampleDAO.deleteSample(sample);
	}
	
	public void deleteAllSamples() {
		sampleDAO.deleteAllSamples();
	}

	public Sample findSample(String sampleId) {
		return sampleDAO.findSampleById(sampleId);
	}

	public double calculateAverageWaterContent() {
		return sampleDAO.getAllSamples().stream().mapToDouble(Sample::getWaterContent).average().getAsDouble();
	}

	public List<String> getAllLocations() {
		return sampleDAO.getAllSamples().stream().map(Sample::getLocation).collect(Collectors.toList());
	}

}
