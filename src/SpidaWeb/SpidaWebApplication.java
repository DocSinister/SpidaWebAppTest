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
		
		/*
		 * Print out the list of jobs received from the jobs object
		 */
		for (int i = 0; i < jobList.size(); i++) {
			JsonObject item = jobList.getJsonObject(0);
			System.out.println("Position: " + item.getString("position") + ": ");
			System.out.println("Description: " + item.getString("description"));
			System.out.println("ID: " + item.getString("_id"));
			System.out.println("=======================================\n");
		}
		
		/*
		 * Generate the application and post it to SpideWeb.
		 */
		apply = new Application(applyURL + "/applications");
		
		apply.setApplicantsName("James (Mike) Brewer");
		apply.setJobId(jobs.getJob(0).get("_id").toString());
		apply.setJustification("Logic is the key to developing any sort of application, be it " + 
							   "a thin client for the web, or a fat client run from someones desk top.  " +
							   "I have several decades worth of experience is creating these types of " +
							   "applications and have a solid understanding of the logic required to " +
							   "program in just about any language out there.  Syntax is relevant " +
							   "only to a given language; Logic, on the other hand, is global.  I promise " +
							   "that if you give me a chance, you will not be disappointed!");
		apply.setSourceCode("https://github.com/DocSinister/SpidaWebAppTest/tree/master/src/SpidaWeb");
		
		apply.postApplication();
		
		JsonObject item = apply.getApplication();
		System.out.println("\n\nApplicaion Id: " + item.getString("_id"));
		System.out.println("Name: " + item.getString("name") + ": ");
		System.out.println("Job Id: " + item.getString("jobId"));
		System.out.println("Why Hire: " + item.getString("justification"));
		System.out.println("Source Code: " + item.getString("code"));
		System.out.println("=======================================");

	}
}

