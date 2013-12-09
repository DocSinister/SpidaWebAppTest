package SpidaWeb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;


public class Application {
	private URL url;
	private String applicantsName;
	private String jobId;
	private String justification;
	private String sourceCode;
	
	public Application(String path) {
		try {
				url = new URL(path);
		} catch (MalformedURLException e) {
			System.out.println("A MalformedURLException occurred: " + e);
		}
	}
	
	public int postApplication() {		
		try {
		
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
	 
			String jsonData = "{\"name\":" + applicantsName + 
					      ",\"jobId\":" + jobId +
					      ",\"justification\":" + justification +
					      ",\"code\":" + sourceCode + "}";
	 
			System.out.println(jsonData.toString());
			
			OutputStream os = conn.getOutputStream();
/*			os.write(jsonData.getBytes());
			os.flush();
	 
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	 
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
*/
			conn.disconnect();
		} catch (IOException e) {
			System.out.println("An IOException occurred: " + e);
		}
		
		return 0;
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
