package DISNY;

import java.io.*;
import java.util.*;

public class convertCodes {

  public static String gettingCodes(String cityName) {
    
    Map<String, String> dictCodes = new HashMap<>();
    String city;
    String code;

    try {
      BufferedReader cities = new  BufferedReader(new FileReader("/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/cities.txt"));
      BufferedReader codes = new  BufferedReader(new FileReader("/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/codes.txt"));

      while(( (city=cities.readLine())!=null)){
       
       code = codes.readLine();
      
        dictCodes.put(city.toLowerCase().trim(), code);
      }

      cities.close();
      codes.close();
      return dictCodes.get(cityName.toLowerCase().trim());
      //System.out.println(dictCodes.get(cityName.toLowerCase().trim()));

    } catch (Exception e) {
    	return null;
      //System.out.println("not found");
    }
}
}
