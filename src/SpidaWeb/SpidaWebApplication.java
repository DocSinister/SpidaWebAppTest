package SpidaWeb;

import javax.json.JsonObject;
import javax.json.JsonArray;

/**
 * @author James M Brewer	2013.12.05
 * 
 * The purpose of this application is to demonstrate my understanding of java
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
			System.out.println("=======================================");
		}
		
		apply = new Application(applyURL + "/applications");
		apply.setApplicantsName("James (Mike) Brewer");
		apply.setJobId("5258454d3c32a9e7b1000001");
		apply.setJustification("Logic is the key to developing any sort of application, be it " + 
							   "a thin client for the web, or a fat client run from someones desk top.  " +
							   "I have several decades worth of experience is creating these types of " +
							   "applications and have a solid understanding of the logic required to " +
							   "program in just about every language out there.  Syntax is relevant " +
							   "only to a given language; Logic, on the other hand, is global.");
		apply.setSourceCode("https://github.com/DocSinister/SpidaWebAppTest/tree/master/src/SpidaWeb");
		apply.postApplication();
		
		apply.getApplications();
	}
}

