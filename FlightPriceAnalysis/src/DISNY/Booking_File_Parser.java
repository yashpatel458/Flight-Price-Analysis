package DISNY;

import java.io.File;

import org.jsoup.Jsoup;

public class Booking_File_Parser {

    public Flight_Detail[] Booking_Parser(String origin, String destination, int date_month_string, int date_year, int date_day_input, String folderPath) {
        String booking_data = "";
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

        Flight_Detail[] bestBookingFlights = new Flight_Detail[3];

        try {
            String path = folderPath + "//" + origin.toLowerCase() + "_to_" + destination.toLowerCase() + "_" + date_day + "_" + date_month + "_" + date_year + "_Booking" + ".html";
            File input = new File(path.toLowerCase());
            org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");

            for (int i = 0; i < 3; i++) {
                org.jsoup.nodes.Element flightCard = doc.select("#flightcard-" + i).first();

                if (flightCard == null) {
                    // Handle the case where flightCard is null
                    System.out.println("Error: flightCard is null for index " + i);
                    continue; // Skip to the next iteration
                }

                org.jsoup.nodes.Element departureTimeElem = flightCard.selectFirst("[data-testid=flight_card_segment_departure_time_0]");
                String departureTime = departureTimeElem.text();

                org.jsoup.nodes.Element destinationTimeElement = flightCard.selectFirst("div[data-testid=flight_card_segment_destination_time_0]");
                String destinationTime = destinationTimeElement.text();

                org.jsoup.nodes.Element durationElement = flightCard.selectFirst("div[data-testid=flight_card_segment_duration_0]");
                String duration = durationElement.text();

                org.jsoup.nodes.Element element = flightCard.select("div[data-testid=flight_card_segment_stops_0]").first();
                String stops = element.text();

                org.jsoup.select.Elements originElement = flightCard.select("div[data-testid=flight_card_segment_departure_airport_0]");
                String originAirport = originElement.select("div[data-testid=flight_card_segment_departure_airport_0]").text();

                org.jsoup.nodes.Element destAirportElement = flightCard.selectFirst("[data-testid=flight_card_segment_destination_airport_0]");
                String destinationAirport = destAirportElement.text();

                org.jsoup.nodes.Element priceElement = flightCard.selectFirst(".FlightCardPrice-module__priceContainer___nXXv2");
                String[] price_object = priceElement.text().split(",");
                String price = price_object[0].replace("C$", "").replace(".", "");

                org.jsoup.select.Elements airlineNameDiv = flightCard.select("div[data-testid=flight_card_carrier_" + i + "]");
                String airlineName = airlineNameDiv.text();

                Flight_Detail currentFlight = new Flight_Detail(departureTime, destinationTime, originAirport, destinationAirport, duration, stops, airlineName, "Booking", Integer.parseInt(price));
                bestBookingFlights[i] = currentFlight;
            }
        } catch (Exception e) {
            // Handle the exception
        	System.out.println("Error parsing Booking data: " + e.getMessage());
         	 System.out.println("\n-------------------------------------------------------");
             System.out.println("There must be no flights on Booking.com for this Route or Date!");

        }

        return bestBookingFlights;
    }
}
