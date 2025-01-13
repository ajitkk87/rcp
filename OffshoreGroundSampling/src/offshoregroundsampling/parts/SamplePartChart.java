package offshoregroundsampling.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
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
import offshoregroundsampling.model.Sample;

public class SamplePartChart {

	@Inject
    private IEclipseContext context;

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
        @SuppressWarnings("unchecked")
		List<Sample> samples = (List<Sample>) context.get(Constants.CONTEXT_SAMPLES);
        JFreeChart chart = createChart(samples != null ? samples : new ArrayList<Sample>());
        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel);
    }

    private JFreeChart createChart(List<Sample> samples) {
        // Create the data series
        XYSeries series = new XYSeries("Unit Weight vs Water Content");
        
        samples.forEach(sample -> {
        	series.add(sample.getUnitWeight(), sample.getWaterContent());
        });
       
        // Add the series to the dataset
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Create the chart
        return ChartFactory.createXYLineChart(
            "Dependency between Unit Weight and Water Content", // Title
            "Water Content (%)",                               // X-Axis Label
            "Unit Weight (kN/m³)",                             // Y-Axis Label
            dataset,                                           // Dataset
            PlotOrientation.VERTICAL,                         // Orientation
            true,                                              // Show legend
            true,                                              // Use tooltips
            false                                              // Generate URLs
        );
    }
}
