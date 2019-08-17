package jobsources.files_that_work_with_job_data;

import jobsources.CustomExceptions;
import org.junit.jupiter.api.Test;

class JobDuplicateCheckerTest {

    @Test
    void JobDuplicateTest() throws CustomExceptions {
//
//        File file = new File("objectTestFile.bin");
//        file.delete();
//
//        GlassdoorMainSiteJobLinkExtractor glassdoorMainSiteJobLinkExtractor = new GlassdoorMainSiteJobLinkExtractor();
//        TreeSet<String> allJobLinks;
//        allJobLinks = glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite("https://www.glassdoor.com/Job/walnut-creek-software-engineer-jobs-SRCH_IL.0,12_IC1147417_KO13,30.htm?radius=0&fromAge=7");
//
//        ArrayList<String> jobLinks = new ArrayList<>();
//        jobLinks = allJobLinks.keySet().iterator().next();
//
//        TreeSet<JobData> treeSetOfJobs = new TreeSet<>(new SortbyJobID());
//        for (String s : jobLinks) {
//            treeSetOfJobs.add(new JobData(s));
//        }
//
//        WriteObjectsToFile writeObjectsToFile = new WriteObjectsToFile();
//        writeObjectsToFile.writeObjects(treeSetOfJobs, "objectTestFile.bin");
//
//        HashMap<ArrayList<String>, String> allJobLinks2;
//        allJobLinks2 = glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite("https://www.glassdoor.com/Job/walnut-creek-software-engineer-jobs-SRCH_IL.0,12_IC1147417_KO13,30.htm?radius=0&fromAge=7");
//        jobLinks.addAll(allJobLinks2.keySet().iterator().next());
//
//        JobDuplicateChecker jobDuplicateChecker = new JobDuplicateChecker("objectTestFile.bin");
//        for (String s : jobLinks) {
//            assertTrue(jobDuplicateChecker.isDuplicate(s));
//        }
    }
}