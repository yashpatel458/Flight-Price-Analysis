package DISNY;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AEROQUEST {
	private static final String DATE_REGEX =
	        "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$";

	private static final Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX);

	public static boolean isValidDate(String date) {
	    Matcher matcher = DATE_PATTERN.matcher(date);

	    if (matcher.matches()) {
	        int day = Integer.parseInt(matcher.group(1));
	        int month = Integer.parseInt(matcher.group(2));
	        int year = Integer.parseInt(matcher.group(3));

	        if (month >= 1 && month <= 12) {
	            int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	            // Leap year check
	            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
	                daysInMonth[2] = 29; 
	            }

	            return day >= 1 && day <= daysInMonth[month - 1];
	        }
	    }

	    return false;
	}
	
	 
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
		Yash_Word_Completion WC = new Yash_Word_Completion();
		//htmlParserDP hParser = new htmlParserDP();
		while (quit == false) 
		{			
			boolean flag = true;
			int feature = 1;
			while (flag) {
	            try {
	                System.out.println("\n============ WELCOME TO AEROQUEST ============");
	                System.out.println("\nSelect any one option:");
	                System.out.println("[1] Perform Flight Price Analysis & Explore Additional Features");
	                System.out.println("[2] Exit\n");
	                System.out.print("Input : ");
	                String feature_select = scan.next();

	                if (checkNum(feature_select)) {
	                    // Handle the valid numeric input here if needed
	                }

	                int selectedOption = Integer.parseInt(feature_select);

	                if (selectedOption == 1) {
	                    feature = 1;
	                    flag = false;
	                } else if (selectedOption == 2) {
	                    feature = 2;
	                    flag = false;
	                } else {
	                    System.out.println("\n Input not valid! Please enter 1 OR 2 \n");
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("\n Input not valid! Please enter a valid number\n");
	   
	            }
	        }
			///////////// FEATURE 1 /////////////
			if(feature == 1)
			{			
				///////////// CRAWL = YES /////////////
				
				
					Runtime_Crawler RC = new Runtime_Crawler();
					
					///////////// ORIGIN /////////////
					System.out.print("\nEnter the Origin place: ");
					scan.nextLine();
					String origin = scan.nextLine().toLowerCase();	
					WC.Word_Completor(origin);			
					origin = SF.Search_Frequency(origin);
					
					
					///////////// DESTINATION /////////////
					
					
					boolean ifSame = true;
					String destination = null;
					while(ifSame) {
						System.out.print("\nEnter the Destination place : ");
						destination = scan.nextLine().toLowerCase();
						WC.Word_Completor(destination);
						destination = SF.Search_Frequency(destination);
						
						// Check if origin and destination are the same
						if (!origin.equals(destination)) {
							ifSame = false;
							break;
						}else {
							System.out.println("\nError: Origin and destination cannot be the same. Please enter different locations.");
							
						}
						
					}
										
					
					///////////// DATE /////////////					
					
					while (flag == false) {
						try {
						    System.out.print("\nDate [dd/mm/yyyy] : ");
						    input_date = scan.next();
						    String[] input_date_arr = input_date.split("/");

						    if (input_date.matches(regex)) {
						        LocalDate enteredDate = LocalDate.of(Integer.parseInt(input_date_arr[2]), Integer.parseInt(input_date_arr[1]), Integer.parseInt(input_date_arr[0]));

						        // Check if the entered date is a valid future date
						        if (enteredDate.isAfter(LocalDate.now()) && isValidDate(enteredDate)) {
						            // Valid date
						            flag = true;
						        } else {
						            System.out.println("\nPlease enter a valid date after today in the dd/mm/yyyy format.\n");
						        }
						    } else {
						        System.out.println("\nPlease enter a valid date in the dd/mm/yyyy format.\n");
						    }
						} catch (Exception e) {
						    System.out.println("\nIncorrect date! Please try again.\n");
						}

			         
			        }
				
		       			        
			        
					String[] date_object = input_date.split("/");
					WebDriver driver = new EdgeDriver();
					String folderPath = "/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/tmp";
					RC.runtime_crawl(origin, destination, Integer.parseInt(date_object[0]), Integer.parseInt(date_object[1]), Integer.parseInt(date_object[2]), folderPath, driver);	
					
					
					
					///////////// PARSING /////////////
					try {
					    Kayak_File_Parser KFP = new Kayak_File_Parser();
					    Flight_Detail[] kayak_data_arr = KFP.Kayak_Parser(origin, destination, Integer.parseInt(date_object[1]), Integer.parseInt(date_object[2]), Integer.parseInt(date_object[0]), folderPath);

					    Booking_File_Parser BFP = new Booking_File_Parser();
					    Flight_Detail[] booking_data_arr = BFP.Booking_Parser(origin, destination, Integer.parseInt(date_object[1]), Integer.parseInt(date_object[2]), Integer.parseInt(date_object[0]), folderPath);

					    Cheapflights_File_Parser OFP = new Cheapflights_File_Parser();
					    Flight_Detail[] Cheapflights_data_arr = OFP.Cheapflights_File_Parser(origin, destination, Integer.parseInt(date_object[1]), Integer.parseInt(date_object[2]), Integer.parseInt(date_object[0]), folderPath);

					    if (kayak_data_arr == null || booking_data_arr == null || Cheapflights_data_arr == null
					            || kayak_data_arr.length == 0 || booking_data_arr.length == 0 || Cheapflights_data_arr.length == 0) {
					        System.out.println("Error: No data found from one or more parsers");
					    } else {
					        Flight_Detail[] result = new Flight_Detail[9];

					        System.arraycopy(kayak_data_arr, 0, result, 0, Math.min(kayak_data_arr.length, 3));
					        System.arraycopy(booking_data_arr, 0, result, 3, Math.min(booking_data_arr.length, 3));
					        System.arraycopy(Cheapflights_data_arr, 0, result, 6, Math.min(Cheapflights_data_arr.length, 3));

					        Sort_Flights sortObj = new Sort_Flights();
					        sortObj.sortFlights(result);

					        System.out.println("\nTop 3 Best Results are: \n");
					        System.out.println("-------------------------");
					        for (int i = 0; i < Math.min(result.length, 3); i++) {
					            if (result[i] != null) {
					                System.out.println(result[i].getOriginShort() + " - " + result[i].getDestinationShort() + " " + result[i].getDepartureTime() + " - " + result[i].getDestinationTime());
					                System.out.println(result[i].getDuration() + " " + result[i].getNumberOfStops() + " " + result[i].getAirlineName());
					                System.out.println("*C$ " + result[i].getPrice() + "* From: " + result[i].getWebsite());
					                System.out.println("-------------------------");
					            } else {
					                System.out.println("Error: Flight_Detail object is null at index " + i);
					            }
					        }
					    }
					} catch (NumberFormatException e) {
					    // Handle the case where Integer.parseInt fails
					    System.out.println("Error: Unable to parse integers from date_object");
//					    e.printStackTrace();
					} catch (IndexOutOfBoundsException e) {
					    // Handle the case where array indices go out of bounds
					    System.out.println("Error: Array index out of bounds");
//					    e.printStackTrace();
					} catch (NullPointerException e) {
					    // Handle the case where a Flight_Detail object is null
					    System.out.println("Error: Flight_Detail object is null");
//					    e.printStackTrace();
					} catch (Exception e) {
					    // Handle other unexpected exceptions
//					    e.printStackTrace();
					}


			
	
					Thread.sleep(1000);

					
					///////////// SAVING FILES TO TMP FOLDER /////////////
					
			        
					String folderpaths = "/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/tmp";
					
					File folder = new File(folderpaths);
					
					for(File f : folder.listFiles()) {
						if(f.isFile() && f.getName().endsWith(".html")) {
							System.out.println(f.getName());
						}
					}
					
				    htmlParserDP.htmlParsing(folderpaths);
				    
//=======================================================================================
				    
				   // Inverted Indexing
					System.out.println("\n============================================================================");
				    System.out.println("\nEnter a Keyword for Inverted Indexing : ");
				    String folderToIndex = "/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/DISNY/parsedFiles";  
				    Scanner sc = new Scanner(System.in);
				    String key = sc.nextLine();
				    
				    invertedIndexingDP.invertedFinal(folderToIndex, key);
				    
				    
				    //Pattern finding
				    System.out.println("\n============================================================================");
				    System.out.println("\nInput anything to start pattern finding : ");

				    String anyInput = sc.next();
				    
				    if(anyInput!=null) {
					    patternFindingUsingRegex.patterns("C\\$[\\s]+[0-9]*", folderToIndex);
				    }
				    
					// Frequency Count
					Frequency_Count FC = new Frequency_Count(0, null);
					System.out.println("\n============================================================================");
					System.out.println("\nEnter a Keyword for Frequency Count : ");
					String pat = scan.next();
//					WC.Word_Completor(pat);
//					pat = SF.Search_Frequency(pat);
					FC.Frequency_Counter(folderpaths, pat);
					
					
					
					// Page Ranking
					Page_Ranking PR = new Page_Ranking(0, null);
					System.out.println("\n============================================================================");
					System.out.println("\nEnter a Keyword for Page Ranking : ");
					pat = scan.next();
//					WC.Word_Completor(pat);
//					pat = SF.Search_Frequency(pat);
					PR.Page_Ranker(folderpaths, pat);
//					

				    // Create a File object for the folder
				   File folder2 = new File(folderPath);
				    
				    // Get the list of files in the folder
				   File[] files = folder2.listFiles();
//				    
//				    // Loop through the files and delete each one
				    for (File file : files) {
				        if (file.isFile()) {
				            file.delete();
				        }
				    }
				    
					   File folder3 = new File("/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/DISNY/parsedFiles");
					    
					    // Get the list of files in the folder
					   File[] files3 = folder3.listFiles();
			    
					    // Loop through the files and delete each one
					    for (File file : files3) {
					        if (file.isFile()) {
					            file.delete();
					        }
				    }
					    
					    

				
			}
			
			
// IF YOU ENTER FEATURE NUMBER 2
			
			if(feature == 2)
				
			{
				System.exit(0);
	
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
					System.exit(0);
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
	
	private static boolean isValidDate(LocalDate date) {
	    int year = date.getYear();
	    int month = date.getMonthValue();
	    int day = date.getDayOfMonth();

	    // Check for leap year
	    if (month == 2) {
	        if (day > 28) {
	            // Leap year check
	            if (!((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))) {
	                System.out.println("\nInvalid date. February " + day + ", " + year + " is not a leap year.\n");
	                return false;
	            } else if (day > 29) {
	                System.out.println("\nInvalid date. February " + day + ", " + year + " has at most 29 days in a leap year.\n");
	                return false;
	            }
	        }
	    }

	    // Check for valid day values based on the month
	    if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
	        System.out.println("\nInvalid date. Month " + month + " has at most 30 days.\n");
	        return false;
	    } else if (day > 31) {
	        System.out.println("\nInvalid date. Month " + month + " has at most 31 days.\n");
	        return false;
	    }

	    return true;
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
