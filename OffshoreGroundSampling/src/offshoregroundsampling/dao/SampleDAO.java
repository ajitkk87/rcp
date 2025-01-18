package offshoregroundsampling.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Creatable;
import jakarta.inject.Singleton;
import offshoregroundsampling.db.DatabaseConnectionManager;
import offshoregroundsampling.model.Sample;

@Creatable
@Singleton
public class SampleDAO {

	private static String CREATE_TABLE = """
			CREATE TABLE IF NOT EXISTS SAMPLE (
			    SAMPLE_ID VARCHAR(255) PRIMARY KEY,
			    LOCATION VARCHAR(255),
			    DATE_COLLECTED DATE,
			    UNIT_WEIGHT DOUBLE,
			    WATER_CONTENT DOUBLE,
			    SHEAR_STRENGTH DOUBLE
			);
			""";

	private static String INSERT_SQL = "INSERT INTO Sample (SAMPLE_ID, LOCATION, DATE_COLLECTED, UNIT_WEIGHT, WATER_CONTENT, SHEAR_STRENGTH) VALUES (?, ?, ?, ?, ?, ?)";

	private static String UPDATE_SQL = "UPDATE Sample SET SAMPLE_ID = ?, LOCATION = ?, DATE_COLLECTED = ?, UNIT_WEIGHT = ?, WATER_CONTENT = ?, SHEAR_STRENGTH = ? WHERE SAMPLE_ID = ?";

	private static String SELECT_ID_SQL = "SELECT * FROM Sample WHERE SAMPLE_ID = ?";

	private static String SELECT_SQL = "SELECT * FROM Sample";

	private static String DELETE_SQL = "DELETE FROM Sample WHERE SAMPLE_ID = ?";

	private static String DELETE_ALL_SQL = "DELETE FROM Sample";
	
	public static void initialize() {
		try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
				Statement stmt = conn.createStatement()) {
			stmt.execute(CREATE_TABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createSample(Sample sample) {
		try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL)) {
			pstmt.setString(1, sample.getSampleId());
			pstmt.setString(2, sample.getLocation());
			pstmt.setDate(3, new java.sql.Date(sample.getDateCollected().getTime()));
			pstmt.setDouble(4, sample.getUnitWeight());
			pstmt.setDouble(5, sample.getWaterContent());
			pstmt.setDouble(6, sample.getShearStrength());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Sample findSampleById(String sampleId) {
		Sample sample = null;

		try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ID_SQL)) {
			pstmt.setString(1, sampleId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				sample = new Sample(rs.getString("SAMPLE_ID"), rs.getString("LOCATION"), rs.getDate("DATE_COLLECTED"),
						rs.getDouble("UNIT_WEIGHT"), rs.getDouble("WATER_CONTENT"), rs.getDouble("SHEAR_STRENGTH"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sample;
	}

	public List<Sample> getAllSamples() {
		List<Sample> samples = new ArrayList<>();
		try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Sample sample = new Sample(rs.getString("SAMPLE_ID"), rs.getString("LOCATION"),
						new java.util.Date(rs.getDate("DATE_COLLECTED").getTime()), rs.getDouble("UNIT_WEIGHT"), rs.getDouble("WATER_CONTENT"),
						rs.getDouble("SHEAR_STRENGTH"));
				samples.add(sample);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return samples;
	}

	public void updateSample(Sample sample) {
		try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL)) {
			pstmt.setString(1, sample.getSampleId());
			pstmt.setString(2, sample.getLocation());
			pstmt.setDate(3, new java.sql.Date(sample.getDateCollected().getTime()));
			pstmt.setDouble(4, sample.getUnitWeight());
			pstmt.setDouble(5, sample.getWaterContent());
			pstmt.setDouble(6, sample.getShearStrength());
			pstmt.setString(7, sample.getSampleId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteSample(String sampleId) {
		try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {
			pstmt.setString(1, sampleId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteAllSamples() {
		try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(DELETE_ALL_SQL)) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
