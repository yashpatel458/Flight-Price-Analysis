package DISNY;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class testing 
{
	public static void main(String[] args) throws Exception {

		EdgeDriver driver = new EdgeDriver();
		String folderPath = "/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/tmp";
//					SSC.Crawl_Latest_Selenium(origin, destination);
		Runtime_Crawler RC = new Runtime_Crawler();
		RC.runtime_crawl("mumbai", "tokyo", 12,12, 2023, folderPath, driver);

		Kayak_File_Parser KFP = new Kayak_File_Parser();
		Flight_Detail[] kayak_data_arr = KFP.Kayak_Parser("mumbai", "tokyo", 12,12, 2023, folderPath);

	/*	convertCodes c = new convertCodes();

		String s = c.gettingCodes("MumbaI");

		System.out.println(s);*/
	}
}
