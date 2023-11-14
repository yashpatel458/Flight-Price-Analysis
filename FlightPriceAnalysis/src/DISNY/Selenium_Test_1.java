package DISNY;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.swing.text.Document;

import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class Selenium_Test_1 
{
	public static void main(String[] args) throws InterruptedException 
	{
		WebDriver driver = new EdgeDriver();
//		
//		for(int f=1 ; f<=31; f++)
//		{
//			System.out.println("\n\t*******" + f + "*******\n");
//			if(f<10)
//			{
//				driver.get("https://www.ca.kayak.com/flights/AMD-YYZ/2023-05-0" + f + "?sort=bestflight_a");
//			}
//			else
//			{
//				driver.get("https://www.ca.kayak.com/flights/AMD-YYZ/2023-05-" + f + "?sort=bestflight_a");
//			}
//			Thread.sleep(10000);
//
//			String html = driver.getPageSource();
//			
//			try 
//			{
//	            BufferedWriter writer = new BufferedWriter(new FileWriter("Crawled_Files//AMDtoYYZ_2023_04_" + f + ".html"));
////	            writer.write(html.getText());
//	            writer.write(html);
//	            writer.close();
//	            System.out.println(">>>> HTML file created successfully.");
//	        } 
//			catch (IOException e) 
//			{
//	            System.out.println("Error creating HTML file: " + e.getMessage());
//	        }
////			
////			//Parsing
//			File input = new File("Crawled_Files//AMDtoYYZ_2023_04_" + f + ".html");
//	        org.jsoup.nodes.Document doc;
//	        try {
//	            doc = Jsoup.parse(input, "UTF-8");
//	        } catch (IOException e) {
//	            System.out.println("Error parsing HTML file: " + e.getMessage());
//	            return;
//	        }
//
//	        // Find all links in the HTML file
//	        org.jsoup.select.Elements results = doc.select("div[data-resultid]");
//	        
//	        org.jsoup.select.Elements classes = doc.select("div[class='f8F1-price-text']");
//	        // Print the text and URL of each link
//	        for (int k=0 ; k<=2 ; k++) {
//	            
//	            org.jsoup.select.Elements divisions = results.get(k).select("div");
//	            org.jsoup.select.Elements spans = divisions.select("span");
//
//	        		  int i=0;
//	        		  if(spans.get(5).text().length() == 0)
//		            	{
//		            		i = 1;
//		            	}
//		            	if(spans.get(6).text().length() == 0)
//		            	{
//		            		i = 2;
//		            	}
//		            	if(spans.get(7).text().length() == 0)
//		            	{
//		            		i = 3;
//		            	}
//		            	if(spans.get(0).text().length() != 0)
//		            	{
//		            		i = -5;
//		            	}
//
//			            String time_from = spans.get( (i+5) ).text();
//			            String time_to = spans.get( (i+7) ).text();
//			            String origin_short = spans.get( (i+9) ).text();
//			            String origin = spans.get( (i+10) ).text();
//			            String destination_short = spans.get( (i+13) ).text();
//			            String destination = spans.get( (i+14) ).text();
//			            String num_of_stops = spans.get( (i+15) ).text();
//			            
//			            System.out.println("Time : " + time_from + " to " + time_to + "\t" + num_of_stops);
//			            System.out.println("From :" + origin_short + " " + origin + " - " + destination_short + " " + destination);
//			            System.out.println(classes.get(k).text());  	
//		}

//        }
		driver.get("https://flights.booking.com/flights/"+ "AMD" +".AIRPORT-"+ "YYZ" +".AIRPORT/?type=ONEWAY&adults=1&cabinClass=ECONOMY&children=&from="+ "AMD" +".AIRPORT&to="+ "YYZ" +"&Airport&depart=2023-05-"+ "10" +"&sort=BEST");
		// https://www.booking.com/flights/index.en-gb.html?aid=304142&label=gen173nr-1FEg1mbGlnaHRzX2luZGV4KIICQgVpbmRleEgJWARoJ4gBAZgBCbgBF8gBDNgBAegBAfgBAogCAagCA7gCn8OsoQbAAgHSAiRhN2Y3MWI1NC1mNzk1LTQzZDEtODY1Yi03M2VmNjgyNmExNTPYAgXgAgE
//		Thread.sleep(10000);
//		driver.quit();
	}
}
