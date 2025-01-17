package offshoregroundsampling.parts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JPanel;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
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

/**
 * This part contains logic for graph creation for unit weight and water content.
 */
public class SamplePartChart {
	
	private SampleService sampleService = new SampleService();
		
	@Inject
	private MPart part;
	
    @Inject
    public SamplePartChart() {}

    @PostConstruct
    public void createControls(Composite parent) {
        
    	AtomicInteger windowCorrected = new AtomicInteger(0);
    	// Set SWT layout
        parent.setLayout(new FillLayout());
 
        // Create the AWT Frame for embedding JFreeChart
        Composite awtFrame = new Composite(parent, SWT.EMBEDDED);
        java.awt.Frame frame = SWT_AWT.new_Frame(awtFrame);
        // Create the chart and embed it in the AWT frame
        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        
        // Set BorderLayout for the container
        JPanel container = new JPanel(new BorderLayout());
        
        // Add the ChartPanel to the CENTER of the container
        container.add(chartPanel, BorderLayout.CENTER);
        frame.add(container);
   
        MElementContainer<MUIElement> partStack = part.getParent().getParent();
     
        //Refresh the graph for new data
        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            loadAndRefreshDataset(); // Update dataset
            chartPanel.revalidate();
            chartPanel.repaint();
            
            //Logic to fit chart in map
            if(windowCorrected.incrementAndGet() < 2) {
             //Set the "Maximized" tag on the part stack
              partStack.getChildren().get(1).getTags().add("Maximized");
              partStack.getChildren().get(1).getTags().remove("Maximized");
            }
        });
        timer.start();

    }
   

 // Create the chart
    private JFreeChart createChart() {
        
        return ChartFactory.createXYLineChart(
            "Dependency between Unit Weight and Water Content", // Title
            "Water Content (%)",                               // X-Axis Label
            "Unit Weight (kN/m³)",                             // Y-Axis Label
            loadAndRefreshDataset(),                           // Dataset
            PlotOrientation.VERTICAL,                         // Orientation
            true,                                              // Show legend
            true,                                              // Use tooltips
            false                                              // Generate URLs
        );
    }

    // Create the data series
	private XYSeriesCollection loadAndRefreshDataset() {
		XYSeries series = new XYSeries("Unit Weight vs Water Content");
        
		List<Sample> samples = sampleService.getAllSamples(); 
		
        samples.forEach(sample -> {
        	series.add(sample.getWaterContent(), sample.getUnitWeight());
        });
       
        // Add the series to the dataset
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        
        return dataset;
	}
}
