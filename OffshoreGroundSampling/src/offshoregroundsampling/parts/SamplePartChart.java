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
import offshoregroundsampling.constants.Constants;
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
	public SamplePartChart() {
	}

	private JFreeChart chart;

	@PostConstruct
	public void createControls(Composite parent) {

		AtomicInteger windowCorrected = new AtomicInteger(0);
		
		createChart(parent);

		maximizeAndRestoreThePart();

		Timer timer = new Timer(1000, e -> {
			if (windowCorrected.incrementAndGet() < 3) {
				maximizeAndRestoreThePart();
			} else {
				((Timer) e.getSource()).stop();
			}
			
		});
		timer.start();
        
	}

	/**
	 * Maximize and restore the part stack using tags.
	 * @return
	 */
	private MElementContainer<MUIElement> maximizeAndRestoreThePart() {
		MElementContainer<MUIElement> partStack = part.getParent().getParent();
		partStack.getChildren().get(1).getTags().add("Maximized");
		partStack.getChildren().get(1).getTags().remove("Maximized");
		return partStack;
	}

	/**
	 * Create XY Line Chart under parent.
	 * 
	 * @param parent
	 */
	private void createChart(Composite parent) {

		// Set SWT layout
		parent.setLayout(new FillLayout(SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER));
		// Create the AWT Frame for embedding JFreeChart
		Composite awtFrame = new Composite(parent, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame(awtFrame);

		// Create the chart and embed it in the AWT frame
		chart = ChartFactory.createXYLineChart(Constants.CHART_TITLE_DEPENDENCY_BETWEEN_UNIT_WEIGHT_AND_WATER_CONTENT, // Title
				Constants.CHART_X_AXIS_WATER_CONTENT, // X-Axis Label
				Constants.CHART_Y_AXIS_UNIT_WEIGHT, // Y-Axis Label
				loadChartDataset(), // Dataset
				PlotOrientation.VERTICAL, // Orientation
				true, // Show legend
				true, // Use tooltips
				false // Generate URLs
		);

		ChartPanel chartPanel = new ChartPanel(chart);

		// Set BorderLayout for the container
		JPanel container = new JPanel(new BorderLayout());

		// Add the ChartPanel to the CENTER of the container
		container.add(chartPanel, BorderLayout.CENTER);
		frame.add(container);
	}

	/**
	 * Refresh Chart for button events
	 */
	public void refreshChart() {
		loadOrRefreshChartDataset((XYSeriesCollection) chart.getXYPlot().getDataset());
		chart.fireChartChanged();
	}

	/**
	 * Fetch data for chart
	 * 
	 * @return
	 */
	private XYSeriesCollection loadChartDataset() {
		return loadOrRefreshChartDataset(null);
	}

	/**
	 * Fetch or refresh data for chart
	 * 
	 * @return
	 */
	private XYSeriesCollection loadOrRefreshChartDataset(XYSeriesCollection dataset) {
		XYSeries series = dataset != null && dataset.getSeries() != null ? (XYSeries) dataset.getSeries().getFirst()
				: new XYSeries("Unit Weight vs Water Content");

		fillSeries(series);

		return dataset = dataset != null ? dataset : new XYSeriesCollection(series);

	}

	private void fillSeries(XYSeries series) {
		series.clear();
		sampleService.getAllSamples().forEach(sample -> {
			series.add(sample.getWaterContent(), sample.getUnitWeight());
		});
	}
}
