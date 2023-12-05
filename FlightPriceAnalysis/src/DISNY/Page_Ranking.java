package DISNY;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Page_Ranking 
{
		private int intValue;
		 private String stringValue;
		 
		 public Page_Ranking(int intValue, String stringValue) 
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

//	Page_Rank_Object[] PRO = new Page_Rank_Object[600];
//	int k = 0;
	   
	   Map<Integer, String> Hash_Map = new HashMap<>();
       List<Page_Ranking> prObject = new ArrayList<>();
	public void Page_Ranker(String folderpaths, String pat) throws Exception 
	{
		
			File folder = new File(folderpaths);

		    // List all the files in the folder
		    File[] listOfFiles = folder.listFiles();
//	        System.out.print("Enter a keyword : ");
//	        Scanner sc = new Scanner(System.in);
//	        String pat = sc.next();
	        
	        

		    // Print the names of the files
		    for (int i = 0; i < listOfFiles.length; i++) 
		    {
		      if (listOfFiles[i].isFile()) 
		      {
//		    	  System.out.println("File: " + listOfFiles[i].getName());;
		    	  		String txt = "";
		    	  		File file = new File(folderpaths + "//" + listOfFiles[i].getName());
		    	  		
		    	  		//Scanner class to implement scanning
		    	  		Scanner scan = new Scanner(file);
		    	  		
		    	  		//Delimiter for Scan
		    	  		scan.useDelimiter("\\Z");
		    	  		
		    	  		//Scan the file_Content and making all the words to Lower Case
		    	  		String file_Content = scan.next().toLowerCase();
		    	  		
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
		    	          prObject.add(new Page_Ranking(freq, listOfFiles[i].getName()));
		    	          scan.close();
		      }
		    }		    
		
		Collections.sort(prObject, Comparator.comparingInt(Page_Ranking::getIntValue).reversed());

		System.out.println("");
		System.out.println("-----Page Ranking-----");
        for (Page_Ranking obj : prObject) {
            System.out.println(obj.getIntValue() + " " + obj.getStringValue());
        }
    	System.out.println("");

	}

}
