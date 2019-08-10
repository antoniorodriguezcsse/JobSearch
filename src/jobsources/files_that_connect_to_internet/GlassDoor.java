package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import jobsources.SortbyRank;
import jobsources.files_that_work_with_job_data.JobData;
import jobsources.files_that_work_with_job_data.ShowJobsCriteria;

import java.util.*;

public class GlassDoor {

    private final int millseconds = 500;
    //TODO: Some jobs are "not connected" check that out.
    // Clean this up, clean up GlassDoor, and test glassdoor
    // ArrayList<String> jobLocations = new ArrayList<>();
    private HashMap<ArrayList<String>, String> listOfJobsAndNextPage = new HashMap<>();
    private TreeSet<JobData> jobsThatPassSearchCriteria = new TreeSet<>(new SortbyRank());
    private TreeSet<String> noDuplicatesSortedSet = new TreeSet<String>();

    public TreeSet<JobData> getAllJobs(String numberOfJobsToFind, String mainLink) {
        setAllJobs(numberOfJobsToFind, mainLink);
        return jobsThatPassSearchCriteria;
    }

    private void setAllJobs(String numberOfJobsToFind, String mainLink) {
//        int jobCounter = 0;
        String nextMainSite = mainLink;
        ArrayList<String> jobLinks = new ArrayList<>();

        ShowJobsCriteria showJobsCriteria = new ShowJobsCriteria();
//        HashMap<String, Boolean> hasJobBeenSeen = new HashMap<>();
//        JobDataObjectsFile jobDataObjectsFile = new JobDataObjectsFile();

        do {
            jobLinks = getListOfJobLinks(nextMainSite);
            nextMainSite = getNextMainSite();
            boolean foundNumberOfJobs = false;

            for (String s : jobLinks) {
                JobData jobData = new JobData(s);
                System.out.println(s);

                sleep(millseconds);

                if (!noDuplicatesSortedSet.add(s)) {
                    System.out.println("***DUPLICATE FOUND***: " + jobData.getJobTitle());
                    continue;
                }

                if (showJobsCriteria.meetsCriteria(jobData)) {
                    System.out.println("JOB FOUND: " + jobData.getJobTitle());
                    System.out.println(s);
                    System.out.println(jobData.getJobTitle());

                    jobsThatPassSearchCriteria.add(jobData);
                    if (jobsThatPassSearchCriteria.size() == Integer.parseInt(numberOfJobsToFind)) {
                        foundNumberOfJobs = true;
                        break;
                    }

                } else {
                    if (jobData.getRejectedString().isEmpty()) {
                        System.out.println(showJobsCriteria.getRejected());
                    } else {
                        System.out.println(jobData.getRejectedString());
                    }
                }
            }

            if (foundNumberOfJobs) {
                break;
            }
        } while (!nextMainSite.equals("no more pages"));
    }

    private void sleep(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getListOfJobLinks(String mainSite) {
        GlassdoorMainSiteJobLinkExtractor glassdoorMainSiteJobLinkExtractor = new GlassdoorMainSiteJobLinkExtractor();

        try {
            listOfJobsAndNextPage = glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(mainSite);
        } catch (CustomExceptions customExceptions) {
            customExceptions.printStackTrace();
        }
        return listOfJobsAndNextPage.keySet().iterator().next();
    }

    private String getNextMainSite() {
        return listOfJobsAndNextPage.get(listOfJobsAndNextPage.keySet().iterator().next());
    }

    public int getNumberOfJobsFound() {
        return jobsThatPassSearchCriteria.size();
    }
}
