package jobsources;

import jobsources.files_that_connect_to_internet.JobSiteData;
import jobsources.files_that_work_with_job_data.JobData;
import jobsources.files_that_work_with_job_data.JobRanker;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JobRankerTest {
    private String path = "D:/Java/JobSearch/websiteTest/";

    @Test
    public void EasyApplyPointsTest() {
        //    System.out.println(" jobRank = (goodWordTotalPoints * 25) + applyTypePoints - badWordTotalPoints - numberOfDaysPostedPoints");
        String todaysDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        JobData jobData = new JobData(path + "easyApply.htm");
        String numberOfDaysPosted = jobData.getDifferenceBetweenDates(jobData.getDatePosted(),todaysDate,"yyyy-MM-dd");

        int easyApplyTotalPoints = (2 * 25 + 50 - 2) + Integer.valueOf(numberOfDaysPosted);
        assertEquals((Integer) easyApplyTotalPoints, jobData.getRank());

        JobData jobData1 = new JobData(path + "companySite.htm");
        String numberOfDaysPostedCompanySite = jobData1.getDifferenceBetweenDates(jobData1.getDatePosted(),todaysDate,"yyyy-MM-dd");

        int companyApplyPoints = 2*25 - 2 + Integer.valueOf(numberOfDaysPostedCompanySite);// + Integer.valueOf(numberOfDaysPosted);
        assertEquals((Integer) companyApplyPoints, jobData1.getRank());
    }

    @Test
    public void HashMap() {
//        String jobSite = "https://www.glassdoor.com/job-listing/senior-backend-software-developer-nyansa-JV_IC1147434_KO0,33_KE34,40.htm?jl=2596888844";
//        JobRanker jobRanker = new JobRanker(jobSite);
    }
}