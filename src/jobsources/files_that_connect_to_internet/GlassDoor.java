package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import jobsources.files_that_work_with_job_data.JobData;

import java.util.ArrayList;

public class GlassDoor {

    public ArrayList<JobData> getAllJobs(String numberOfSites, String mainLink) {
        GlassdoorMainSiteJobLinkExtractor glassdoorMainSiteJobLinkExtractor = new GlassdoorMainSiteJobLinkExtractor();
        ArrayList<String> jobLinks = new ArrayList<>();

        try {
            jobLinks = glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromAllMainSites(numberOfSites, mainLink);
        } catch (CustomExceptions customExceptions) {
            customExceptions.printStackTrace();
        }

        ArrayList<JobData> arrayOfJobData = new ArrayList<>();
        for (String s : jobLinks) {
            JobData jobData = new JobData(s);
            arrayOfJobData.add(jobData);
        }

        return arrayOfJobData;

    }
}
