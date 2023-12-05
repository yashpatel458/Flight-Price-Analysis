package DISNY;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Frequency_Count {

	private int intValue;
	 private String stringValue;
	 
	 public Frequency_Count(int intValue, String stringValue) 
	 {
	        this.intValue = intValue;
	        this.stringValue = stringValue;
	 }
	 
	 public int getIntValue() 
	 {
	        return intValue;
   }

  public String getStringValue() 
  {
      return stringValue;
  }

//  Map<Integer, String> Hash_Map = new HashMap<>();
  List<Frequency_Count> freqObj = new ArrayList<>();
public void Frequency_Counter(String folderpaths, String pat) throws Exception 
{

	{
		File folder = new File(folderpaths);

	    File[] listOfFiles = folder.listFiles();
	    
	    for (int i = 0; i < listOfFiles.length; i++) 
	    {
	      if (listOfFiles[i].isFile()) 
	      {
	    	  		String txt = "";
	    	  		File file = new File(folderpaths + "/" + listOfFiles[i].getName());
	    	  		
	    	  		//Scanner class to implement scanning
	    	  		Scanner scan = new Scanner(file);
	    	  		
	    	  		//Delimiter for Scan
	    	  		scan.useDelimiter("\\Z");
	    	  		
	    	  		//Scan the file_Content and making all the words to Lower Case
	    	  		String file_Content = scan.nextLine().toLowerCase();
	    	  		
	    	  		//Initialize String Tokenizer
	    	  		StringTokenizer SToken = new StringTokenizer(file_Content);
	    	  		
	    	  		//The below loop adds all the words and their frequencies in the Hash Table
	    	          while (SToken.hasMoreTokens())
	    	          {
	    	          	String str = SToken.nextToken();
	    	          	if (Pattern.matches("[a-zA-Z0-9]+",str)) 
	    	          	{
	    	          		txt = txt + str + " ";
	    	          	}
	    	          }
	    	          int M = pat.length();
	    	          int N = txt.length();
	    	          int freq = 0;
	    	          int newIndex = 0;
	    	          BoyerMoore BM = new BoyerMoore(pat);
	    	          int offset = BM.search(txt, newIndex);
	    	          while( newIndex < (N-M) )
	    	          {
	    	      		
	    	          	BM = new BoyerMoore(pat);
	    	      		offset = BM.search(txt, newIndex);
	    	      		newIndex = 1 + offset;
	    	      		if(offset != N)
	    	      		{
	    	      			freq += 1;
	    	      		}
	    	          }
	    	          freqObj.add(new Frequency_Count(freq, listOfFiles[i].getName()));
	    	          
	    	          scan.close();
	      }
	    }		    
	
	System.out.println("");
	for (Frequency_Count obj : freqObj) {
        System.out.println(obj.getIntValue() + " " + obj.getStringValue());
    }
	System.out.println("");
}
}
}