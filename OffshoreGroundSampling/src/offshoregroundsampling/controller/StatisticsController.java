package offshoregroundsampling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import offshoregroundsampling.services.SampleService;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private SampleService sampleService;

    @GetMapping
    public Map<String, Double> getStatistics() {
        return sampleService.calculateStatistics();
    }
}
