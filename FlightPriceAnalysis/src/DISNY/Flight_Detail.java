package DISNY;

public class Flight_Detail {

	private String departureTime, destiationTime, originShort, destinationShort, duration;
	private String numberOfStops, airlineName, website;
	private int price;
	
	public Flight_Detail(String departureTime, String destiationTime, String originShort, String destinationShort, String duration,
			String numberOfStops, String airlineName, String website, int price) {
		this.departureTime = departureTime;
		this.destiationTime = destiationTime;
		this.originShort = originShort;
		this.destinationShort = destinationShort;
		this.duration = duration;
		this.numberOfStops = numberOfStops;
		this.airlineName = airlineName;
		this.website = website;
		this.price = price;
	}
	
	public String getDepartureTime() {
		return departureTime;	
	}
	
	public String getDestinationTime() {
		return destiationTime;	
	}
	
	public String getOriginShort() {
		return originShort;	
	}
	
	public String getDestinationShort() {
		return destinationShort;	
	}
	
	public String getDuration() {
		return duration;	
	}
	
	public String getNumberOfStops() {
		return numberOfStops;	
	}
	
	public String getAirlineName() {
		return airlineName;	
	}
	
	public String getWebsite() {
		return website;	
	}
	
	public int getPrice() {
		return price;	
	}
	
}

