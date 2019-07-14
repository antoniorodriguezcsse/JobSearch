package jobsources.files_that_work_with_job_data;

import jobsources.SortbyRank;
import jobsources.files_that_connect_to_internet.GlassdoorMainSiteJobLinkExtractor;
import jobsources.gui.JComboBoxGUI;
import jobsources.gui.JListAllJobsGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class JListData {
    private String mainSite = "";
    ArrayList<String> allJobLinks;
    protected ArrayList<JobData> allJobData;
    private GlassdoorMainSiteJobLinkExtractor jobLinkExtractor;
    protected JListAllJobsGUI jListAllJobsGUI;
    private Integer jobFoundCounter;
    JComboBoxGUI jComboBoxNumberOfJobsToFind;
    private String searchTerm;

    public JListData(String searchTerm, JList<String> jlistOfJobs, JComboBoxGUI jComboBoxNumberOfJobsToFind) {
        allJobLinks = new ArrayList<>();
       // jobLinkExtractor = new GlassdoorMainSiteJobLinkExtractor();
        allJobData = new ArrayList<>();
        this.searchTerm = searchTerm;

        setMainLink(mainSite);
        jListAllJobsGUI = new JListAllJobsGUI(jlistOfJobs);
        setAllJobLinks();
        jListAllJobsGUI.clear();
        jListAllJobsGUI.addItem("Found a total of " + allJobLinks.size() + " jobs.");
      //  setJComboBox(jComboBoxNumberOfJobsToFind);
        this.jComboBoxNumberOfJobsToFind = jComboBoxNumberOfJobsToFind;
        setAllJobs();
    }

    private void setMainLink(String mainSite) {
        if (searchTerm.equals("Entry level software engineer")) {
            this.mainSite = "https://www.glassdoor.com/Job/concord-software-engineer-entry-level-jobs-SRCH_IL.0,7_IC1147340_KE8,37.htm?radius=50";
        }

        if (searchTerm.equals("Junior software engineer")) {
            this.mainSite = "https://www.glassdoor.com/Job/concord-junior-software-engineer-jobs-SRCH_IL.0,7_IC1147340_KO8,32.htm?radius=50";
        }
    }

    private void setAllJobLinks() {
//        do {
//    //        jobLinkExtractor.connectToWebsite(mainSite, jListAllJobsGUI);
//            for (String s : jobLinkExtractor.getAllJobLinksFromSite()) {
//                allJobLinks.add(s);
//            }
//
//            mainSite = jobLinkExtractor.getNextMainSite();
//            sleep();
//        } while (!mainSite.equals("no more pages"));
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setAllJobs() {
        JobData jobData;
        ArrayList<String> jobTitleList = new ArrayList<>();
        for (String s : allJobLinks) {
            jobData = new JobData(s);
            if (jobMeetsCriteria(jobData)) {

                System.out.println(jobData.getJobTitle());
                jListAllJobsGUI.addItem("Job Found, does meet criteria: " + jobData.getJobTitle());

                allJobData.add(jobData);

                System.out.println("all job data: " + allJobData.size());
                System.out.println("Number of jobs to find: " + jComboBoxNumberOfJobsToFind.getSelectedItem());
                if (allJobData.size() == Integer.parseInt(jComboBoxNumberOfJobsToFind.getSelectedItem())) {
                    break;
                }

                System.out.println("number of jobs added: " + allJobData.size());
            } else {
                jListAllJobsGUI.addItem("Job found, does not meet criteria: " + jobData.getJobTitle());
            }

            sleep();
        }
        Collections.sort(allJobData, new SortbyRank());

        for (JobData jd : allJobData) {
            jobTitleList.add(formatedJobForOutput(jd));
        }
      //  jListAllJobsGUI.clear();
        jListAllJobsGUI.addList(jobTitleList);
      //  System.out.println("Final job count: " + allJobData.size());
    }

    private Boolean jobMeetsCriteria(JobData jd) {
        if (jd.dontShowJob()) {
            return false;
        }

        if (jd.getRank() < 20) {
            return false;
        }

        return jd.getNumberOfDaysPosted() <= 14;
    }

    private String formatedJobForOutput(JobData jd) {
       // jListAllJobsGUI.addItem(String.valueOf(allJobData.size()));
        StringBuilder jobSummary = new StringBuilder("<html><b>Rank: " + jd.getRank() +
                "<br/>" + jd.getJobTitle() +
                "</b>" +
                "<br/>" + jd.getJobLink());

        for (String s : jd.getBulletPoints()) {
            jobSummary.append("<br/>")
                    .append(s);
        }
        jobSummary.append("<br/><br/>").append("</html>");
        return String.valueOf(jobSummary);
    }
}
