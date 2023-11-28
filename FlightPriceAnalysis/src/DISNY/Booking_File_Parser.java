package DISNY;

import java.io.File;

import org.jsoup.Jsoup;

public class Booking_File_Parser {

	public Flight_Detail[] Booking_Parser(String origin, String destination, int date_month_string, int date_year, int date_day_input, String folderPath) 
	{
		String booking_data = "";
		String date_month = ""+date_month_string;
		if(date_month_string < 10)
		{
			date_month = "0"+date_month_string;
		}
		
		String date_day = null;
		
		if(date_day_input<10)
		{
			date_day = "0" + date_day_input;
		}
		else
		{
			date_day = "" + date_day_input;
		}
		//Parsing
		String path = folderPath + "//" + origin.toLowerCase() + "_to_" + destination.toLowerCase() + "_" + date_day + "_" + date_month + "_" + date_year + "_Booking" + ".html";
		File input = new File(path.toLowerCase());
        org.jsoup.nodes.Document doc=null;
        try {
            doc = Jsoup.parse(input, "UTF-8");
        } catch (Exception e) {
            System.out.println("Error parsing HTML file:  Needs More Crawling ");
        }
        
        Flight_Detail[] bestBookingFlights = new Flight_Detail[3];
        
        for(int i=0 ; i<3; i++)
        {
        	org.jsoup.nodes.Element flightCard = doc.select("#flightcard-" + i + "").first();
            org.jsoup.nodes.Element departureTimeElem = flightCard.selectFirst("[data-testid=flight_card_segment_departure_time_0]");
            String departureTime = departureTimeElem.text();
//            System.out.println("Departure Time : "+departureTime);
            org.jsoup.nodes.Element destinationTimeElement = flightCard.selectFirst("div[data-testid=flight_card_segment_destination_time_0]");
            String destinationTime = destinationTimeElement.text();

//            System.out.println("Destination Time : " + destinationTime);
            org.jsoup.nodes.Element durationElement = flightCard.selectFirst("div[data-testid=flight_card_segment_duration_0]");
            String duration = durationElement.text();
//            System.out.println("Duration : "+duration);
            
            org.jsoup.nodes.Element element = flightCard.select("div[data-testid=flight_card_segment_stops_0]").first();
            String stops = element.text();
//            System.out.println("Stops : "+stops);
            
            org.jsoup.select.Elements originElement= flightCard.select("div[data-testid=flight_card_segment_departure_airport_0]");
            String originAirport = originElement.select("div[data-testid=flight_card_segment_departure_airport_0]").text();
//            System.out.println("Origin : "+originAirport);
            
            org.jsoup.nodes.Element destAirportElement = flightCard.selectFirst("[data-testid=flight_card_segment_destination_airport_0]");
            String destinationAirport = destAirportElement.text();
//            System.out.println("Destination : "+destinationAirport);
            org.jsoup.nodes.Element priceElement = flightCard.selectFirst(".FlightCardPrice-module__priceContainer___nXXv2");
            String[] price_object = priceElement.text().split(",");
            String price = price_object[0].replace("C$","").replace(".", "");
//            System.out.println("Price : "+price);
            
            org.jsoup.select.Elements airlineNameDiv = flightCard.select("div[data-testid=flight_card_carrier_"+i+"]");
            String airlineName = airlineNameDiv.text();
//            System.out.println("Airline Name : "+airlineName);
//            System.out.println("Visit Url : " + "https://flights.booking.com/flights/"+ originAirport +".AIRPORT-"+ destinationAirport +".AIRPORT/?type=ONEWAY&adults=1&cabinClass=ECONOMY&children=&from="+ originAirport +".AIRPORT&to="+ destinationAirport +"&Airport&depart=" + date_year + "-" + date_month + "-"+ date_day +"&sort=BEST");
//            System.out.println("");
            
            Flight_Detail currentFlight = new Flight_Detail(departureTime, destinationTime, originAirport, destinationAirport, duration, stops, airlineName, "Booking", Integer.parseInt(price));
            bestBookingFlights[i] = currentFlight;   
            
//            booking_data = booking_data + "#" + price + "*" + originAirport+"_"+destinationAirport+"_"+departureTime+"_"+destinationTime+"_"+duration+"_"+stops+"_"+airlineName;
        }
		return bestBookingFlights;
	}
}
