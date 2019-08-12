package jobsources;

import jobsources.files_that_work_with_job_data.JobData;

import java.io.Serializable;
import java.util.Comparator;

public class SortbyJobID implements Comparator<JobData>, Serializable {
    @Override
    public int compare(JobData a, JobData b) {
        return b.getJobID().compareTo(a.getJobID());
    }


}
