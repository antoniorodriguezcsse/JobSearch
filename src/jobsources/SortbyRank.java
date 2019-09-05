//package jobsources;
//
//import jobsources.files_that_work_with_job_data.JobData;
//
//import java.io.Serializable;
//import java.util.Comparator;
//
//public class SortbyRank implements Comparator<JobData>, Serializable {
//    @Override
//    public int compare(JobData a, JobData b) {
//        int blah = b.getJobID().compareTo(a.getJobID());
//        return b.getRank() - a.getRank();
//    }
//
//    @Override
//    public Comparator<JobData> reversed() {
//        return null;
//    }
//}
//
////
