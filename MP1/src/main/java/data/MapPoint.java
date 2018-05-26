package data;

public class MapPoint {

	private String subject;
	private double latitude;
	private double longitude;
	private String label;
	
	public MapPoint() {
		super();
	}
	public MapPoint(String subject) {
		super();
		this.subject = subject;
	}
	public MapPoint(String subject, double latitude, double longitude, String label) {
		super();
		this.subject = subject;
		this.latitude = latitude;
		this.longitude = longitude;
		this.label = label;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
