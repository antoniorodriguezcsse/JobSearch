package jobsources.files_that_connect_to_internet;

public class GlassDoor {
//
//    private final int millseconds = 500;
//    private HashMap<ArrayList<String>, String> listOfJobsAndNextPage = new HashMap<>();
//    private TreeSet<JobData> jobsThatPassSearchCriteria = new TreeSet<>(new SortbyJobID());
//    private TreeSet<String> noDuplicatesSortedSet = new TreeSet<String>();
//    private TreeSet<JobData> jobDataTreeSet = new TreeSet<>(new SortbyJobID());
//    private TreeSet<JobData> newJobDataTreeSet = new TreeSet<>(new SortbyJobID());
//    private ShowJobsCriteria showJobsCriteria = new ShowJobsCriteria();
//    private boolean foundNumberOfJobs = false;
//
//
//    public TreeSet<JobData> getAllJobs(int numberOfJobsToFind, String mainLink) {
//        ReadObjectsFromFile readObjectsFromFile = new ReadObjectsFromFile();
//        jobDataTreeSet = readObjectsFromFile.readObjects();
//        setAllJobs(numberOfJobsToFind, mainLink);
//        return readObjectsFromFile.readObjects();
//    }
//
//    private void setAllJobs(int numberOfJobsToFind, String mainLink) {
//        String nextMainSite = mainLink;
//        ArrayList<String> jobLinks = new ArrayList<>();
//
//        JobDuplicateChecker jobDuplicateChecker = new JobDuplicateChecker();
//        TreeSet<JobData> jobID = new TreeSet<>(new SortbyJobID());
//        int numberOfLinks = 0;
//
//        do {
//            jobLinks = getListOfJobLinks(nextMainSite);
//            nextMainSite = getNextMainSite();
//
//            for (String s : jobLinks) {
//                System.out.println("site: " + s);
//
//                if (jobDuplicateChecker.isDuplicate(s)) {
//                    System.out.println("***DUPLICATE FOUND***");
//                    continue;
//                }
//
//                JobData jobData = new JobData(s);
//                newJobDataTreeSet.add(jobData);
//
//                sleep();
//
//                if (jobData.getMeetsCriteria()) {
//                    System.out.println("JOB FOUND: " + jobData.getJobTitle());
//                    System.out.println(jobData.getJobLink());
//                    System.out.println(jobData.getJobTitle());
//                    jobsThatPassSearchCriteria.add(jobData);
//                }else {
//                    if (jobData.getRejectedString().isEmpty()) {
//                        System.out.println(showJobsCriteria.getRejected());
//                    } else {
//                        System.out.println(jobData.getRejectedString());
//                    }
//                }
//
//                if (jobsThatPassSearchCriteria.size() == numberOfJobsToFind) {
//                    foundNumberOfJobs = true;
//                    break;
//                }
//            }
//
//            if (foundNumberOfJobs) {
//                break;
//            }
//        } while (!nextMainSite.equals("no more pages"));
//
//        WriteObjectsToFile writeObjectsToFile = new WriteObjectsToFile();
//        jobDataTreeSet.addAll(newJobDataTreeSet);
//        writeObjectsToFile.writeObjects(jobDataTreeSet);
//    }
//
//    private void sleep() {
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private ArrayList<String> getListOfJobLinks(String mainSite) {
//        GlassdoorMainSiteJobLinkExtractor glassdoorMainSiteJobLinkExtractor = new GlassdoorMainSiteJobLinkExtractor();
//
//        try {
//            listOfJobsAndNextPage = glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(mainSite);
//        } catch (CustomExceptions customExceptions) {
//            customExceptions.printStackTrace();
//        }
//        return listOfJobsAndNextPage.keySet().iterator().next();
//    }
//
//    private String getNextMainSite() {
//        return listOfJobsAndNextPage.get(listOfJobsAndNextPage.keySet().iterator().next());
//    }
//
//    public int getNumberOfJobsFound() {
//        return jobsThatPassSearchCriteria.size();
//    }

}
