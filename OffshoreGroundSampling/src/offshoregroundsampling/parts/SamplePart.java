package offshoregroundsampling.parts;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import offshoregroundsampling.constants.Constants;
import offshoregroundsampling.dialog.SampleDialog;
import offshoregroundsampling.model.Sample;
import offshoregroundsampling.services.SampleService;

import java.text.SimpleDateFormat;
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

/**
 * This part contains logic for UI to display samples table and button for CRUD
 * operations.
 * 
 */
public class SamplePart {

	private TableViewer tableViewer;

	@Inject
	private MPart part;
	
	@Inject
    private Shell shell;
	
	private SampleService sampleService = new SampleService();		
	
	SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_COLLECTED_DATE_FORMAT);

	@PostConstruct
	public void createComposite(Composite parent) {
		createTableViewer(parent);
		parent.getShell().setMaximized(true); // To open window in maximized mode
	}

	/**
	 * This method creates sample data table.
	 * @param parent
	 * @return
	 */
	public TableViewer createTableViewer(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
	        
		// Create the table
		tableViewer = new TableViewer(parent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		// Define columns for the table
		createColumns(tableViewer.getTable());

		// Set the content provider (which tells the viewer how to retrieve the data)
		tableViewer.setContentProvider(new ArrayContentProvider());

		// Bind the data (the sample list from the backend)
		tableViewer.setInput(sampleService.getAllSamples());
		
		tableViewer.getTable().setHeaderVisible(true); // Shows headers
		tableViewer.getTable().setLinesVisible(true);  // Enables grid lines
		tableViewer.getTable().setSize(750,150); //Minimum Size of table
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
	    tableViewer.getTable().setLayoutData(gridData);
		 
	    // Buttons for operations
        createButtonsForTableOperations(parent);
		return tableViewer;
	}


	/**
	 * This method contains all the buttons related to CRUD operations for samples.
	 * @param parent
	 */
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

	/**
	 * This method creates columns for Table with sample data.
	 * @param table
	 */
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
                return formatter.format(((Sample) element).getDateCollected());
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

	/**
     * This is create event handler method for UI.
     */
    private void openAddSampleDialog(Composite parent) {
        // Implement a dialog to add a sample
    	SampleDialog sampleDialog = new SampleDialog(shell);
        if (sampleDialog.open() == SampleDialog.OK) {
            Sample sample = sampleDialog.getSample();
            sampleService.createSample(sample);   
        }
        tableViewer.setInput(sampleService.getAllSamples());
        tableViewer.refresh();
    }

    /**
     * This is edit event handler method for UI.
     */
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
        } else {
            // No selection
            MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
            messageBox.setMessage(Constants.NO_ROW_SELECTED);
            messageBox.open();
        }
    }

    /**
     * This is delete event handler method for UI.
     */
    private void deleteSample() {
    	IStructuredSelection selection = tableViewer.getStructuredSelection();
        if (!selection.isEmpty()) {
            // Get the selected Sample object
            sampleService.deleteSample((Sample) selection.getFirstElement());
            tableViewer.refresh(); // Refresh the table after editing
        } else {
            // No selection
            MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
            messageBox.setMessage(Constants.NO_ROW_SELECTED);
            messageBox.open();
        }
    }
    
	/**
	 * Utility method to get button for button panel.
	 * 
	 * @param buttonPanel
	 * @param labelName
	 * @return
	 */
	private Button getButton(Composite buttonPanel, String labelName) {
		Button button = new Button(buttonPanel, SWT.PUSH);
        button.setText(labelName);
        button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		return button;
	}

	/**
	 *  Utility method for creating table viewer column.
	 *  
	 * @param labelName
	 * @return
	 */
	private TableViewerColumn getTableViewerColumn(String labelName) {
        TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        tableViewerColumn.getColumn().setText(labelName);
        tableViewerColumn.getColumn().setWidth(100);
        tableViewerColumn.getColumn().setResizable(true);
		return tableViewerColumn;
	}
	
	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	@Persist
	public void save() {
		part.setDirty(false);
	}
 


}