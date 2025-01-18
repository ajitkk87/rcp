package offshoregroundsampling.controller;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.springframework.web.bind.annotation.*;

import jakarta.inject.Inject;
import offshoregroundsampling.model.Sample;
import offshoregroundsampling.services.SampleService;

import java.util.List;

/**
 * This controller is responsible for providing sample creation, modification,
 * deletion and retrieval of samples.
 */
@RestController
@RequestMapping("/api/samples")
@Creatable
public class SampleController {
	
	@Inject
	private SampleService sampleService;

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
