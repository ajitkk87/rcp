package offshoregroundsampling.controller;

import org.springframework.web.bind.annotation.*;

import offshoregroundsampling.model.Sample;
import offshoregroundsampling.services.SampleService;

import java.util.List;

/**
 * This controller is responsible for providing sample creation, modification,
 * deletion and retrieval of samples.
 */
@RestController
@RequestMapping("/api/samples")
public class SampleController {
	
	private SampleService sampleService = new SampleService();

	@GetMapping
	public List<Sample> getAllSamples() {
		return sampleService.getAllSamples();
	}

	@PostMapping
	public Sample createSample(@RequestBody Sample sample) {
		return sampleService.createSample(sample);
	}

	@PutMapping
	public Sample updateSample(@RequestBody Sample sample) {
		return sampleService.updateSample(sample);
	}

	@DeleteMapping("/{id}")
	public void deleteSample(@PathVariable String id) {
		sampleService.deleteSample(sampleService.findSample(id));
	}
}
