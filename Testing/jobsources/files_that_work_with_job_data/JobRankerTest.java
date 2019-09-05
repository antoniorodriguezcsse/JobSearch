//package jobsources;
//
//import jobsources.files_that_work_with_job_data.GlassdoorJobData;
//import org.junit.jupiter.api.Test;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class JobRankerTest {
//    private String path = "D:/Java/JobSearch/websiteTest/";
//
//    @Test
//    public void EasyApplyPointsTest() throws CustomExceptions {
//        //    System.out.println(" jobRank = (goodWordTotalPoints * 25) + applyTypePoints - badWordTotalPoints - numberOfDaysPostedPoints");
//        String todaysDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
//
//        GlassdoorJobData glassdoorJobData = new GlassdoorJobData(path + "easyApply.htm");
//        String numberOfDaysPosted = glassdoorJobData.getDifferenceBetweenDates(glassdoorJobData.getDatePosted(),todaysDate,"yyyy-MM-dd");
//
//        int easyApplyTotalPoints = (2 * 25 + 50 - 2) + Integer.parseInt(numberOfDaysPosted);
//        assertEquals((Integer) easyApplyTotalPoints, glassdoorJobData.getRank());
//
//        GlassdoorJobData glassdoorJobData1 = new GlassdoorJobData(path + "companySite.htm");
//        String numberOfDaysPostedCompanySite = glassdoorJobData1.getDifferenceBetweenDates(glassdoorJobData1.getDatePosted(),todaysDate,"yyyy-MM-dd");
//
//        int companyApplyPoints = 2*25 - 2 + Integer.parseInt(numberOfDaysPostedCompanySite);// + Integer.valueOf(numberOfDaysPosted);
//        assertEquals((Integer) companyApplyPoints, glassdoorJobData1.getRank());
//    }
//
//    @Test
//    public void HashMap() {
////        String jobSite = "https://www.glassdoor.com/job-listing/senior-backend-software-developer-nyansa-JV_IC1147434_KO0,33_KE34,40.htm?jl=2596888844";
////        JobRanker jobRanker = new JobRanker(jobSite);
//    }
//}