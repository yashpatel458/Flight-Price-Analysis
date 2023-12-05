package DISNY;

import java.util.Arrays;
import java.util.Comparator;

public class Sort_Flights {

	public Flight_Detail[] sortFlights(Flight_Detail[] flights) {
		
		Arrays.sort(flights, new Comparator<Flight_Detail>(){
			
			@Override
			public int compare(Flight_Detail flight1, Flight_Detail flight2) {
			    if (flight1 == null || flight2 == null) {
			        return 0; // or handle the null case as needed
			    }
			    if (flight1.getPrice() == flight2.getPrice()) {
			        return 0;
			    } else if (flight1.getPrice() > flight2.getPrice()) {
			        return 1;
			    } else {
			        return -1;
			    }
			}
			
		});
		
		return flights;
	}
	
}
