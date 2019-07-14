package jobsources.files_that_work_with_job_data;

import jobsources.SortbyRank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowJobsCriteria implements SearchCriteria {
    List<JobData> showJobs = new ArrayList<>();

    @Override
    public List<JobData> meetsCriteria(List<JobData> jobs) {
        for (JobData jd : jobs) {
            if (jobMeetsCriteria(jd)) {
                showJobs.add(jd);
            }
        }
        sortJobs();
        return showJobs;
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

    private void sortJobs()
    {
        Collections.sort(showJobs, new SortbyRank());
    }
}
