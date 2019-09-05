package jobsources.files_that_work_with_job_data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ShowJobsCriteria implements Serializable {
    private String rejected = "";

    public boolean meetsCriteria(JobData jd) {
        if (jd.dontShowJob()) {
            return false;
        }

        if (jd.isApplied()) {
            return false;
        }

        int minimumRank = -10;
        if (jd.getRank() < minimumRank) {
            rejected = "REJECTED: Job rank is less than " + jd.getRank();
            return false;
        }

        List<String> jobLocations = Arrays.asList("San Francisco, CA", "Concord, CA", "Walnut Creek, CA", "San Ramon, CA", "Pleasanton, CA");

        boolean locationFound = false;
        for (String s : jobLocations) {
            if (jd.getJobTitle().contains(s)) {
                locationFound = true;
                break;
            }
        }

        if (!locationFound) {
            rejected = "REJECTED: due to location.";
            return false;
        }

        int daysSincePost = 7;
        if (jd.getNumberOfDaysPosted() <= daysSincePost) {
            rejected = "REJECTED: Post is older than " + daysSincePost + " days.";
            return true;
        } else {
            return false;
        }

    }

    public String getRejected() {
        return rejected;
    }
}
