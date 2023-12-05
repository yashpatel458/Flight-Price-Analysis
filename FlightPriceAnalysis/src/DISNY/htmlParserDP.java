
package DISNY;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.nodes.Document;



public class htmlParserDP {

    public static void main(String args[]) throws IOException {
       // URL_parsing();

    }

public static void htmlParsing(String folderPath) {
		
		String dir = folderPath;


		File folder = new File(dir);

		for(File f : folder.listFiles()) {

			if (f.isFile() && f.getName().endsWith(".html")){
				
				File input = new File(f.getAbsolutePath());

				try {
					
					Document connectToURL = Jsoup.parse(input , "UTF-8", "http://example.com/");
					
					String varName = "/Users/yashpatel/git/Flight-Price-Analysis/FlightPriceAnalysis/src/DISNY/parsedFiles/"+f.getName()+".txt";
					
					File file = new File(varName); 
					file.createNewFile();
					
					FileWriter writer = new FileWriter(varName);//boolean flag = true;
			        
					int i=0;	

			        while(!connectToURL.getElementsByIndexEquals(i).isEmpty()){
			            if(!connectToURL.getElementsByIndexEquals(i).text().isEmpty()) {
			                writer.write(connectToURL.getElementsByIndexEquals(i).text());
			                writer.write("\n");
						            	//System.out.println(connectToURL.getElementsByIndexEquals(i).text());
			            }
			            i++;
			        }

			        writer.close();
			        			        					
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}

			}
		}
	}

}
