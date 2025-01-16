package offshoregroundsampling.model;

import java.util.Date;

import jakarta.persistence.Entity;

@Entity
public class Sample {

	private String sampleId;
	private String location;
	private Date dateCollected;
	private double unitWeight; // in kN/m3
	private double waterContent; // in percentage
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
		return dateCollected;
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