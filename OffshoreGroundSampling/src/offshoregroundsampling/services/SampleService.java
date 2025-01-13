package offshoregroundsampling.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import offshoregroundsampling.model.Sample;

public class SampleService {

	// This method would interact with the backend to fetch the sample data
	public List<Sample> getSamplesFromBackend() {

		List<Sample> samples = new ArrayList<>();
		// Example of adding a sample manually
		samples.add(new Sample("S001", "Location1", new Date(), 20.5, 35.0, 150.0));
		samples.add(new Sample("S002", "Location2", new Date(), 19.5, 30.0, 140.0));
		samples.add(new Sample("S003", "Location3", new Date(), 20.5, 35.0, 150.0));
		samples.add(new Sample("S004", "Location4", new Date(), 19.5, 30.0, 140.0));
		samples.add(new Sample("S005", "Location5", new Date(), 20.5, 35.0, 150.0));
		samples.add(new Sample("S006", "Location6", new Date(), 19.5, 30.0, 140.0));

		/*
		 * RestTemplate restTemplate = new RestTemplate();
		 * 
		 * // Example: Fetching all samples ResponseEntity<List<Sample>> response =
		 * restTemplate.exchange( "http://your-backend-url/api/samples", HttpMethod.GET,
		 * null, new ParameterizedTypeReference<List<Sample>>() {});
		 * 
		 * List<Sample> samples = response.getBody();
		 */

		return samples;
	}
	
	// This method would interact with the backend to save the sample data
	public List<Sample> save(List<Sample> samples) {
			return samples;
	}

}
