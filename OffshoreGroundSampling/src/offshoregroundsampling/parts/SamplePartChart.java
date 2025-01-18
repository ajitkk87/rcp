package offshoregroundsampling.parts;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
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
import offshoregroundsampling.services.SampleService;

/**
 * This part contains logic for graph creation for unit weight and water content.
 */
public class SamplePartChart {
	
	@Inject
	private SampleService sampleService;	
		
	@Inject
	private MPart part;
	
    @Inject
    public SamplePartChart() {}
    
    private Frame frame;

    @PostConstruct
	public void createControls(Composite parent) {

		AtomicInteger windowCorrected = new AtomicInteger(0);
		frame = getFrame(parent);
		createChartAndRefreshFrame();
		
		// Set the "Maximized" tag on the part stack
		maximizeAndRestoreTheFrame();
		
		Timer timer = new Timer(3000, e -> {
			if (windowCorrected.incrementAndGet() < 2) {
				maximizeAndRestoreTheFrame();
			}
		});
		timer.start();

	}

	private MElementContainer<MUIElement> maximizeAndRestoreTheFrame() {
		MElementContainer<MUIElement> partStack = part.getParent().getParent();
		partStack.getChildren().get(1).getTags().add("Maximized");
		partStack.getChildren().get(1).getTags().remove("Maximized");
		return partStack;
	}

	public Frame getFrame(Composite parent) {
		if (frame == null) {
			// Set SWT layout
			parent.setLayout(new FillLayout(SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER));
			// Create the AWT Frame for embedding JFreeChart
			Composite awtFrame = new Composite(parent, SWT.EMBEDDED);
			frame = SWT_AWT.new_Frame(awtFrame);
		}
		return frame;
	}

	/**
	 * 
	 */
	public void createChartAndRefreshFrame() {
		
		// Create the chart and embed it in the AWT frame
		JFreeChart chart = ChartFactory.createXYLineChart(
	            "Dependency between Unit Weight and Water Content", // Title
	            "Water Content (%)",                                // X-Axis Label
	            "Unit Weight (kN/m³)",                              // Y-Axis Label
	            loadAndRefreshDataset(),                            // Dataset
	            PlotOrientation.VERTICAL,                           // Orientation
	            true,                                               // Show legend
	            true,                                               // Use tooltips
	            false                                               // Generate URLs
	        );
		ChartPanel chartPanel = new ChartPanel(chart);

		// Set BorderLayout for the container
		JPanel container = new JPanel(new BorderLayout());
    
		// Add the ChartPanel to the CENTER of the container
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

   
	/* Fetch data for report
	 * @return
	 */
	private XYSeriesCollection loadAndRefreshDataset() {
		XYSeries series = new XYSeries("Unit Weight vs Water Content");
        
		sampleService.getAllSamples().forEach(sample -> {
        	series.add(sample.getWaterContent(), sample.getUnitWeight());
        });
       
        // Add the series to the dataset
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        
        return dataset;
	}
}
