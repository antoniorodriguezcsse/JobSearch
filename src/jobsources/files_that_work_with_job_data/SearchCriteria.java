package jobsources.files_that_work_with_job_data;

import java.util.List;

public interface SearchCriteria {
    boolean meetsCriteria(JobData jobs);
}
