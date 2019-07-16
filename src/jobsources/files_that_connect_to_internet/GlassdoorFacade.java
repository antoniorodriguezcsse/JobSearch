package jobsources.files_that_connect_to_internet;

import java.util.ArrayList;

public class GlassdoorFacade {
    private String mainSite;
    private ArrayList<String> allJobLinks = new ArrayList();

    private GlassdoorMainSiteJobLinkExtractor glassdoorMainSiteJobLinkExtractor;

    public GlassdoorFacade(String mainSite) {
        this.mainSite = mainSite;

        glassdoorMainSiteJobLinkExtractor = new GlassdoorMainSiteJobLinkExtractor();
      //  glassdoorMainSiteJobLinkExtractor.connectToMainWebSite(mainSite);
     //   glassdoorMainSiteJobLinkExtractor.validateJobContainer();

       // allJobLinks = glassdoorMainSiteJobLinkExtractor.getJobLinksFromAllMainSites("All",mainSite);
        getAllJobLinks();

    }

    private void getAllJobLinks() {
//        System.out.println(mainSite);
//
//        //TODO: Return number of jobs. e.g. number of sites = 2, return 2 jobs
//        ArrayList<JobData> allJobsData = new ArrayList<>();
//
//        for (String s : allJobLinks) {
//            JobData jobData = new JobData(s);
//            System.out.println(jobData.getJobTitle());
//        }
    }
}
