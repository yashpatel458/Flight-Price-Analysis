package DISNY;

public class Start_Selenium_Crawler {

	public void Crawl_Latest_Selenium(String[] cities, String date_month_string, int date_year) throws Exception 
	{
		String date_month = date_month_string;
		if(Integer.parseInt(date_month_string) < 10)
		{
			date_month = "0"+date_month_string;
		}
		
		Selenium_Crawler_Kayak SCK = new Selenium_Crawler_Kayak();
		Selenium_Crawler_Booking SCB = new Selenium_Crawler_Booking();
		Selenium_Crawler_Cheapflights SCO = new Selenium_Crawler_Cheapflights();
		
		for(int l=1; l<=3; l++)
		{
			for(int m=0; m<cities.length; m++)
			{
				for(int n=0; n<cities.length; n++)
				{
					if(m!=n)
					{
						String origin = cities[m];
						String destination = cities[n];
						
						if(l==1)
						{
							SCK.Kayak_Web_Crawler(origin, destination, date_month, date_year);
						}
						else if(l==2)
						{
							SCB.Booking_Web_Crawler(origin, destination, date_month, date_year);
						}
						else if(l==3)
						{
							SCO.Cheapflights_Web_Crawler(origin, destination, date_month, date_year);
						}
					}
				}
			}
		}
	}

}
