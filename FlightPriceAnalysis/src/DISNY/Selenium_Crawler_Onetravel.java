package DISNY;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Selenium_Crawler_Onetravel {
	
	public void Onetravel_Web_Crawler(String origin, String destination, String date_month, int date_year) throws Exception 
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
		
		boolean first = false;
		
		String origin_short="AMD", destination_short="DXB"; 
		
		if (origin.toLowerCase().matches("ahmedabad"))
		{
			origin_short = "AMD";
		}
		else if(origin.toLowerCase().matches("dubai"))
		{
			origin_short = "DXB";
		}
		else if(origin.toLowerCase().matches("toronto"))
		{
			origin_short = "YYZ";
		}
		
		if (destination.toLowerCase().matches("ahmedabad"))
		{
			destination_short = "AMD";
		}
		else if(destination.toLowerCase().matches("dubai"))
		{
			destination_short = "DXB";
		}
		else if(destination.toLowerCase().matches("toronto"))
		{
			destination_short = "YYZ";
		}
		WebDriver driver = new EdgeDriver();
//		
		for(int f=1 ; f<=last_day_of_month; f++)
		{
			String date_day = "" + f;
			if(f<10)
			{
				date_day = "0" + f;
			}
			
			driver.get("https://www.onetravel.com/air/listing?&d1="+origin_short+"&r1="+destination_short+"&dt1="+date_month+"/"+date_day+"/"+date_year+"&triptype=ONEWAYTRIP&cl=ECONOMY");
			
			if(first == false)
			{
				Thread.sleep(4000);
				driver.findElement(By.cssSelector("[data-test='header-block__link']")).click();
				Thread.sleep(2000);
				driver.findElement(By.cssSelector("[aria-label='CAD']")).click();
				Thread.sleep(2000);
				first = true;
			}
			else 
			{
				Thread.sleep(8000);
			}

			String html = driver.getPageSource();
			
			try 
			{
				BufferedWriter writer = new BufferedWriter(new FileWriter("Crawled_Files_Onetravel//"+origin.toLowerCase()+"_to_"+destination.toLowerCase()+"_" + date_day + "_"+date_month+"_"+date_year+"_Onetravel" + ".html"));
				writer.write(html);
	            writer.close();
	            System.out.println(">>>> HTML file created successfully for Date " + date_day + " / "+date_month+" / "+date_year+" of " + origin + " to " + destination + " from Onetravel");
	        } 
			catch (IOException e) 
			{
//	            System.out.println("Error creating HTML file: " + e.getMessage());
	        }
		}
		driver.quit();
	}

}
