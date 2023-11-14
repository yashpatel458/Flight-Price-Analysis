package DISNY;

import java.io.File;
import java.util.List;

import org.jsoup.Jsoup;

public class Onetravel_File_Parser {
	public Flight_Detail[] Onetravel_Parser(String origin, String destination, int date_month_string, int date_year, int date_day_input, String folderPath)
	{
		String onetravel_data = "";
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
		String path = folderPath + "//" + origin.toLowerCase() + "_to_" + destination.toLowerCase() + "_" + date_day + "_" + date_month + "_" + date_year + "_Onetravel" + ".html";
		File input = new File(path.toLowerCase());
        org.jsoup.nodes.Document doc=null;
        try {
            doc = Jsoup.parse(input, "UTF-8");
        } catch (Exception e) {
            System.out.println("Error parsing HTML file: Needs More Crawling ");
        }
        List<org.jsoup.nodes.Element> flightElements = doc.select("div.contract__content").subList(0, 4);

        Flight_Detail[] bestOnetravelFlights = new Flight_Detail[3];
        
        for (int k=0 ; k<3 ; k++) 
        {
            // Find the departure time element
            org.jsoup.nodes.Element timeElement = flightElements.get(k).selectFirst("time.is--flight-time");
            String time = timeElement.text();

            // Find the destination time element
            org.jsoup.nodes.Element destinationTimeElement = flightElements.get(k).select("li.timeline--list__time").last().selectFirst("time");
            String destinationTime = destinationTimeElement.text();
            
//            org.jsoup.nodes.Element originElement = doc.select("span[data-test=no_flightDetails]").first();
//            String origin_short = originElement.text();
            
//            org.jsoup.nodes.Element destinationElement = doc.selectFirst("li.timeline--list__time span[data-test=no_flightDetails]");

            // Extract the text content of the destination airport element
//            String destination_short = destinationElement.text();
//
            // Find the duration element
            org.jsoup.nodes.Element durationElement = flightElements.get(k).selectFirst(".stop__trip-duration");
            String duration = durationElement.text();
            
            List<org.jsoup.nodes.Element> locations = flightElements.get(k).select("span[data-test='no_flightDetails']");
            
            String origin_short = locations.get(0).text();
            String destination_short = locations.get(1).text();
            
            org.jsoup.nodes.Element stopElement = doc.selectFirst("div.stop__number");
            String numStops = stopElement.text().split(" ")[0];
            

            // Find the airline name element
            org.jsoup.nodes.Element airlineNameElement = flightElements.get(k).selectFirst(".trip__airline--name");
            String airlineName = airlineNameElement.text();

            // Find the price element
            org.jsoup.nodes.Element priceElement = flightElements.get(k).selectFirst(".is--fare__amount");
//            System.out.println(flightElements.get(k).selectFirst(".is--fare__amount").text());
            String price_object = priceElement.text().replace("C$", "").replace(".", " ");

            // Print the extracted flight details

            
            Flight_Detail currentFlight = new Flight_Detail(time, destinationTime, origin_short, destination_short, duration, numStops, airlineName, "Booking", Integer.parseInt(price_object.split(" ")[0].replace(",", "")));
            bestOnetravelFlights[k] = currentFlight; 
            
//            onetravel_data = onetravel_data + "#" + price_object.split(" ")[0] + "*" + origin_short+"_"+destination+"_"+time+"_"+destinationTime+"_"+duration+"_"+numStops+"_"+airlineName;
        }
		return bestOnetravelFlights;
	}

}
