package offshoregroundsampling.controller;

import org.springframework.web.bind.annotation.*;

import jakarta.inject.Inject;
import offshoregroundsampling.services.SampleService;

import java.util.List;

/**
 * This controller is responsible for providing location specific details.
 */
@RestController
@RequestMapping("/offshoregroundsampling/locations")
public class LocationController {

    @Inject
	private SampleService sampleService;

	@GetMapping
	public List<String> getAllLocations() {
		return sampleService.getAllLocations();
	}
}
