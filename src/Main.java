class Main {
   public static void main(String[] args) {
//        final String searchForJobs = "1";
//        final String viewSavedJobs = "2";
//        final String applyToJobs = "3";
//
//        ArrayList<JobData> allJobs = new ArrayList<>();
//        ArrayList<JobData> jobsToDeleteFromAllJobs = new ArrayList<>();
//        ArrayList<JobData> jobsToAddToAllJobs = new ArrayList<>();
//        Scanner keyboard = new Scanner(System.in);
//        String userInput = "";
//
//        do {
//            System.out.println("To search for jobs press 1");
//            System.out.println("To view saved jobs press 2");
//            System.out.println("Apply to saved jobs(Searched jobs are automatically saved): 3");
//            System.out.println("to quit press q");
//            System.out.print("Enter value: ");
//            userInput = keyboard.nextLine();
//
//            if (userInput.equals(searchForJobs)) {
//                allJobs = getJobsFromSearch();
//                System.out.println("number of jobs found: " + allJobs.size());
//                displayJobs(allJobs);
//
//            }
//
//            if (userInput.equals(viewSavedJobs)) {
//                allJobs = getJobsFromFile();
//                System.out.println("number of jobs found: " + allJobs.size());
//                displayJobs(allJobs);
//            }
//
//            if (userInput.equals(applyToJobs)) {
//                allJobs = getJobsFromFile();
//                ArrayList<JobData> copyAllJobs = new ArrayList<>(allJobs);
//
//                for (Iterator<JobData> iterator = allJobs.iterator(); iterator.hasNext(); )
//                    /* for (JobData jd : allJobs) */ {
//                    JobData jd = iterator.next();
//                    if (jd.getMeetsCriteria()) {
//                        System.out.println("Link: " + jd.getJobLink());
//                        System.out.println("Rank: " + jd.getRank());
//                        System.out.println("Title: " + jd.getJobTitle());
//                        System.out.println("Applied: " + jd.isApplied());
//                        System.out.println("Date Posted: " + jd.getDatePosted());
//                        System.out.println("=====================================================================");
//                        for (String s : jd.getLinesWithGoodKeywords()) {
//                            System.out.println(s);
//                        }
//                        System.out.println("");
//                        System.out.println("Press 'a' to set as applied, 'v' to view applied jobs, 'd' to never show again, 'n' to see next job, or 'q' to quit");
//                        System.out.print("Enter input: ");
//                        userInput = keyboard.nextLine();
//
//                        if (userInput.equals("n")) {
//                            continue;
//                        }
//                        if (userInput.equals("a")) {
//                            jd.setApplied(true);
//                        }
//
//                        if (userInput.equals("d")) {
//                            jd.setDontShowJob(true);
//                        }
//                        if (userInput.equals("v")) {
//                            for (JobData job : allJobs) {
//                                if (job.isApplied()) {
//                                    System.out.println(job.getJobTitle());
//                                }
//                            }
//                        }
//
//                        if (userInput.equals("q")) {
//                            WriteObjectsToFile writeObjectsToFile = new WriteObjectsToFile();
//                            TreeSet<JobData> treeSetJobData = new TreeSet<>(new SortbyJobID());
//                            treeSetJobData.addAll(allJobs);
//                            writeObjectsToFile.writeObjects(treeSetJobData);
//                            break;
//                        }
//                    }
//                }
//
//                System.out.println("number of jobs found: " + allJobs.size());
//
//            }
//
//            System.out.println("");
//        } while (!userInput.equals("q"));
//
//    }
//
//    private static ArrayList<JobData> getJobsFromFile() {
//        ReadObjectsFromFile readObjectsFromFile = new ReadObjectsFromFile();
//        ArrayList<JobData> allJobs = new ArrayList<>(readObjectsFromFile.readObjects());
//        allJobs.sort(new SortbyRank());
//        return allJobs;
//    }
//
//    private static void displayJobs(ArrayList<JobData> allJobs) {
//        for (JobData jobData : allJobs) {
//            if (jobData.getMeetsCriteria()) {
//                System.out.println("Link: " + jobData.getJobLink());
//                System.out.println("Rank: " + jobData.getRank());
//                System.out.println("Title: " + jobData.getJobTitle());
//                System.out.println("Date Posted: " + jobData.getDatePosted());
//
//                System.out.println("");
//            }
//        }
//    }
//
//    private static ArrayList<JobData> getJobsFromSearch() {
//        //This can be set to any number. Set numberOfSites to "All" to get all sites.
//        // Can take a long time if you use "All", e.g. 900 job sites founds.
//        int numberOfSites = 1000;
//        String concordMainLink = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software%20engineer&locT=C&locId=1147340&locKeyword=Concord,%20CA&jobType=all&fromAge=7&minSalary=0&includeNoSalaryJobs=true&radius=0&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";
//        String walnutCreekLink = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software%20engineer&locT=C&locId=1147417&locKeyword=Walnut%20Creek,%20CA&jobType=all&fromAge=7&minSalary=0&includeNoSalaryJobs=true&radius=0&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";
//        String sanRamonMainLink = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software%20engineer&locT=C&locId=1147410&locKeyword=San%20Ramon,%20CA&jobType=all&fromAge=7&minSalary=0&includeNoSalaryJobs=true&radius=0&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";
//        String pleasantonMainLink = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software%20engineer&locT=C&locId=1147390&locKeyword=Pleasanton,%20CA&jobType=all&fromAge=7&minSalary=0&includeNoSalaryJobs=true&radius=0&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";
//        String sanFranciscoMainLink = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software%20engineer&locT=C&locId=1147401&locKeyword=San%20Francisco,%20CA&jobType=all&fromAge=7&minSalary=0&includeNoSalaryJobs=true&radius=0&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";
//
//        ArrayList<String> citiesToSearchForJobs = new ArrayList<>();
//        citiesToSearchForJobs.add(concordMainLink);
//        citiesToSearchForJobs.add(walnutCreekLink);
//        citiesToSearchForJobs.add(sanRamonMainLink);
//        citiesToSearchForJobs.add(pleasantonMainLink);
//        citiesToSearchForJobs.add(sanFranciscoMainLink);
//
//        GlassDoor glassDoor = new GlassDoor();
//        TreeSet<JobData> allJobsTreeSet = new TreeSet<>(new SortbyJobID());
//        for (String city : citiesToSearchForJobs) {
//            allJobsTreeSet.addAll(glassDoor.getAllJobs(numberOfSites, city));
//        }
//        //  allJobs.sort(new SortbyRank());
//        ArrayList<JobData> allJobs = new ArrayList<>(allJobsTreeSet);
//        allJobs.sort(new SortbyRank());
//        return allJobs;
    }
}
