package offshoregroundsampling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import offshoregroundsampling.services.SampleService;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private SampleService sampleService;

    @GetMapping
    public double getStatistics() {
        return sampleService.calculateStatistics();
    }
}
