package offshoregroundsampling.dialog;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Text;

import offshoregroundsampling.constants.Constants;
import offshoregroundsampling.model.Sample;

import org.eclipse.swt.widgets.Label;

/**
 *  This class contains logic for popup/dialog for editing and creating a new sample.
 *  It also validates the values.
 */
public class SampleDialog extends Dialog {

	private Text sampleIdField;
	private Text locationField;
	private Text dateField;
	private Text unitWeightField;
	private Text waterContentField;
	private Text shearStrengthField;
	private Sample sample;

	public SampleDialog(Shell parentShell) {
		super(parentShell);
		this.sample = null;
	}

	public SampleDialog(Shell parentShell, Sample sample) {
		super(parentShell);
		this.sample = sample;
	}

	/**
	 * Dialog for sample creation and modifications.
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));

		new Label(container, SWT.NONE).setText(Constants.SAMPLE_ID);
		sampleIdField = new Text(container, SWT.BORDER);
		sampleIdField.setLayoutData(getLayoutDataWithWidth(200));

		new Label(container, SWT.NONE).setText(Constants.LOCATION);
		locationField = new Text(container, SWT.BORDER);
		locationField.setLayoutData(getLayoutDataWithWidth(200));

		new Label(container, SWT.NONE)
				.setText(Constants.DATE_COLLECTED + " (" + Constants.DATE_COLLECTED_DATE_FORMAT.toUpperCase() + ")");
		dateField = new Text(container, SWT.BORDER);
		dateField.setLayoutData(getLayoutDataWithWidth(200));

		new Label(container, SWT.NONE).setText(Constants.UNIT_WEIGHT + " (kN/m3):");
		unitWeightField = new Text(container, SWT.BORDER);
		unitWeightField.setLayoutData(getLayoutDataWithWidth(200));

		new Label(container, SWT.NONE).setText(Constants.WATER_CONTENT + " (%):");
		waterContentField = new Text(container, SWT.BORDER);
		waterContentField.setLayoutData(getLayoutDataWithWidth(200));

		new Label(container, SWT.NONE).setText(Constants.SHEAR_STRENGTH + " (kPa):");
		shearStrengthField = new Text(container, SWT.BORDER);
		shearStrengthField.setLayoutData(getLayoutDataWithWidth(200));

		if (sample != null) {
			setSampleToFields();
		}
		return container;
	}

	/**
	 * Basic validation logic for fields
	 * @return
	 */
	private boolean validateFields() {
		try {
			double unitWeight = Double.parseDouble(unitWeightField.getText());
			double waterContent = Double.parseDouble(waterContentField.getText());
			double shearStrength = Double.parseDouble(shearStrengthField.getText());

			if (waterContent <= 5 || waterContent >= 150) {
				showError(Constants.WATER_CONTENT_SHOULD_BE_BETWEEN_5_AND_150);
				return false;
			}
			if (unitWeight <= 12 || unitWeight >= 26) {
				showError(Constants.UNIT_WEIGHT_SHOULD_BE_BETWEEN_12_AND_26_K_N_M3);
				return false;
			}
			if (shearStrength <= 2 || shearStrength >= 1000) {
				showError(Constants.SHEAR_STRENGTH_SHOULD_BE_BETWEEN_2_AND_1000_K_PA);
				return false;
			}
		} catch (NumberFormatException e) {
			showError(Constants.ENTER_VALID_NUMERIC_VALUES);
			return false;
		}

		try {
			getDateFromString(dateField.getText());
		} catch (Throwable e) {
			showError(Constants.INVALID_DATE_FORMAT);
			return false;
		}
		return true;
	}

	private GridData getLayoutDataWithWidth(int width) {
		GridData textLayoutData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textLayoutData.widthHint = 400; // Set the desired width
		return textLayoutData;
	}

	@Override
	protected void okPressed() {
		// Validate the fields
		if (!validateFields()) {
			return;
		}

		// If valid, create a new Sample object and return it
		sample = sample != null ? sample : new Sample();
		getFieldsToSample();
		// Save to backend or update local list
		super.okPressed();
	}

	private void setSampleToFields() {
		sampleIdField.setText(sample.getSampleId());
		locationField.setText(sample.getLocation());
		dateField.setText(getDateString(sample.getDateCollected()));
		unitWeightField.setText(String.valueOf(sample.getUnitWeight()));
		waterContentField.setText(String.valueOf(sample.getWaterContent()));
		shearStrengthField.setText(String.valueOf(sample.getShearStrength()));
	}

	private String getDateString(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		// Define the date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_COLLECTED_DATE_FORMAT);
		// Format the LocalDate
		String formattedDate = localDate.format(formatter);
		return formattedDate;
	}

	private void getFieldsToSample() {
		sample.setSampleId(sampleIdField.getText());
		sample.setLocation(locationField.getText());
		sample.setDateCollected(getDateFromString(dateField.getText()));
		sample.setUnitWeight(Double.parseDouble(unitWeightField.getText()));
		sample.setWaterContent(Double.parseDouble(waterContentField.getText()));
		sample.setShearStrength(Double.parseDouble(shearStrengthField.getText()));
	}

	private Date getDateFromString(String date) {
		return Date.from(LocalDate.parse(date, DateTimeFormatter.ofPattern(Constants.DATE_COLLECTED_DATE_FORMAT))
				.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());
	}

	private void showError(String message) {
		MessageDialog.openError(getShell(), Constants.VALIDATION_ERROR, message);
	}

	public Sample getSample() {
		return sample;
	}

}