package jobsources;

import jobsources.files_that_work_with_job_data.GlassdoorJobData;

import java.io.Serializable;
import java.util.Comparator;

public class SortbyJobID implements Comparator<GlassdoorJobData>, Serializable {
    @Override
    public int compare(GlassdoorJobData a, GlassdoorJobData b) {
        return b.getJobID().compareTo(a.getJobID());
    }


}
