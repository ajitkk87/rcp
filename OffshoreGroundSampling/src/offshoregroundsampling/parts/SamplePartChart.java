package offshoregroundsampling.parts;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
		Frame frame = SWT_AWT.new_Frame(awtFrame);
		createChartAndRefreshFrame(frame);
		//addRefreshButtonWithPanelAndChart(parent, frame); 
		// Set the "Maximized" tag on the part stack
		MElementContainer<MUIElement> partStack = part.getParent().getParent();
		//partStack.getChildren().get(1).getTags().add("Maximized");
		//partStack.getChildren().get(1).getTags().remove("Maximized");
		Timer timer = new Timer(3000, e -> {
			// Logic to fit chart in map
			createChartAndRefreshFrame(frame);
			if (windowCorrected.incrementAndGet() < 2) {
				System.out.println(partStack.getChildren().get(1));
				partStack.getChildren().get(1).getTags().add("Maximized");
				partStack.getChildren().get(1).getTags().remove("Maximized");
			}
		});
		timer.start();

	}

	private void createChartAndRefreshFrame(Frame frame) {
		
		
		// Create the chart and embed it in the AWT frame
		JFreeChart chart = ChartFactory.createXYLineChart(
	            "Dependency between Unit Weight and Water Content", // Title
	            "Water Content (%)",                               // X-Axis Label
	            "Unit Weight (kN/m³)",                             // Y-Axis Label
	            loadAndRefreshDataset(),                           // Dataset
	            PlotOrientation.VERTICAL,                         // Orientation
	            true,                                              // Show legend
	            true,                                              // Use tooltips
	            false                                              // Generate URLs
	        );
		ChartPanel chartPanel = new ChartPanel(chart);

		// Set BorderLayout for the container
		JPanel container = new JPanel(new BorderLayout());
       
		// Add the ChartPanel to the CENTER of the container
		container.removeAll();
		container.add(chartPanel, BorderLayout.CENTER);
		frame.removeAll();
		frame.add(container);

		// Refresh the graph for new data

		chartPanel.revalidate();
		chartPanel.repaint();
		container.revalidate();
		container.repaint();
		frame.revalidate();
		frame.repaint();
	}

	private Button addRefreshButtonWithPanelAndChart(Composite parent, Frame frame) {
		Composite buttonPanel = new Composite(parent, SWT.BOTTOM);
		buttonPanel.setLayout(new GridLayout(3, true));
		buttonPanel.setSize(100, 30);
		buttonPanel.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		Button button = new Button(buttonPanel, SWT.PUSH);
        button.setText("Refresh");
        button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        button.addListener(SWT.V_SCROLL, e -> createChartAndRefreshFrame(frame));
		return button;
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
