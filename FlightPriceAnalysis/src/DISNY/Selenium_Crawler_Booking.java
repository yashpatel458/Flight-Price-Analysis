package DISNY;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Selenium_Crawler_Booking {
	public void Booking_Web_Crawler(String origin, String destination, String date_month, int date_year) throws Exception 
	{
		int last_day_of_month = 30; // 1 3 5 7 8 10 12 
		if(date_month.matches("01") || date_month.matches("03") || date_month.matches("05") || date_month.matches("07") || date_month.matches("08") || date_month.matches("10") || date_month.matches("12") )
		{
			last_day_of_month = 31;
		}
		if(date_month.matches("02"))
		{
			last_day_of_month = 28;
		}
		
		convertCodes c = new convertCodes();
		String origin_short=c.gettingCodes(origin), destination_short=c.gettingCodes(destination);
		
		
		WebDriver driver = new EdgeDriver();
//		
		for(int f=1 ; f<=last_day_of_month; f++)
		{
			String date_day = "" + f;
			if(f<10)
			{
				date_day = "0" + f;
			}
			
			driver.get("https://flights.booking.com/flights/"+ origin_short +".AIRPORT-"+ destination_short +".AIRPORT/?type=ONEWAY&adults=1&cabinClass=ECONOMY&children=&from="+ origin_short +".AIRPORT&to="+ destination_short +"&Airport&depart=" + date_year + "-" + date_month + "-"+ date_day +"&sort=BEST");
			
			Thread.sleep(8000);

			String html = driver.getPageSource();
			
			try 
			{
				BufferedWriter writer = new BufferedWriter(new FileWriter("Crawled_Files_Booking//"+origin.toLowerCase()+"_to_"+destination.toLowerCase()+"_" + date_day + "_"+date_month+"_"+date_year+"_Booking" + ".html"));
				writer.write(html);
	            writer.close();
	            System.out.println(">>>> HTML file created successfully for Date " + date_day + " / "+date_month+" / "+date_year+" of " + origin + " to " + destination + " from Booking");
	        } 
			catch (IOException e) 
			{
//	            System.out.println("Error creating HTML file: " + e.getMessage());
	        }
		}
		driver.quit();
	}	
}