package offshoregroundsampling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import offshoregroundsampling.services.SampleService;

/**
 * This controller is responsible for providing statistics about data such as
 * average water content.
 */
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
	@Autowired
	private SampleService sampleService;

	@GetMapping
	public double getAverageWaterContent() {
		return sampleService.calculateAverageWaterContent();
	}
}
