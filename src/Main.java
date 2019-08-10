import jobsources.SortbyRank;
import jobsources.files_that_connect_to_internet.GlassDoor;
import jobsources.files_that_work_with_job_data.JobData;

import java.util.ArrayList;
import java.util.TreeSet;

class Main {

    public static void main(String[] args) {
        //This can be set to any number. Set numberOfSites to "All" to get all sites.
        // Can take a long time if you use "All", e.g. 900 job sites founds.
        String numberOfSites = "1000";
        String concordMainLink = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software%20engineer&locT=C&locId=1147340&locKeyword=Concord,%20CA&jobType=all&fromAge=7&minSalary=0&includeNoSalaryJobs=true&radius=0&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";
        String walnutCreekLink = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software%20engineer&locT=C&locId=1147417&locKeyword=Walnut%20Creek,%20CA&jobType=all&fromAge=7&minSalary=0&includeNoSalaryJobs=true&radius=0&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";
        String sanRamonMainLink = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software%20engineer&locT=C&locId=1147410&locKeyword=San%20Ramon,%20CA&jobType=all&fromAge=7&minSalary=0&includeNoSalaryJobs=true&radius=0&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";
        String pleasantonMainLink = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software%20engineer&locT=C&locId=1147390&locKeyword=Pleasanton,%20CA&jobType=all&fromAge=7&minSalary=0&includeNoSalaryJobs=true&radius=0&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";
        String sanFranciscoMainLink = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software%20engineer&locT=C&locId=1147401&locKeyword=San%20Francisco,%20CA&jobType=all&fromAge=7&minSalary=0&includeNoSalaryJobs=true&radius=0&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";

        ArrayList<String> citiesToSearchForJobs = new ArrayList<>();
        citiesToSearchForJobs.add(concordMainLink);
        citiesToSearchForJobs.add(walnutCreekLink);
        citiesToSearchForJobs.add(sanRamonMainLink);
        citiesToSearchForJobs.add(pleasantonMainLink);
        citiesToSearchForJobs.add(sanFranciscoMainLink);

        GlassDoor glassDoor = new GlassDoor();
        TreeSet<JobData> allJobs = new TreeSet<>(new SortbyRank());

        for (String city : citiesToSearchForJobs) {
            allJobs.addAll(glassDoor.getAllJobs(numberOfSites, city));
        }

        //  allJobs.sort(new SortbyRank());
        //  TreeSet<JobData> noDuplicateJobs = new TreeSet<>(allJobs);
        System.out.println("number of jobs found: " + allJobs.size());

        for (JobData jobData : allJobs) {
            System.out.println("Link: " + jobData.getJobLink());
            System.out.println("Rank: " + jobData.getRank());
            System.out.println("Title: " + jobData.getJobTitle());
            System.out.println("Date Posted; " + jobData.getDatePosted());

            System.out.println("");
        }
    }
}
