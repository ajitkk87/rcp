package offshoregroundsampling.constants;

import java.text.SimpleDateFormat;

public class Constants {

	public static final String SHEAR_STRENGTH = "Shear Strength";

	public static final String WATER_CONTENT = "Water Content";

	public static final String UNIT_WEIGHT = "Unit Weight";

	public static final String DATE_COLLECTED = "Date Collected";

	public static final String LOCATION = "Location";

	public static final String SAMPLE_ID = "Sample ID";

	public static final String ADD_SAMPLE = "Add Sample";

	public static final String EDIT_SAMPLE = "Edit Sample";

	public static final String DELETE_SAMPLE = "Delete Sample";

	public static final String CONTEXT_SAMPLES = "samples";

	public static final String RECORD_MODIFIED_SUCCESSFULLY = "Record modified successfully";

	public static final String NO_ROW_SELECTED = "No row selected!";

	public static final String INVALID_DATE_FORMAT = "Invalid Date Format";
	
	public static final String ENTER_VALID_NUMERIC_VALUES = "Please enter valid numeric values.";
	
	public static final String SHEAR_STRENGTH_SHOULD_BE_BETWEEN_2_AND_1000_K_PA = "Shear strength should be between 2 and 1000 kPa";
	
	public static final String UNIT_WEIGHT_SHOULD_BE_BETWEEN_12_AND_26_K_N_M3 = "Unit weight should be between 12 and 26 kN/m3";
	
	public static final String WATER_CONTENT_SHOULD_BE_BETWEEN_5_AND_150 = "Water content should be between 5% and 150%";

	public static final String CHART_Y_AXIS_UNIT_WEIGHT = "Unit Weight (kN/m³)";

	public static final String CHART_X_AXIS_WATER_CONTENT = "Water Content (%)";

	public static final String CHART_TITLE_DEPENDENCY_BETWEEN_UNIT_WEIGHT_AND_WATER_CONTENT = "Dependency between Unit Weight and Water Content";

    public static final String H2_DB_DRIVER = "org.h2.Driver";
    
	public static final String H2_DB_JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"; // Modify as needed
	
    public static final String H2_DB_USER = "sa";
    
    public static final String H2_DB_PASSWORD = "";
    
	public static final String DATE_COLLECTED_DATE_FORMAT = "dd/MM/yyyy";
	
	public static final SimpleDateFormat DATE_COLLECTED_DATE_FORMATTER = new SimpleDateFormat(Constants.DATE_COLLECTED_DATE_FORMAT);
	
	public static final String VALIDATION_ERROR = "Validation Error";
	
	public static final String OFFSHOREGROUNDSAMPLING_PART_SAMPLECHART = "offshoregroundsampling.part.samplechart";
}
