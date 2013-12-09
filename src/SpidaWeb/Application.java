package SpidaWeb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author James M Brewer	2013.12.09
 * 
 * This class is used to create an application object and to post it
 * to "https://www.spidasoftware.com/apply/applications".
 */
public class Application {
	private URL url;
	private String applicantsName;
	private String jobId;
	private String justification;
	private String sourceCode;
	
	/**
	 * 
	 * @param path the URL to the applications section: https://www.spidasoftware.com/apply/applications
	 */
	public Application(String path) {
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
		
		if(applicantsName == "") {
			System.out.println("The applicants name must be provided before the application can be committed.");
		} else if(jobId == "") {
			System.out.println("The job id must be provided before the application can be committed.");
		} else if(justification == "") {
			System.out.println("The justification must be provided before the application can be committed.");
		} else if(sourceCode == "") {
			System.out.println("The source code URL must be provided before the application can be committed.");
		} else {
			validData = true;
		}

		
		return validData;
	}
	
	/**
	 * This function combines the class data into a valid json object and submits it
	 * to the applications url.
	 */
	public void postApplication() {
		if(checkData()) {
			try {
			
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json");
		 
				String jsonData = "{\"name\":{\"description\":\"" + applicantsName + "\",\"type\":\"string\"}" +
								",\"jobId\":{\"description\":\"" + jobId + "\",\"type\":\"string\"}"+
								",\"justification\":{\"description\":\"" + justification + "\",\"type\":\"string\"},"+
								"\"code\":{\"description\":\"" + sourceCode + "\",\"type\":\"string\"}";
		 
				System.out.println(jsonData.toString());
				
				OutputStream os = connection.getOutputStream();
				os.write(jsonData.getBytes());
				os.flush();
	
				connection.disconnect();
			} catch (IOException e) {
				System.out.println("An IOException occurred: " + e);
			}
		}
	}
	
	/**
	 * The Get Applications function is added for completness.  The applications url does
	 * not appear to allow "GET" attemts so there is no way to verify if the application
	 * was submitted.
	 */
	public void getApplications() {
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
	 
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
	 
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
	 
			connection.disconnect();
	 
		  } catch (MalformedURLException e) {
			  System.out.println("A MalformedURLException occurred: " + e);
		  } catch (IOException e) {
			  System.out.println("A IOException occurred: " + e);
		  }
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
