package jobsources.files_that_work_with_job_data;

import jobsources.CustomExceptions;
import jobsources.read_write_to_files.FileRead;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlassdoorJobDataTest {
    private String glassdoorPath = "TestingGlassdoor/";
    private FileRead fileRead = new FileRead();

    @Test
    void testReflection() {
//        Class reflectClass = JobData.class;
//        Method[] classmMethods = reflectClass.getMethods();
//        for (Method method : classmMethods) {
//            System.out.println(method.getName());
//        }
    }

    @Test
    void getLinesWithGoodKeywords_Glassdoor_itDoesntConfuseJavaWithJavaScript() throws CustomExceptions {
        ArrayList<String> goodWordsList = new ArrayList<String>();
        JobData jobData = new JobData(glassdoorPath + "glassdoor_getLinesWithGoodKeywords_Javascript.htm");
        ArrayList<String> goodLines = jobData.getLinesWithGoodKeywords();
        assert (goodLines.isEmpty());
    }

    @Test
    void getJobDescriptionText_itReturnsAllLinesFromJobDescription_ArrayListOfAllText() throws CustomExceptions {
        ArrayList<String> goodWordsList = new ArrayList<String>();
        ArrayList<String> expected = new ArrayList<>();
        expected = fileRead.getLinesFromFile(glassdoorPath + "AllTextTest.txt");

        ArrayList<String> actual = new ArrayList<>();
        JobData jobData = new JobData(glassdoorPath + "glassdoorJobSite1.htm");
        actual.add("***Title***" + jobData.getJobTitle());
        actual.addAll(jobData.getJobDescriptionText());

        JobData jobData1 = new JobData(glassdoorPath + "glassdoorCompanySite.htm");
        actual.add("***Title***" + jobData1.getJobTitle());
        actual.addAll(jobData1.getJobDescriptionText());

        JobData jobData3 = new JobData(glassdoorPath + "glassdoorEasyApply.htm");
        actual.add("***Title***" + jobData3.getJobTitle());
        actual.addAll(jobData3.getJobDescriptionText());

        JobData jobData4 = new JobData(glassdoorPath + "glassdoorPartnerSite.htm");
        actual.add("***Title***" + jobData4.getJobTitle());
        actual.addAll(jobData4.getJobDescriptionText());

        JobData jobData5 = new JobData(glassdoorPath + "glassdoorGoDaddy.htm");
        actual.add("***Title***" + jobData5.getJobTitle());
        actual.addAll(jobData5.getJobDescriptionText());
        assertEquals(expected, actual);
    }

    @Test
    void getLinesWithGoodKeywords_itReadsJobDescriptions_ArrayListOfStringsWithGoodKeywords() throws CustomExceptions {
        ArrayList<String> goodWordsList = new ArrayList<String>();
        goodWordsList.add("As an engineering intern at Airbnb, youll be an integral part of our team where you will face our companys most ambitious goals.");
        goodWordsList.add("Experience using Java, Scala, Ruby and/or Ruby on Rails");
        goodWordsList.add("Experience using C++, and SQL for the backend (project dependent)");
        goodWordsList.add("Experience or relevant coursework in Java (Android) or iOS");
        goodWordsList.add("Experience or relevant coursework in Java (Android) or iOS");
        goodWordsList.add("Nice to Have: Experience in Objective C, Swift (iOS), and/or Kotlin (Android)");

        JobData jobData = new JobData(glassdoorPath + "glassdoorJobDataGoodWordTest1.htm");
        ArrayList<String> goodLines = jobData.getLinesWithGoodKeywords();
        assertEquals(goodWordsList, goodLines);
    }

    @Test
    void getLinesWithGoodKeywords_Indeed_cpp() throws CustomExceptions {
//        JobData jobData = new JobData(indeedPath + "indeed_jobData_Cpp_LinesOfGoodWordsTest.htm");
//        assertEquals("We invite you to join to very experienced, highly motivated, and successful Engineering team as a C++ developer.", jobData.getLinesWithGoodKeywords().get(0));
  }

    @Test
    void getDatePosted_ItReturnsTheProperDate_StringOfDate() throws CustomExceptions {
        JobData jobData = new JobData(glassdoorPath + "glassdoorJobDataGoodWordTest1.htm");
        assertEquals("08-31-2019", jobData.getDatePosted());

        JobData jobData1 = new JobData(glassdoorPath + "glassdoorCompanySite.htm");
        assertEquals("03-15-2019", jobData1.getDatePosted());

//        JobData indeed = new JobData(indeedPath + "indeed_jobSiteData_getDate1DayAgo.htm");
//        assertEquals("08-28-2019",indeed.getDatePosted());
    }

    @Test
    void getDatePosted_invalidSites_StringOfDate() throws CustomExceptions {
        JobData jobData = new JobData(glassdoorPath + "cantFindPage.htm");
        assertEquals("JobSiteData: Could not connect to site:: no date posted.", jobData.getDatePosted());

        JobData jobData1 = new JobData(glassdoorPath + "expiered.htm");
        assertEquals("JobSiteData: Could not connect to site:: no date posted.", jobData1.getDatePosted());
    }

    @Test
    void getDifferenceBetweenDays_itReturnsThedifferenceBetweenTwoDates_returnsNumberOfDays() throws IOException, InterruptedException, CustomExceptions {
        JobData jobData = new JobData(glassdoorPath + "glassdoorJobDataGoodWordTest1.htm");
        String initialDate = jobData.getDatePosted();
        System.out.println(initialDate);
        String differenceBetweenDays = jobData.getDifferenceBetweenDatesInDays("8-31-2019", initialDate, "MM-dd-yyyy");

        assertEquals("0", differenceBetweenDays);

        JobData jobData1 = new JobData(glassdoorPath + "glassdoorCompanySite.htm");
        initialDate = jobData1.getDatePosted();
        differenceBetweenDays = jobData1.getDifferenceBetweenDatesInDays("04-12-2019", initialDate, "MM-dd-yyyy");
        assertEquals("28", differenceBetweenDays);
    }

    @Test
    void getDifferenceBetweenDays_badDates_returns999999() throws CustomExceptions {
        String differenceBetweenDays;
        JobData jobData3 = new JobData(glassdoorPath + "cantFindPage.htm");

        differenceBetweenDays = jobData3.getDifferenceBetweenDatesInDays("2019-04-12", "asdf", "yyyy-MM-dd");
        assertEquals("invalid date.", differenceBetweenDays);

        JobData jobData4 = new JobData(glassdoorPath + "expiered.htm");
        differenceBetweenDays = jobData4.getDifferenceBetweenDatesInDays("asdfas", "2019-04-12", "yyyy-MM-dd");
        assertEquals("invalid date.", differenceBetweenDays);

    }

    @Test
    void getRankTest() throws InterruptedException, CustomExceptions {
        JobData jobData1 = new JobData(glassdoorPath + "goodWordsTest01.htm");

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
        String jobSite = glassdoorPath + "glassdoorJobDataGoodWordTest1.htm";
        JobData jobData = new JobData(jobSite);
        assertEquals("TestingGlassdoor/glassdoorJobDataGoodWordTest1.htm", jobData.getJobLink());
    }

    @Test
    void getTitle() throws IOException, CustomExceptions {
        JobData jobData = new JobData(glassdoorPath + "glassdoorCantFindPage.htm");
        assertEquals("JobTitle: Can't find page, no data.", jobData.getJobTitle());

        JobData jobData1 = new JobData(glassdoorPath + "glassdoorCompanySite.htm");
        assertEquals("Revature Entry Level C# Software Developer Job in Hayward, CA | Glassdoor", jobData1.getJobTitle());
    }

    @Test
    void getApplyType() throws IOException, CustomExceptions {
        //company site
        JobData jobData = new JobData(glassdoorPath + "glassdoorCompanySite.htm");
        assertEquals("Apply on Company Site", jobData.getApplyType());

        //Easy apply
        JobData jobData2 = new JobData(glassdoorPath + "glassdoorEasyApply.htm");
        assertEquals("Easy Apply", jobData2.getApplyType());

        //expired
        JobData jobData3 = new JobData(glassdoorPath + "glassdoorExpired.htm");
        assertEquals("JobSiteData: Job expiered: No apply type data.", jobData3.getApplyType());

        //partner site
        JobData jobData4 = new JobData(glassdoorPath + "glassdoorPartnerSite.htm");
        assertEquals("Apply on Partner Site", jobData4.getApplyType());
    }

    @Test
    void blah() throws CustomExceptions {


    }
}