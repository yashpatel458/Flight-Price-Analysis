package DISNY;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class testing 
{
	public static void main(String[] args)
	{
		WebDriver driver = new EdgeDriver();
		
		driver.get("https://kayak.com/flights");
	}
}
