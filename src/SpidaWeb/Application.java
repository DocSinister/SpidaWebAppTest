package SpidaWeb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;


/**
 * @author James M Brewer	2013.12.09
 * 
 * This class is used to create an application object and to post it
 * to "https://www.spidasoftware.com/apply/applications".
 */
public class Application {
	private String path;
	private URL url;
	private String applicantsName;
	private String jobId;
	private String justification;
	private String sourceCode;
	private String jsonData;
	private JsonObject applicationRecord;
	
	
	/**
	 * 
	 * @param path the URL to the applications section: https://www.spidasoftware.com/apply/applications
	 */
	public Application(String path) {
		this.path = path;
		
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			System.out.println("A MalformedURLException occurred: " + e);
		}
	}
	
	/**
	 * Validate all call variables for content to ensure nothing is excluded from the application
	 * process.
	 * 
	 * @return Returns a value of false if any fields have been missed. True if all fields have data.
	 */
	private Boolean checkData() {
		Boolean validData = false;
		
		if(applicantsName == null) {
			System.out.println("The applicants name must be provided before the application can be submitted.");
		} else if(jobId == null) {
			System.out.println("The job id must be provided before the application can be submitted.");
		} else if(justification == null) {
			System.out.println("The justification must be provided before the application can be submitted.");
		} else if(sourceCode == null) {
			System.out.println("The source code URL must be provided before the application can be submitted.");
		} else {
			validData = true;
		}

		
		return validData;
	}
	
	/**
	 * This function checks to see if we have all the necessary data, then
	 * generates the JSON for the application.
	 */
	public void generateApplication() {
		if(checkData()) {
			jsonData = "{\"name\":\"" + applicantsName + 
					   "\",\"jobId\":\"" + jobId + 
					   "\",\"justification\":\"" + justification + 
					   "\",\"code\":\"" + sourceCode + "\"}";
		}
	}
	
	/**
	 * This function combines the class data into a valid json object and submits it
	 * to the applications url.
	 */
	public void postApplication() {
		HttpURLConnection connection;
		InputStream input;
		OutputStream output;
		JsonReader reader;
		
		if(checkData()) {
			try {
				
				/*
				 * Check to make sure we have created the json to send to SpidaWeb
				 */
				if(jsonData == null) {
					generateApplication();
				}
				// Create the connection and header request.
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json");

				//Get the output stream and write the application.
				output = connection.getOutputStream();
				output.write(jsonData.getBytes());
				output.flush();
				
				//Check out response code to make sure the data was sent and received properly
				if (connection.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
				}
				
				//Get the input stream so that we can retrieve the applicatios "_id" from
				//the server response.  Store this in 
				input = connection.getInputStream();
				reader = Json.createReader(input);
				applicationRecord = reader.readObject();
				
				System.out.println("Your applicaiton has been posted.  Applicaiton Id: " + applicationRecord.getString("_id"));
				
				//Close our connection.
				connection.disconnect();
			} catch (IOException e) {
				System.out.println("An IOException occurred: " + e);
			}
		}
	}
	
	public JsonObject getApplication() {
		URL appUrl;
		JsonReader reader;
		InputStream streamIn;
		JsonObject appData = null;
		
		try {
			appUrl = new URL(path + "/" + applicationRecord.getString("_id"));
			
			streamIn = appUrl.openStream();
			reader = Json.createReader(streamIn);
			appData =  reader.readObject();
			streamIn.close();
			
		} catch (MalformedURLException e) {
			System.out.print ("An exception occurred." + e);
		} catch (IOException e) {
			System.out.print ("An IO exception occurred: " + e);
		}
		
		return appData;
	}
	
	public String getApplicantsName() {
		return applicantsName;
	}

	public void setApplicantsName(String applicantsName) {
		this.applicantsName = applicantsName;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
}
