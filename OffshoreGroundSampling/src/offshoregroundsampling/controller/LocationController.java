package offshoregroundsampling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import offshoregroundsampling.services.SampleService;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    @Autowired
    private SampleService sampleService;

    @GetMapping
    public List<String> getAllLocations() {
        return sampleService.getAllLocations();
    }
}
