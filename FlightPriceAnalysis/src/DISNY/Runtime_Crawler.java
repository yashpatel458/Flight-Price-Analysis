package DISNY;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Runtime_Crawler {

	public void runtime_crawl(String origin, String destination, int date_day_string, int date_month_string, int date_year, String folderPath, WebDriver driver) throws Exception 
	{
		
		convertCodes c = new convertCodes();
		
		String origin_short=c.gettingCodes(origin), destination_short=c.gettingCodes(destination); 
		
		
		String date_day = "" + date_day_string;
		if(date_day_string<10)
		{
			date_day = "0" + date_day_string;
		}
		
		String date_month = "" + date_month_string;
		if(date_month_string<10)
		{
			date_month = "0" + date_month_string;
		}
		
//		WebDriver driver = new EdgeDriver();
		
		//Kayak
		driver.get("https://www.ca.kayak.com/flights/" + origin_short + "-" + destination_short + "/" + date_year + "-" + date_month + "-" + date_day + "?sort=bestflight_a");
		
		Thread.sleep(8000);

		String html = driver.getPageSource();
		
		try 
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(""+folderPath+"//" + origin.toLowerCase() + "_to_" + destination.toLowerCase() + "_" + date_day + "_"+ date_month +"_" + date_year + "_Kayak" + ".html"));
			writer.write(html);
            writer.close();
            System.out.println(">>>> HTML file created successfully for Date " + date_day + " / "+date_month+" / "+date_year+" of " + origin + " to " + destination + " from Kayak");	            
        } 
		catch (IOException e) 
		{
//            System.out.println("Error creating HTML file: " + e.getMessage());
        }
		
		//Booking
		driver.get("https://flights.booking.com/flights/"+ origin_short +".AIRPORT-"+ destination_short +".AIRPORT/?type=ONEWAY&adults=1&cabinClass=ECONOMY&children=&from="+ origin_short +".AIRPORT&to="+ destination_short +"&Airport&depart=" + date_year + "-" + date_month + "-"+ date_day +"&sort=BEST");
		
		Thread.sleep(8000);

		html = driver.getPageSource();
		
		try 
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(""+folderPath+"//"+origin.toLowerCase()+"_to_"+destination.toLowerCase()+"_" + date_day + "_"+date_month+"_"+date_year+"_Booking" + ".html"));
			writer.write(html);
            writer.close();
            System.out.println(">>>> HTML file created successfully for Date " + date_day + " / "+date_month+" / "+date_year+" of " + origin + " to " + destination + " from Booking");
        } 
		catch (IOException e) 
		{
//            System.out.println("Error creating HTML file: " + e.getMessage());
        }
		
		//Cheapflights
		driver.get("https://www.cheapflights.ca/flight-search/"+origin_short+"-"+destination_short+"/"+date_year+"-"+date_month+"-"+date_day+"?csort=bestflight_a");
		
//		Thread.sleep(5000);
//		driver.findElement(By.cssSelector("[data-test='header-block__link']")).click();
//		Thread.sleep(2000);
//		driver.findElement(By.cssSelector("[aria-label='CAD']")).click();
//		Thread.sleep(2000);

		html = driver.getPageSource();
		
		try 
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(""+folderPath+"//"+origin.toLowerCase()+"_to_"+destination.toLowerCase()+"_" + date_day + "_"+date_month+"_"+date_year+"_Cheapflights" + ".html"));
			writer.write(html);
            writer.close();
            System.out.println(">>>> HTML file created successfully for Date " + date_day + " / "+date_month+" / "+date_year+" of " + origin + " to " + destination + " from Cheapflights");
        } 
		catch (IOException e) 
		{
//            System.out.println("Error creating HTML file: " + e.getMessage());
        }
		
		driver.manage().window().minimize();
//		Thread.sleep(30000);
//		driver.quit();
    }
}
