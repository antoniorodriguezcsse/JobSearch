import jobsources.files_that_connect_to_internet.GlassDoor;
import jobsources.files_that_work_with_job_data.JobData;

import java.util.ArrayList;

class Main {

    public static void main(String[] args) {
        //This can be set to any number. Set numberOfSites to "All" to get all sites.
        // Can take a long time if you use "All", e.g. 900 job sites founds.
        String numberOfSItes = "10";

        //This is the main job link the program is searching. You ccan change it.
        // go to glassdoor fill out some search criterias and push "search" then copy and paste the link
        String mainLink = "https://www.glassdoor.com/Job/jobs.htm?suggestCount=0&suggestChosen=false&clickSource=searchBtn&typedKeyword=software+engineer&sc.keyword=software+engineer&locT=C&locId=1147340&jobType=";

        GlassDoor glassDoor = new GlassDoor();
        ArrayList<JobData> allJobs = new ArrayList<JobData>();
        allJobs = glassDoor.getAllJobs(numberOfSItes, mainLink);

        ArrayList<String> jobDescription = new ArrayList<>();
        System.out.println("number of jobs found: " + allJobs.size());
        for (JobData jobData : allJobs) {
            System.out.println("Link: " + jobData.getJobLink());
            System.out.println("Title: " + jobData.getJobTitle());
            System.out.println("Date Posted; " + jobData.getDatePosted());

            jobDescription = jobData.getJobDescriptionText();
            System.out.print("Job Description: ");
            for (String s : jobDescription) {
                System.out.println(s);
            }

            System.out.println("");
        }
    }
}
