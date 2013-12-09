package SpidaWeb;

import javax.json.JsonObject;
import javax.json.JsonArray;

/**
 * @author James M Brewer	2013.12.05
 * 
 * @Description The purpose of this application is to demonstrate my understanding of java
 * and the SpiderWeb API. https://www.spidasoftware.com/apply
 */

public class SpidaWebApplication {
	private static String applyURL = "https://www.spidasoftware.com/apply";
	private static Jobs jobs;
	private static JsonArray jobList;
	private static Application apply;
	
	public static void main(String[] args) {
		jobs = new Jobs(applyURL + "/jobs");
		jobList = jobs.getJobs();
		
		for (int i = 0; i < jobList.size(); i++) {
			JsonObject item = jobList.getJsonObject(0);
			System.out.println("Position: " + item.getString("position") + ": ");
			System.out.println("Description: " + item.getString("description"));
			System.out.println("ID: " + item.getString("_id"));
			System.out.println("-----------");
		}
		
		apply = new Application(applyURL + "/application");
		apply.setApplicantsName("James (Mike) Brewer");
		apply.setJobId("00000000001");
		apply.setJustification("Because I am awesome and super cool");
		apply.setSourceCode("www.githug.com/sourcecode");
		apply.postApplication();
	}
}

