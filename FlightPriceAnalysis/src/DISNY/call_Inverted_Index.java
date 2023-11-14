package DISNY;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class call_Inverted_Index {
	List<Page_Ranking> prObject = new ArrayList<>();
	public void Index_Inverter(String[] folderpaths, String pat) throws FileNotFoundException 
	    {
		List<String> documents = new ArrayList<String>();
		for(String folderpath : folderpaths)
		{
			File folder = new File(folderpath);

		    // List all the files in the folder
		    File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) 
		    {
		      if (listOfFiles[i].isFile()) 
		      {
		    	  // This will be the content for each file scanned
		    	  String content = "";
//		    	  System.out.println("File: " + listOfFiles[i].getName());;
		    	  		String txt = "";
		    	  		File file = new File(folderpath + "\\" + listOfFiles[i].getName());
		    	  		
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
		    	        	// Checking if next (another) token exists or not !
	    		    	    	String string_Token = SToken.nextToken();
	    		    	    	
	    		    	    	// The tken found however should match the Wording requirements..
	    		    	        if (Pattern.matches("[a-zA-Z0-9]+",string_Token)) 
	    		    	       	{
	    		    	        	// Appending the new string token
	    		    	        	content = content + string_Token + " ";
	    		             	}
		    	          }
	    		    	    documents.add(content);
	    		    	    scan.close();
		      }
		    }		    
		}
	    		    Inverted_Indexing index = new Inverted_Indexing(documents);

	    	System.out.println("");
	        System.out.println("Indexing for ' " + pat + " ': " + Arrays.toString(index.searchIndex(pat).toArray()));
	    	System.out.println("");
	    }
	}

