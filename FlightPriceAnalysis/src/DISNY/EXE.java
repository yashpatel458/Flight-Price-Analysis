package DISNY;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class EXE {
	
	public static void main(String[] args) throws Exception 
	{
		LocalDate today = LocalDate.now();
		System.out.println(today);
	    String[] todaysDate = today.toString().split("-");
	    int valDay = Integer.parseInt(todaysDate[2])  , valMonth = Integer.parseInt(todaysDate[1]), valYear = Integer.parseInt(todaysDate[0]);
	    String regex = "^(0?[1-9]|[1-2][0-9]|3[0-1])\\/(0?[1-9]|1[0-2])\\/\\d{4}$";
		String input_date = "";
		Scanner scan = new Scanner(System.in);
		boolean quit = false;
		Search_Frequency SF = new Search_Frequency();
		isNumericCheck checkNum = new isNumericCheck();
		Word_Completion WC = new Word_Completion();
		while (quit == false) 
		{			
			boolean flag = true;
			int feature = 1;
			while(flag == true)
			{
				System.out.println("\n============ WELCOME TO AEROQUEST ============");
				System.out.println("\nSelect any one option:");
				System.out.println("[1] Perform Flight Price Analysis");
				System.out.println("[2] Explore Additional Features\n");
				System.out.print("Input : ");
				String feature_select = scan.next();
				if( checkNum(feature_select) )  {}
				
				if(Integer.parseInt(feature_select) == 1)
				{
					feature = 1;
					flag = false;
				}
				else if(Integer.parseInt(feature_select) == 2)
				{
					feature = 2;
					flag = false;
				}
				else 
				{
					System.out.println("\n ...Input not valid! Please enter 1 OR 2 ...\n");
				}
			}
			
			if(feature == 1)
			{
				flag = true;
				String start_crawl = "";
				while (flag == true) 
				{
					System.out.println("\nDo you want to crawl latest flights? Yes or No (Note: This requires system to be online) : ");
					start_crawl = scan.next().toLowerCase();
					
					if (start_crawl.matches("yes") == true)
					{	
						flag = false;
					} 
					else if (start_crawl.matches("no") == true)
					{
						flag = false;
					}
					else 
					{
						System.out.println("\n ...Input not valid! Please state Yes or No...\n");
					}
				}
				
				if(start_crawl.matches("yes"))
				{
					Runtime_Crawler RC = new Runtime_Crawler();
					System.out.print("\nEnter the Origin place: ");
					String origin = scan.next().toLowerCase();
					
					
					/// Word Completion Feature
					WC.Word_Completor(origin);
					
					
					/// Frequency Count Feature
					origin = SF.FrequencySearch_SpellChecking(origin);
					
					System.out.print("\nEnter the Destination place : ");
					String destination = scan.next().toLowerCase();
					WC.Word_Completor(destination);
					destination = SF.FrequencySearch_SpellChecking(destination);
					
					System.out.print("\nDate [dd/mm/yyyy] : ");
					flag = false;
					while (flag == false) 
					{
						input_date = scan.next();
						String[] input_date_arr = input_date.split("/");
						if (input_date.matches(regex)) 
						{
							if(Integer.parseInt(input_date_arr[0])  >= valDay)
							{
								if(Integer.parseInt(input_date_arr[1])  >= valMonth)
								{
									if(Integer.parseInt(input_date_arr[2])  >= valYear)
									{
										flag = true;
									}
									else 
									{
										System.out.println("\nPlease enter a valid date in the dd/mm/yyyy format.\n");
									}
								}
								else 
								{
									System.out.println("\nPlease enter a valid date in the dd/mm/yyyy format.\n");
								}
							}
							else 
							{
								System.out.println("\nPlease enter a valid date in the dd/mm/yyyy format.\n");
							}
						}
						else 
						{
							System.out.println("\nPlease enter a valid date in the dd/mm/yyyy format.\n");
						}
						
					}
					String[] date_object = input_date.split("/");
					WebDriver driver = new EdgeDriver();
					String folderPath = "/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/tmp";
//					SSC.Crawl_Latest_Selenium(origin, destination);
					RC.runtime_crawl(origin, destination, Integer.parseInt(date_object[0]), Integer.parseInt(date_object[1]), Integer.parseInt(date_object[2]), folderPath, driver);	
					
					Kayak_File_Parser KFP = new Kayak_File_Parser();
					Flight_Detail[] kayak_data_arr = KFP.Kayak_Parser(origin, destination, Integer.parseInt(date_object[1]), Integer.parseInt(date_object[2]) , Integer.parseInt(date_object[0]), folderPath);
					
					Booking_File_Parser BFP = new Booking_File_Parser();
					Flight_Detail[] booking_data_arr = BFP.Booking_Parser(origin, destination, Integer.parseInt(date_object[1]), Integer.parseInt(date_object[2]) , Integer.parseInt(date_object[0]), folderPath);
					
					Onetravel_File_Parser OFP = new Onetravel_File_Parser();
					Flight_Detail[] onetravel_data_arr = OFP.Onetravel_Parser(origin, destination, Integer.parseInt(date_object[1]), Integer.parseInt(date_object[2]) , Integer.parseInt(date_object[0]), folderPath);
					
					Flight_Detail[] result = new Flight_Detail[9];
					
					System.arraycopy(kayak_data_arr, 0, result, 0, 3);
					System.arraycopy(booking_data_arr, 0, result, 3, 3);
					System.arraycopy(onetravel_data_arr, 0, result, 6, 3);
					
					Sort_Flights sortObj = new Sort_Flights();
					sortObj.sortFlights(result);
					
					System.out.println("\nTop 3 Best results are : \n");
					
					System.out.println("-------------------------");
					for(int i=0; i<3; i++) {
						System.out.println(result[i].getOriginShort() + " - " + result[i].getDestinationShort() + " " + result[i].getDepartureTime() + " - " + result[i].getDestinationTime());
						System.out.println(result[i].getDuration() + " " + result[i].getNumberOfStops() + " " + result[i].getAirlineName());
						System.out.println("*C$ " + result[i].getPrice() + "* From: " + result[i].getWebsite());
						System.out.println("-------------------------");
					}
					
					Thread.sleep(1000);
					try { driver.quit(); }
					catch (Exception e) {}
			        
					String[] folderpaths = {"/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/tmp"};
					
					// Frequency Count
//					System.out.println("Frequency Count :");
					Frequency_Count FC = new Frequency_Count(0, null);
					System.out.println("\nEnter a Keyword for Frequency Count : ");
					String pat = scan.next();
					WC.Word_Completor(pat);
					pat = SF.FrequencySearch_SpellChecking(pat);
					FC.Frequency_Counter(folderpaths, pat);
					
					// Page Ranking
//					System.out.println("Page Ranking :");
					Page_Ranking PR = new Page_Ranking(0, null);
					System.out.println("\nEnter a Keyword for Page Ranking : ");
					pat = scan.next();
					WC.Word_Completor(pat);
					pat = SF.FrequencySearch_SpellChecking(pat);
					PR.Page_Ranker(folderpaths, pat);
					
					// Inverted Indexing
//					System.out.println("Inverted Indexing :");
					System.out.println("\nEnter a Keyword for Inverted Indexing : ");
					pat = scan.next();
					WC.Word_Completor(pat);
					pat = SF.FrequencySearch_SpellChecking(pat);
					call_Inverted_Index II = new call_Inverted_Index();
					II.Index_Inverter(folderpaths, pat);
					
					System.out.println("\n\t**all files from tmp folder are deleted**\n");
					
					Thread.sleep(10000);
				    // Create a File object for the folder
				    File folder = new File(folderPath);
				    
				    // Get the list of files in the folder
				    File[] files = folder.listFiles();
				    
				    // Loop through the files and delete each one
				    for (File file : files) {
				        if (file.isFile()) {
				            file.delete();
				        }
				    }
				}
				if(start_crawl.matches("no"))
				{
					String update = "";
					flag = true;
					while (flag == true) 
					{
						System.out.println("\nDo you want to download and updates the data for a particular Month ? : ");
						update = scan.next().toLowerCase();
						if (update.matches("yes") == true)
						{	
							flag = false;
						} 
						else if (update.matches("no") == true)
						{
							flag = false;
						}
						else 
						{
							System.out.println("\n ...Input not valid! Please state Yes or No...\n");
						}
					}
					
					if(update.matches("yes"))
					{
						System.out.println("\nEnter the month for which you want to update the local data [1-12]: ");
						String date_month_string = scan.next();
						System.out.println("Enter the year for which you want to update the local data [yyyy]: ");
						int date_year = scan.nextInt();
						
						String[] cities = {"Mumbai", "London", "Thailand", "Surat", "Pune"};
						
						Start_Selenium_Crawler SSC = new Start_Selenium_Crawler();
						SSC.Crawl_Latest_Selenium(cities, date_month_string, date_year);
						
						System.out.println("\nThe data for " + date_month_string + " is downloaded successfully.");
					}
					if(update.matches("no"))
					{
						System.out.print(". ");
						Thread.sleep(200);
						System.out.print(". ");
						Thread.sleep(200);
						System.out.print(". ");
						Thread.sleep(300);
						System.out.print("loading data from local directory ");
						Thread.sleep(300);
						System.out.print(". ");
						Thread.sleep(200);
						System.out.print(". ");
						Thread.sleep(200);
						System.out.print(".\n\n");
						Thread.sleep(1000);
						System.out.print("\nEnter the origin : ");
						String origin = scan.next().toLowerCase();
						WC.Word_Completor(origin);
						origin = SF.FrequencySearch_SpellChecking(origin);

//						Search_Frequency SF = new Search_Frequency();
						System.out.print("\nEnter the destination : ");
						String destination = scan.next().toLowerCase();
						WC.Word_Completor(destination);
						destination = SF.FrequencySearch_SpellChecking(destination);
						
						System.out.print("\nDate [dd/mm/yyyy] : ");
						flag = false;
						while (flag == false) 
						{
							
							input_date = scan.next();
							String[] input_date_arr = input_date.split("/");
							System.out.println( Integer.parseInt(input_date_arr[0]) + "" + Integer.parseInt(input_date_arr[1]) + "" + Integer.parseInt(input_date_arr[2]));
							if (input_date.matches(regex)) 
							{
								if(Integer.parseInt(input_date_arr[0])  >= valDay)
								{
									if(Integer.parseInt(input_date_arr[1])  >= valMonth)
									{
										if(Integer.parseInt(input_date_arr[2])  >= valYear)
										{
											flag = true;
										}
										else 
										{
											System.out.println("\nPlease enter a valid date in the dd/mm/yyyy format. (year) \n");
										}
									}
									else 
									{
										System.out.println("\nPlease enter a valid date in the dd/mm/yyyy format. (month) \n");
									}
								}
								else 
								{
									System.out.println("\nPlease enter a valid date in the dd/mm/yyyy format. (day) \n");
								}
							}
							else 
							{
								System.out.println("\nPlease enter a valid date in the dd/mm/yyyy format. (regex) \n");
							}
							
						}
						String[] date_object = input_date.split("/");
						System.out.println("");
						
						String folderPath = "Crawled_Files_Kayak";
						Kayak_File_Parser KFP = new Kayak_File_Parser();
						
						Flight_Detail[] kayak_data_arr = KFP.Kayak_Parser(origin, destination, Integer.parseInt(date_object[1]), Integer.parseInt(date_object[2]) , Integer.parseInt(date_object[0]), folderPath);
						
						folderPath = "Crawled_Files_Booking";
						Booking_File_Parser BFP = new Booking_File_Parser();
						Flight_Detail[] booking_data_arr = BFP.Booking_Parser(origin, destination, Integer.parseInt(date_object[1]), Integer.parseInt(date_object[2]) , Integer.parseInt(date_object[0]), folderPath);
						
						folderPath = "Crawled_Files_Onetravel";
						Onetravel_File_Parser OFP = new Onetravel_File_Parser();
						Flight_Detail[] onetravel_data_arr = OFP.Onetravel_Parser(origin, destination, Integer.parseInt(date_object[1]), Integer.parseInt(date_object[2]) , Integer.parseInt(date_object[0]), folderPath);
						
						Flight_Detail[] result = new Flight_Detail[9];
						
						System.arraycopy(kayak_data_arr, 0, result, 0, 3);
						System.arraycopy(booking_data_arr, 0, result, 3, 3);
						System.arraycopy(onetravel_data_arr, 0, result, 6, 3);
						
						Sort_Flights sortObj = new Sort_Flights();
						sortObj.sortFlights(result);
						
						System.out.println("\nTop 3 Best results are : \n");
						
						System.out.println("-------------------------");
						for(int i=0; i<3; i++) {
							System.out.println(result[i].getOriginShort() + " - " + result[i].getDestinationShort() + " " + result[i].getDepartureTime() + " - " + result[i].getDestinationTime());
							System.out.println(result[i].getDuration() + " " + result[i].getNumberOfStops() + " " + result[i].getAirlineName());
							System.out.println("*C$ " + result[i].getPrice() + "* From: " + result[i].getWebsite());
							System.out.println("-------------------------");
						}
					}
				}
			}
			
			
// IF YOU ENTER FEATURE NUMBER 2
			
			if(feature == 2)
			{
				String[] folderpaths = {"/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/Crawled_Files_Kayak", "/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/Crawled_Files_Booking", "/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/Crawled_Files_Onetravel"};
				
				// Frequency Count
				System.out.println("\n================================== FEATURE 1 =============================================");
				System.out.println("=>[Frequency Count] with [Word Completion & Spell Checking]");
				Frequency_Count FC = new Frequency_Count(0, null);
				System.out.println("\nEnter a City for Frequency Count: ");
				String pat = scan.next();
				WC.Word_Completor(pat);
				pat = SF.FrequencySearch_SpellChecking(pat);
				
				// Page Ranking
				System.out.println("\n================================== FEATURE 2 =============================================");
				Page_Ranking PR = new Page_Ranking(0, null);
				System.out.println("=>[Page Ranking] with [Word Completion, Spell Checking & Frequency Count]");
				System.out.println("\nEnter a City for Page Ranking : ");
				pat = scan.next();
				WC.Word_Completor(pat);
				pat = SF.FrequencySearch_SpellChecking(pat);
				PR.Page_Ranker(folderpaths, pat);
				
				// Inverted Indexing
				System.out.println("\n================================== FEATURE 3 =============================================");
				System.out.println("=>[Inverted Indexing] with [Word Completion, Spell Checking & Frequency Count]");
				System.out.println("\nEnter a City for Inverted Indexing : ");
				pat = scan.next();
				WC.Word_Completor(pat);
				pat = SF.FrequencySearch_SpellChecking(pat);
				call_Inverted_Index II = new call_Inverted_Index();
				II.Index_Inverter(folderpaths, pat);
				
			}
			
			

			String quit_string = "";
			flag = true;
			while (flag == true) 
			{
				System.out.println("\n================================================================================");
				System.out.println("That's a wrap! Do you want to exit the system : ");
				quit_string = scan.next().toLowerCase();
				
				if (quit_string.matches("yes") == true)
				{	
					quit = true;
					flag = false;
				} 
				else if (quit_string.matches("no") == true)
				{
					flag = false;
				}
				else 
				{
					System.out.println("\n ...Input not valid! Please state Yes or No...\n");
				}
			}
		} 
		scan.close();
	}

	private static boolean checkNum(String string) throws Exception 
	{
		int intValue;
		
			
	    if(string == null || string.equals("")) {
	        System.out.println("\nString cannot be parsed, it is null or empty.");
	        return false;
	    }
	    
	    try {
	        intValue = Integer.parseInt(string);
	        return true;
	    } catch (NumberFormatException e) {
	        System.out.println("\nInput String cannot be parsed to Integer.");
	    }
	    return false;
	}
}
