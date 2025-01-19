package offshoregroundsampling.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SAMPLE_TABLE")
public class Sample {

	@Id
	@Column(name = "SAMPLE_ID")
	private String sampleId;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "DATE_COLLECTED", columnDefinition =  "DATE")
	private Date dateCollected;
	
	@Column(name = "UNIT_WEIGHT")
	private double unitWeight; // in kN/m3
	
	@Column(name = "WATER_CONTENT")
	private double waterContent; // in percentage
	
	@Column(name = "SHEAR_STRENGTH")
	private double shearStrength; // in kPa

	public Sample() {
	}
	
	public Sample(String sampleId, String location, Date dateCollected, double unitWeight, double waterContent,
			double shearStrength) {
		this.sampleId = sampleId;
		this.location = location;
		this.dateCollected = dateCollected;
		this.unitWeight = unitWeight;
		this.waterContent = waterContent;
		this.shearStrength = shearStrength;
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDateCollected() {
		return dateCollected instanceof java.sql.Date ? new Date(dateCollected.getTime()) : dateCollected;
	}

	public void setDateCollected(Date dateCollected) {
		this.dateCollected = dateCollected;
	}

	public double getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(double unitWeight) {
		this.unitWeight = unitWeight;
	}

	public double getWaterContent() {
		return waterContent;
	}

	public void setWaterContent(double waterContent) {
		this.waterContent = waterContent;
	}

	public double getShearStrength() {
		return shearStrength;
	}

	public void setShearStrength(double shearStrength) {
		this.shearStrength = shearStrength;
	}
}