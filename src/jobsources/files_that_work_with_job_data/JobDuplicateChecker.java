//package jobsources.files_that_work_with_job_data;
//
//import jobsources.StringTools;
//import jobsources.read_write_to_files.ReadObjectsFromFile;
//
//import java.util.TreeSet;
//
//public class JobDuplicateChecker {
//
//    private TreeSet<String> jobIDsTreeSet = new TreeSet<>();
//
//    public JobDuplicateChecker() {
//       setUpJobIDs("jobObjects.bin");
//    }
//
//    public JobDuplicateChecker(String fileName) {
//        setUpJobIDs(fileName);
//    }
//
//    private void setUpJobIDs(String fileName) {
//        ReadObjectsFromFile readObjectsFromFile = new ReadObjectsFromFile();
//        TreeSet<GlassdoorJobData> readObjectsTreeSet = readObjectsFromFile.readObjects(fileName);
//        for (GlassdoorJobData jd : readObjectsTreeSet) {
//            jobIDsTreeSet.add(jd.getJobID());
//        }
//    }
//
//    public boolean isDuplicate(String link) {
//        String jobID = extractJobListingID(link);
//        return !jobIDsTreeSet.add(jobID);
//    }
//
//    private String extractJobListingID(String link) {
//        StringTools stringTools = new StringTools();
//        return stringTools.removeEverythingBeforeAndIncludingTerm(link, "jobListingId=");
//    }
//}
