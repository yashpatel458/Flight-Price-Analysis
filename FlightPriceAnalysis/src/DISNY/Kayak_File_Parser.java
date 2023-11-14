package DISNY;

import java.io.File;

import org.jsoup.Jsoup;

public class Kayak_File_Parser 
{

	public Flight_Detail[] Kayak_Parser(String origin, String destination, int date_month_string, int date_year, int date_day_input, String folderPath) 
	{
		String kayak_data="";
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
		String path = folderPath + "//" + origin.toLowerCase() + "_to_" + destination.toLowerCase() + "_" + date_day + "_" + date_month + "_" + date_year + "_Kayak" + ".html";
		File input = new File(path.toLowerCase());
        org.jsoup.nodes.Document doc = null;
        try 
        {
            doc = Jsoup.parse(input, "UTF-8");
        } catch (Exception e) 
        {
            System.out.println("Error parsing HTML file:  Needs More Crawling ");
        }

        // Find all links in the HTML file
        org.jsoup.select.Elements results = doc.select("div[data-resultid]");
        org.jsoup.select.Elements airlines = doc.select("div[class='J0g6-operator-text']");
        org.jsoup.select.Elements durations = doc.select("div[class='vmXl vmXl-mod-variant-default']");
        org.jsoup.select.Elements prices = doc.select("div[class='f8F1-price-text']");
        // Print the text and URL of each link
        
        Flight_Detail[] bestKayakFlights = new Flight_Detail[3];
        
        for (int k=0 ; k<3 ; k++) {
        	
            
            org.jsoup.select.Elements divisions = results.get(k).select("div");
            org.jsoup.select.Elements spans = divisions.select("span");

        		  int i=0;
        		  if(spans.get(5).text().length() == 0)
	            	{
	            		i = 1;
	            	}
	            	if(spans.get(6).text().length() == 0)
	            	{
	            		i = 2;
	            	}
	            	if(spans.get(7).text().length() == 0)
	            	{
	            		i = 3;
	            	}
	            	if(spans.get(0).text().length() != 0)
	            	{
	            		i = -5;
	            	}

		            String time_from = spans.get( (i+5) ).text();
		            String time_to = spans.get( (i+7) ).text();
		            String origin_short = spans.get( (i+9) ).text();
//		            String origin1 = spans.get( (i+10) ).text();
		            String destination_short = spans.get( (i+13) ).text();
//		            String destination1 = spans.get( (i+14) ).text();
		            String num_of_stops = spans.get( (i+15) ).text();
		            String airline_name = airlines.get(k).text();
		            String duration_of_flight = durations.get(k).text();
		            String[] price_object = prices.get(k).text().split(" ");
		            String price = price_object[1].replace(",", "");
		            
		            Flight_Detail currentFlight = new Flight_Detail(time_from, time_to, origin_short, destination_short, duration_of_flight, num_of_stops, airline_name, "Kayak", Integer.parseInt(price));
		           
		            bestKayakFlights[k] = currentFlight;
		            
//		            System.out.println("Departure Time : " + time_from);
//		            System.out.println("Destination Time : " + time_to);
//		            System.out.println("Duration : " + duration_of_flight);
//		            System.out.println("Stops : " + num_of_stops);
//		            System.out.println("Origin : " + origin_short);
//		            System.out.println("Destination : " + destination_short);
//		            System.out.println("Price : " + price_object[1]);
//		            System.out.println("Airline Name : " + airline_name);
//		            System.out.println("Visit Url : " + "https://www.ca.kayak.com/flights/" + origin_short + "-" + destination_short + "/" + date_year + "-" + date_month + "-" + date_day + "?sort=bestflight_a");
//		            System.out.println("");
		            
//		            kayak_data = kayak_data + "#" + price_object[1] + "*" + origin_short+"_"+destination+"_"+time_from+"_"+time_to+"_"+duration_of_flight+"_"+num_of_stops+"_"+airline_name;
		           
	}
		return bestKayakFlights;

	}
}
