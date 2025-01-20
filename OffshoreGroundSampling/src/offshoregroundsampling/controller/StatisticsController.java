package offshoregroundsampling.controller;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.springframework.web.bind.annotation.*;

import jakarta.inject.Inject;
import offshoregroundsampling.services.SampleService;

/**
 * This controller is responsible for providing statistics about data such as
 * average water content.
 */
@Creatable
@RestController
@RequestMapping("/offshoregroundsampling/statistics")
public class StatisticsController {
	
	@Inject
	private SampleService sampleService;

	@GetMapping("/average-water-content")
	public double getAverageWaterContent() {
		return sampleService.calculateAverageWaterContent();
	}
}
