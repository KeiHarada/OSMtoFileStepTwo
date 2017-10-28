package graph;

public class SMNode {

	private long id;
	private double lat;
	private double lon;
	
	public SMNode(long id, double lon, double lat) {
		this.id = id;
		this.lon = lon;
		this.lat = lat;
	}

	public long getID(){
		return id;
	}
	
	public double getLongitude(){
		return lon;
	}
	
	public double getLatitude(){
		return lat;
	}
}
