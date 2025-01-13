package offshoregroundsampling.parts;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import offshoregroundsampling.model.Sample;
import offshoregroundsampling.services.SampleService;

public class SamplePartChart {
	
	private SampleService sampleService = new SampleService();
	
    @Inject
    public SamplePartChart() {}

    @PostConstruct
    public void createControls(Composite parent) {
        // Set SWT layout
        parent.setLayout(new FillLayout());
 
        // Create the AWT Frame for embedding JFreeChart
        Composite awtFrame = new Composite(parent, SWT.EMBEDDED);
        java.awt.Frame frame = SWT_AWT.new_Frame(awtFrame);

        // Create the chart and embed it in the AWT frame
        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel);
        
        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            loadAndRefreshDataset(); // Update dataset
            chartPanel.revalidate();
            chartPanel.repaint();
        });
        timer.start();
    }
   

 // Create the chart
    private JFreeChart createChart() {
        
        return ChartFactory.createXYLineChart(
            "Dependency between Unit Weight and Water Content", // Title
            "Water Content (%)",                               // X-Axis Label
            "Unit Weight (kN/m³)",                             // Y-Axis Label
            loadAndRefreshDataset(),                                           // Dataset
            PlotOrientation.VERTICAL,                         // Orientation
            true,                                              // Show legend
            true,                                              // Use tooltips
            false                                              // Generate URLs
        );
    }

    // Create the data series
	private XYSeriesCollection loadAndRefreshDataset() {
		XYSeries series = new XYSeries("Unit Weight vs Water Content");
        
		List<Sample> samples = sampleService.getSamplesFromBackend();; 
		
        samples.forEach(sample -> {
        	series.add(sample.getWaterContent(), sample.getUnitWeight());
        });
       
        // Add the series to the dataset
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        
        return dataset;
	}
}
