package SpidaWeb;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;

/**
 * @author James M Brewer	2013.12.05
 * 
 * This class is used to create a JsonArray of the available jobs accessed
 * from "https://www.spidasoftware.com/apply/jobs".
 */

public class Jobs {
	private static JsonArray jsonData;
	
	/**
	 * @param path  The URL of the jobs listings: https://www.spidasoftware.com/apply/jobs
	 * 
	 * Class Constructor
	 */
	public Jobs(String path) {
		URL url;
		JsonReader reader;
		InputStream streamIn;
		
		try {
			url = new URL(path);
			
			streamIn = url.openStream();
			reader = Json.createReader(streamIn);
			jsonData =  reader.readArray();
			streamIn.close();
			
		} catch (MalformedURLException e) {
			System.out.print ("An exception occurred." + e);
		} catch (IOException e) {
			System.out.print ("An IO exception occurred: " + e);
			
		}
	}
	
	/**
	 * 
	 * @return a JsonArry object containing a list of the available jobs
	 */
	public JsonArray getJobs() {
		return jsonData;
	}
}
