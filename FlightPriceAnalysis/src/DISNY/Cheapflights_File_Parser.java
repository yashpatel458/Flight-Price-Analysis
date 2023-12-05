package DISNY;

import java.io.File;

import org.jsoup.Jsoup;

public class Cheapflights_File_Parser {

    public Flight_Detail[] Cheapflights_File_Parser(String origin, String destination, int date_month_string, int date_year, int date_day_input, String folderPath) {
        String Cheapflights_data = "";
        String date_month = "" + date_month_string;
        if (date_month_string < 10) {
            date_month = "0" + date_month_string;
        }

        String date_day = null;

        if (date_day_input < 10) {
            date_day = "0" + date_day_input;
        } else {
            date_day = "" + date_day_input;
        }

        Flight_Detail[] bestCheapflightsFlights = new Flight_Detail[3];

        try {
            String path = folderPath + "//" + origin.toLowerCase() + "_to_" + destination.toLowerCase() + "_" + date_day + "_" + date_month + "_" + date_year + "_Kayak" + ".html";
            File input = new File(path.toLowerCase());
            org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");

            org.jsoup.select.Elements results = doc.select("div[class='nrc6-wrapper']");
            org.jsoup.select.Elements airlines = doc.select("div[class='J0g6-operator-text']");
            org.jsoup.select.Elements durations = doc.select("span[class='JWEO-stops-text']");
            org.jsoup.select.Elements prices = doc.select("div[class='f8F1-price-text']");

            for (int k = 0; k < 3; k++) {
                org.jsoup.select.Elements divisions = results.get(k).select("div");
                org.jsoup.select.Elements spans = divisions.select("span");

                int i = 0;
                if (spans.get(5).text().length() == 0) {
                    i = 1;
                }
                if (spans.get(6).text().length() == 0) {
                    i = 2;
                }
                if (spans.get(7).text().length() == 0) {
                    i = 3;
                }
                if (spans.get(0).text().length() != 0) {
                    i = -5;
                }

                String time_from = spans.get((i + 5)).text();
                String time_to = spans.get((i + 7)).text();
                String origin_short = spans.get((i + 9)).text();
                String destination_short = spans.get((i + 13)).text();
                String num_of_stops = spans.get((i + 10)).text();
                String airline_name = airlines.get(k).text();
                String duration_of_flight = durations.get(k).text();
                String[] price_object = prices.get(k).text().split(" ");
                String price = price_object[1].replace(",", "");

                Flight_Detail currentFlight = new Flight_Detail(time_from, time_to, origin_short, destination_short, duration_of_flight, num_of_stops, airline_name, "Cheapflights", Integer.parseInt(price));

                bestCheapflightsFlights[k] = currentFlight;
            }
        } catch (Exception e) {
            // Handle the exception
        	 System.out.println("Error parsing Cheapflights data: " + e.getMessage());
//        	 e.printStackTrace();
          	 System.out.println("\n-------------------------------------------------------");
            System.out.println("There must be no flights on Cheapflights.com for this Route or Date!");
        }

        return bestCheapflightsFlights;
    }
}
