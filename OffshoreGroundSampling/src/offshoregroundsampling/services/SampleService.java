package offshoregroundsampling.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import offshoregroundsampling.model.Sample;

/**
 * This class is service layer for samples. 
 */
public class SampleService {

	// Simulating an API call to fetch sample data
	List<Sample> samples = new ArrayList<>();

	public SampleService() {
		samples.add(new Sample("S001", "Location1", new Date(), 20.5, 35.0, 150.0));
		samples.add(new Sample("S002", "Location2", new Date(), 19.5, 30.0, 140.0));
		samples.add(new Sample("S003", "Location3", new Date(), 20.5, 35.0, 150.0));
		samples.add(new Sample("S004", "Location4", new Date(), 19.5, 30.0, 140.0));
		samples.add(new Sample("S005", "Location5", new Date(), 20.5, 35.0, 150.0));
		samples.add(new Sample("S006", "Location6", new Date(), 19.5, 30.0, 140.0));
	}

	// This method would interact with the backend to save the sample data
	public List<Sample> save(List<Sample> samples) {
		return samples;
	}

	public List<Sample> getAllSamples() {
		return samples;
	}

	public Sample createSample(Sample sample) {
		samples.add(sample);
		return sample;
	}

	public Sample updateSample(Sample sample) {
		return sample;
	}

	public void deleteSample(Sample sample) {
		samples.remove(sample);
	}

	public Sample findSample(String id) {
		Optional<Sample> result = samples.stream().filter(s -> s.getSampleId().equals(id)).findFirst();
		return result.get();
	}

	public double calculateAverageWaterContent() {
		return samples.stream().mapToDouble(Sample::getWaterContent).average().getAsDouble();
	}

	public List<String> getAllLocations() {
		return samples.stream().map(Sample::getLocation).collect(Collectors.toList());
	}

}
