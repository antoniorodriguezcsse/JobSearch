package jobsources.files_that_work_with_job_data;

import jobsources.CustomExceptions;
import jobsources.read_write_to_files.FileRead;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndeedJobDataTest {
    //  private String glassdoorPath = "TestingGlassdoor/";
    private String indeedPath = "TestingIndeed/";
    private FileRead fileRead = new FileRead();

    @Test
    void invalidSite() throws CustomExceptions {
        JobData jobData = new JobData(indeedPath + "indeed_jobSiteData_InvalidSite(404).htm");
        assertEquals("Can't find page. no data.", jobData.getLinesWithGoodKeywords().get(0));
    }

    @Test
    void getGoodKeywordsDoesntGetJavascriptAsJava() throws CustomExceptions {
        ArrayList<String> goodWordsList = new ArrayList<String>();

        JobData jobData = new JobData(indeedPath + "indeed_jobData_Javascript_GoodWordsTest.htm");
        ArrayList<String> goodLines = jobData.getLinesWithGoodKeywords();
        assert (goodLines.isEmpty());
    }

    @Test
    void getLinesWithWithGoodWords() throws CustomExceptions {
        ArrayList<String> goodWordsList = new ArrayList<String>();

        JobData jobData = new JobData(indeedPath + "indeed_jobSiteData_goodWordTest.htm");
        ArrayList<String> goodLines = jobData.getLinesWithGoodKeywords();
        goodWordsList.add("Tech Soft 3Dof Berkeley, CAis looking to hire a full-time C++ Software Engineer.");
        goodWordsList.add("Design and develop production-grade 3D graphics software for desktop and mobile platforms in C++, C#, and C.");
        goodWordsList.add("Qualifications for the C++ Engineer");
        goodWordsList.add("Excellent skill writing and debugging C++ code.");
        goodWordsList.add("Experience with code development and debugging in a wide variety of platforms, including Linux, macOS, iOS, Android, and Windows.");
        assertEquals(goodWordsList, jobData.getLinesWithGoodKeywords());
    }

    @Test
    void getLinesFromJobDescription() throws CustomExceptions {
        JobData jobData = new JobData(indeedPath + "indeed_jobSiteData_getHTMLTitle.htm");
        ArrayList<String> linesFromJob = jobData.getJobDescriptionText();
        FileRead file = new FileRead();
        assertEquals(file.getLinesFromFile(indeedPath + "getJobDescriptionTest.txt"), linesFromJob);

    }

    @Test
    void getLinesFromJObDescription_InvalidSite() throws CustomExceptions {
        JobData jobData = new JobData(indeedPath + "indeed_jobSiteData_InvalidSite(404).htm");
        assertEquals("JobSiteData: Page can't be found: No job description.", jobData.getJobDescriptionText().get(0));
    }

    @Test
    void getLinesWithGoodKeywords_Indeed_cpp() throws CustomExceptions {
        JobData jobData = new JobData(indeedPath + "indeed_jobData_Cpp_LinesOfGoodWordsTest.htm");
        assertEquals("We invite you to join to very experienced, highly motivated, and successful Engineering team as a C++ developer.", jobData.getLinesWithGoodKeywords().get(0));
    }

    @Test
    void getDatePosted_ItReturnsTheProperDate_StringOfDate() throws CustomExceptions {
        InterfaceDateTools dateTools = new DateTools();

        JobData getDate = new JobData(indeedPath + "indeed_jobSiteData_getDate.htm");
        String date = dateTools.getDateMinusDays("30","MM-dd-yyyy");
        assertEquals(date, getDate.getDatePosted());

        JobData oneDayAgo = new JobData(indeedPath + "indeed_jobSiteData_getDate1DayAgo.htm");
        date = dateTools.getDateMinusDays("1","MM-dd-yyyy");
        assertEquals(date, oneDayAgo.getDatePosted());

        JobData twoDayAgo = new JobData(indeedPath + "indeed_jobSiteData_getDate2DaysAgo.htm");
        date = dateTools.getDateMinusDays("2","MM-dd-yyyy");
        assertEquals(date, twoDayAgo.getDatePosted());

        JobData fourHoursAgo = new JobData(indeedPath + "indeed_jobSiteData_getDate4HoursAgo.htm");
        date = dateTools.getDateMinusDays("0","MM-dd-yyyy");
        assertEquals(date, fourHoursAgo.getDatePosted());

        JobData today= new JobData(indeedPath + "indeed_jobSiteData_getDateToday.htm");
        date = dateTools.getDateMinusDays("0","MM-dd-yyyy");
        assertEquals(date, today.getDatePosted());
    }

    @Test
    void getDatePosted_InvalidSite() throws CustomExceptions {
        JobData jobData = new JobData(indeedPath + "indeed_jobSiteData_InvalidSite(404).htm");
        System.out.println(jobData.getDatePosted());
        assertEquals("JobSiteData: Page can't be found: no date posted.", jobData.getDatePosted());
    }

    @Test
    void getDatePosted_invalidSites_StringOfDate() throws CustomExceptions {
        JobData indeed = new JobData(indeedPath + "ate1DayAgo.htm");
        assertEquals("JobSiteData: Could not connect to site:: no date posted.", indeed.getDatePosted());
    }

    @Test
    void getRankTest() throws InterruptedException, CustomExceptions {
        //   JobData jobData1 = new JobData(glassdoorPath + "goodWordsTest01.htm");

//        String mainSite = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software+engineer+entry+level&locT=C&locId=1147340&srs=JOBS_HOME_RECENT_SEARCHES&radius=50";
//        //String newJobs = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=lead%20software%20engineer&locT=C&locId=1147439&locKeyword=Santa%20Clara,%20CA&jobType=all&fromAge=1&minSalary=0&includeNoSalaryJobs=true&radius=25&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";
//        // String jobSite = "https://www.glassdoor.com/job-listing/entry-level-software-engineer-i-relevante-JV_IC1147400_KO0,31_KE32,41.htm?jl=3146625416";
//        GlassdoorMainSiteJobLinkExtractor mainJobLinkExtractor = new GlassdoorMainSiteJobLinkExtractor();
//        mainJobLinkExtractor.connectToWebsite(mainSite);
//
//        ArrayList<String> jobLinks = mainJobLinkExtractor.getAllJobLinksFromSite();
//        for (String s : jobLinks) {
//            //  System.out.println(s);
//            JobData jobSiteParser = new JobData(s);
//            System.out.println(jobSiteParser.getJobLink());
//            System.out.println(jobSiteParser.getRank());
//            Thread.sleep(2000);
//        }
    }

    @Test
    void getJobLink() throws IOException, CustomExceptions {
        JobData jobData= new JobData(indeedPath + "indeed_jobSiteData_getDateToday.htm");
        assertEquals("TestingIndeed/indeed_jobSiteData_getDateToday.htm", jobData.getJobLink());
    }

    @Test
    void getJobLink_invalidSite() throws CustomExceptions {
        JobData indeed = new JobData(indeedPath + "ate1DayAgo.htm");
        assertEquals("JobLink: Not connected to site.", indeed.getJobLink());
    }

    @Test
    void getTitle() throws IOException, CustomExceptions {
        JobData jobData = new JobData(indeedPath + "indeed_jobSiteData_getHTMLTitle.htm");
        assertEquals("Embedded Software Engineer SmartBins San Francisco, CA", jobData.getJobTitle());
    }

    @Test
    void getTitle_invalidSite() throws CustomExceptions {
        JobData indeed = new JobData(indeedPath + "ate1DayAgo.htm");
        assertEquals("JobSiteData: Could not connect to site:: No job title.", indeed.getJobTitle());
    }

    @Test
    void getApplyType() throws IOException, CustomExceptions {
        //Easy apply
        JobData jobData = new JobData(indeedPath + "indeed_jobSiteData_EasyApply.htm");
        assertEquals("Easy Apply", jobData.getApplyType());

        //company site
        JobData jobData2 = new JobData(indeedPath + "indeed_jobSiteData_getHTMLTitle.htm");
        assertEquals("Apply On Company Site", jobData2.getApplyType());
//
        //invalid site
        JobData jobData3 = new JobData(indeedPath + "ate1DayAgo.htm");
        assertEquals("JobSiteData: Could not connect to site:: No apply type data.", jobData3.getApplyType());
    }
}