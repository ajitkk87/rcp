package offshoregroundsampling.parts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import offshoregroundsampling.constants.Constants;
import offshoregroundsampling.dialog.SampleDialog;
import offshoregroundsampling.model.Sample;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class SamplePart {

	private TableViewer tableViewer;

	@Inject
	private MPart part;
	
	@Inject
    private Shell shell;
	
	@Inject
	private IEclipseContext context;
	
    
	// Simulating an API call to fetch sample data
	List<Sample> samples = new ArrayList<>();			

	@PostConstruct
	public void createComposite(Composite parent) {
		createTableViewer(parent);
		parent.getShell().setMaximized(true); // To open window in maximized mode
	}


	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	@Persist
	public void save() {
		part.setDirty(false);
	}
 
	public void addRefreshContext() {
        // Store data in the context, useful to share data between parts
        context.set(Constants.CONTEXT_SAMPLES, samples);
    }
	
	public TableViewer createTableViewer(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
	        
		// Create the table
		tableViewer = new TableViewer(parent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		// Define columns for the table
		createColumns(tableViewer.getTable());

		// Set the content provider (which tells the viewer how to retrieve the data)
		tableViewer.setContentProvider(new ArrayContentProvider());

		samples = getSamplesFromBackend();
		// Bind the data (the sample list from the backend)
		tableViewer.setInput(samples);
		
		tableViewer.getTable().setHeaderVisible(true); // Shows headers
		tableViewer.getTable().setLinesVisible(true);  // Enables grid lines
		tableViewer.getTable().setSize(850,150); //Minimum Size of table
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
	    tableViewer.getTable().setLayoutData(gridData);
		 
	    // Buttons for operations
        createButtonsForTableOperations(parent);
		addRefreshContext();
		return tableViewer;
	}


	private void createButtonsForTableOperations(Composite parent) {
		Composite buttonPanel = new Composite(parent, SWT.NONE);
        buttonPanel.setLayout(new GridLayout(3, true));
        buttonPanel.setSize(600, 30);
        buttonPanel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Button addButton = getButton(buttonPanel, Constants.ADD_SAMPLE);
        addButton.addListener(SWT.Selection, e -> openAddSampleDialog(parent));

        Button editButton = getButton(buttonPanel, Constants.EDIT_SAMPLE);
        editButton.addListener(SWT.Selection, e -> openEditSampleDialog());
     
        // Button click listener to show the selected name
        editButton.addListener(SWT.Selection, e -> {
            // Get the selection
        });

        Button deleteButton = getButton(buttonPanel, Constants.DELETE_SAMPLE);
        deleteButton.addListener(SWT.Selection, e -> deleteSample());
	}


	private Button getButton(Composite buttonPanel, String labelName) {
		Button addButton = new Button(buttonPanel, SWT.PUSH);
        addButton.setText(labelName);
        addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		return addButton;
	}

	private void createColumns(Table table) {		
		
		TableViewerColumn sampleIdColumn = getTableViewerColumn(Constants.SAMPLE_ID);
        sampleIdColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return ((Sample) element).getSampleId();
            }
        });
        
        // Column for Location
        TableViewerColumn locationColumn = getTableViewerColumn(Constants.LOCATION);
        locationColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return ((Sample) element).getLocation();
            }
        });

        // Column for Date Collected
        TableViewerColumn dateCollectedColumn = getTableViewerColumn(Constants.DATE_COLLECTED);
        dateCollectedColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return String.valueOf(((Sample) element).getDateCollected());
            }
        });
        
        // Column for Unit Weight
        TableViewerColumn unitWeightColumn = getTableViewerColumn(Constants.UNIT_WEIGHT);
        unitWeightColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return String.valueOf(((Sample) element).getUnitWeight());
            }
        });
        
        // Column for Water Content
        TableViewerColumn waterContentColumn = getTableViewerColumn(Constants.WATER_CONTENT);
        waterContentColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return String.valueOf(((Sample) element).getWaterContent());
            }
        });
        
        // Column for Shear Strength
        TableViewerColumn shearStrengthColumn = getTableViewerColumn(Constants.SHEAR_STRENGTH);
        shearStrengthColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return String.valueOf(((Sample) element).getShearStrength());
            }
        });
        
        //Adjust table width based on percentage of window size
        table.addControlListener(new ControlAdapter() {
            @Override
            public void controlResized(ControlEvent e) {
                int tableWidth = table.getClientArea().width;
                sampleIdColumn.getColumn().setWidth((int) (tableWidth * 0.20)); // 10% width
                locationColumn.getColumn().setWidth((int) (tableWidth * 0.15)); // 10% width
                dateCollectedColumn.getColumn().setWidth((int) (tableWidth * 0.22)); // 10% width
                unitWeightColumn.getColumn().setWidth((int) (tableWidth * 0.13)); // 10% width
                waterContentColumn.getColumn().setWidth((int) (tableWidth * 0.15)); // 45% width
                shearStrengthColumn.getColumn().setWidth((int) (tableWidth * 0.15)); // 45% width
            }
        });
	}


	private TableViewerColumn getTableViewerColumn(String labelName) {
		// Column for Sample ID
        TableViewerColumn sampleIdColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        sampleIdColumn.getColumn().setText(labelName);
        sampleIdColumn.getColumn().setWidth(100);
        sampleIdColumn.getColumn().setResizable(true);
		return sampleIdColumn;
	}
	
	// This method would interact with the backend to fetch the sample data
	private List<Sample> getSamplesFromBackend() {

		// Example of adding a sample manually
		samples.add(new Sample("S001", "Location1", new Date(), 20.5, 35.0, 150.0));
		samples.add(new Sample("S002", "Location2", new Date(), 19.5, 30.0, 140.0));
		samples.add(new Sample("S003", "Location3", new Date(), 20.5, 35.0, 150.0));
		samples.add(new Sample("S004", "Location4", new Date(), 19.5, 30.0, 140.0));
		samples.add(new Sample("S005", "Location5", new Date(), 20.5, 35.0, 150.0));
		samples.add(new Sample("S006", "Location6", new Date(), 19.5, 30.0, 140.0));

		/*
		RestTemplate restTemplate = new RestTemplate();

		// Example: Fetching all samples
		ResponseEntity<List<Sample>> response = restTemplate.exchange(
		        "http://your-backend-url/api/samples", HttpMethod.GET, null,
		        new ParameterizedTypeReference<List<Sample>>() {});

		List<Sample> samples = response.getBody();
		*/
		
		return samples;
	}
	
    private void openAddSampleDialog(Composite parent) {
        // Implement a dialog to add a sample
    	SampleDialog sampleDialog = new SampleDialog(shell);
        if (sampleDialog.open() == SampleDialog.OK) {
            Sample sample = sampleDialog.getSample();
            samples.add(sample);
        }
        tableViewer.setInput(samples);
        tableViewer.refresh();
        addRefreshContext();
    }

    private void openEditSampleDialog() {
        // Implement editing functionality
    	IStructuredSelection selection = tableViewer.getStructuredSelection();
        if (!selection.isEmpty()) {
            // Get the selected Sample object
            Sample selectedSample = (Sample) selection.getFirstElement();
            SampleDialog sampleDialog = new SampleDialog(shell, selectedSample);
            if (sampleDialog.open() == SampleDialog.OK) {
            	 MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                 messageBox.setMessage(Constants.RECORD_MODIFIED_SUCCESSFULLY);
                 messageBox.open();
            }
            tableViewer.refresh(); // Refresh the table after editing
            addRefreshContext();
        } else {
            // No selection
            MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
            messageBox.setMessage(Constants.NO_ROW_SELECTED);
            messageBox.open();
        }
    }

    private void deleteSample() {
    	IStructuredSelection selection = tableViewer.getStructuredSelection();
        if (!selection.isEmpty()) {
            // Get the selected Sample object
            Sample selectedSample = (Sample) selection.getFirstElement();
            samples.remove(selectedSample);
            tableViewer.refresh(); // Refresh the table after editing
            addRefreshContext();
        } else {
            // No selection
            MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
            messageBox.setMessage(Constants.NO_ROW_SELECTED);
            messageBox.open();
        }
    }

}