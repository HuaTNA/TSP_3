import java.io.IOException;

public class Node {
	private String name ; // node name
	private double lat ; // latitude coordinate
	private double lon ; // longitude coordinate
	
	
// constructors
	public Node () {
		
	}
	public Node ( String name , double lat , double lon ) {
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}

	// setters
	public void setName ( String name ) {
		this.name = name;
	}
	public void setLat ( double lat ) {
		this.lat = lat;
	}
	public void setLon ( double lon ) {
		this.lon = lon;
	}

	// getters
	public String getName () {
		return this.name;
	}
	public double getLat () {
		return this.lat;
	}
	public double getLon () {
		return this.lon;
	}
	
	private void reader(String name, double latitude, double longitude) {
		 this.name = name;
		 this.lat = latitude;
		 this.lon = longitude;
	}
	
	// get user info and edit node
	public void userEdit () throws IOException {
		this.reader(getInput.getString("   Name: "), getInput.getDouble("   latitude: ", -90, 90), getInput.getDouble("   longitude: ", -180, 180));
	}
	
	// print node info as a table row
	public void print() {
		 String string = "(" + this.lat + "," + this.lon + ")";
		    System.out.format("%19s%19s", this.name, string);
	}

   // calc distance btwn two nodes
	public static double distance ( Node i , Node j ) {
		final double lat_1 = i.lat;
		final double lat_2 = j.lat;
		final double lon_1 = i.lon;
		final double lon_2 = j.lon;
		final double distance = Math.pow(Math.sin(Math.toRadians(lat_1-lat_2)/2.00), 2) + Math.cos(Math.toRadians(lat_1)) * Math.cos(Math.toRadians(lat_2)) * Math.pow(Math.sin(Math.toRadians(lon_1 - lon_2) / 2.00), 2.00);
		
		return 2.00 * Math.atan2(Math.sqrt(distance), Math.sqrt(1.00-distance)) *6371;
	}
	
}	