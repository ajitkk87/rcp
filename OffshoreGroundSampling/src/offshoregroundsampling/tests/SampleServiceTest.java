package offshoregroundsampling.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import offshoregroundsampling.model.Sample;
import offshoregroundsampling.services.SampleService;

/**
 *  Junit Plugin Tests for service layer.
 */
public class SampleServiceTest {

    private SampleService sampleService;

    @Before
    public void setUp() {
       sampleService = new SampleService();
       sampleService.getAllSamples().clear();
    }

    @Test
    public void testCreateSample() {
        Sample sample = new Sample("S001", "Location1", new Date(), 20.5, 35.0, 150.0);
        Sample result = sampleService.createSample(sample);
        assertNotNull(result);
        assertEquals("S001", result.getSampleId());
    }

    @Test
    public void testFindSample() {
        Sample sample = new Sample("S001", "Location1", new Date(), 20.5, 35.0, 150.0);
        sampleService.createSample(sample);
        Sample result = sampleService.findSample("S001");
        assertNotNull(result);
        assertEquals("S001", result.getSampleId());
    }

    @Test
    public void testCalculateStatistics() {
        Sample sample1 = new Sample("S001", "Location1", new Date(), 20.5, 35.0, 150.0);
        Sample sample2 = new Sample("S002", "Location2", new Date(), 19.5, 30.0, 140.0);
        sampleService.createSample(sample1);
        sampleService.createSample(sample2);
        double result = sampleService.calculateAverageWaterContent();
        assertEquals(32.5, result, 0.1);
        
    }

    @Test
    public void testGetAllLocations() {
        Sample sample1 = new Sample("S001", "Location1", new Date(), 20.5, 35.0, 150.0);
        Sample sample2 = new Sample("S002", "Location2", new Date(), 19.5, 30.0, 140.0);
        sampleService.createSample(sample1);
        sampleService.createSample(sample2);
        List<String> result = sampleService.getAllLocations();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("Location1"));
        assertTrue(result.contains("Location2"));
    }
}
