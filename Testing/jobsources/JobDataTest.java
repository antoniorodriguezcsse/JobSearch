package jobsources;

import jobsources.files_that_work_with_job_data.JobData;
import jobsources.read_write_to_files.FileRead;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import static org.junit.jupiter.api.Assertions.assertEquals;

class JobDataTest {
    private String path = "D:/Java/JobSearch/websiteTest/";
    FileRead fileRead = new FileRead();

    @Test
    void testReflection() {
//        Class reflectClass = JobData.class;
//        Method[] classmMethods = reflectClass.getMethods();
//        for (Method method : classmMethods) {
//            System.out.println(method.getName());
//        }
    }
    @Test
    void getLinesWithGoodKeywords_itDoesntConfuseJavaWithJavaScript_ArrayListOfGoodWordsIsEmpty() {
        ArrayList<String> goodWordsList = new ArrayList<String>();

        JobData jobData = new JobData(path + "goodWordsTest02-Javascript.htm");
        ArrayList<String> goodLines = jobData.getLinesWithGoodKeywords();
        assert (goodLines.isEmpty());
    }

    @Test
    void getJobDescriptionText_itReturnsAllLinesFromJobDescription_ArrayListOfAllText() {
        ArrayList<String> goodWordsList = new ArrayList<String>();
        ArrayList<String> expected = new ArrayList<>();
        expected = fileRead.getLinesFromFile(path + "AllTextTest.txt");

        ArrayList<String> actual = new ArrayList<>();
        JobData jobData = new JobData(path + "jobsite1.htm");
        actual.add("***Title***" + jobData.getJobTitle());
        actual.addAll(jobData.getJobDescriptionText());

        JobData jobData1 = new JobData(path + "companySite.htm");
        actual.add("***Title***" + jobData1.getJobTitle());
        actual.addAll(jobData1.getJobDescriptionText());

        JobData jobData3 = new JobData(path + "easyApply.htm");
        actual.add("***Title***" + jobData3.getJobTitle());
        actual.addAll(jobData3.getJobDescriptionText());

        JobData jobData4 = new JobData(path + "partnerSite.htm");
        actual.add("***Title***" + jobData4.getJobTitle());
        actual.addAll(jobData4.getJobDescriptionText());

        JobData jobData5 = new JobData(path + "goDaddy.htm");
        actual.add("***Title***" + jobData5.getJobTitle());
        actual.addAll(jobData5.getJobDescriptionText());
        assertEquals(expected,actual);
    }

    @Test
    void getLinesWithGoodKeywords_itReadsJobDescriptions_ArrayListOfStringsWithGoodKeywords() {
        ArrayList<String> goodWordsList = new ArrayList<String>();
        goodWordsList.add("Develop new features using C/C++, Java or C#");
        goodWordsList.add("Develop new features using C/C++, Java or C#");
        goodWordsList.add("Experienced programmer (through internships, university projects, open source projects, etc.). At RTI, you will work in C/C++/Java/C#/Python/Lua. We are looking for somebody who knows how to program. Don't worry if you do not master all of these programming languages. We believe a good programmer will pick up other languages quickly.");
        goodWordsList.add("Experienced programmer (through internships, university projects, open source projects, etc.). At RTI, you will work in C/C++/Java/C#/Python/Lua. We are looking for somebody who knows how to program. Don't worry if you do not master all of these programming languages. We believe a good programmer will pick up other languages quickly.");

        JobData jobData = new JobData(path + "goodWordsTest01.htm");
        ArrayList<String> goodLines = jobData.getLinesWithGoodKeywords();
        assertEquals(goodWordsList, goodLines);
    }

    @Test
    void getDontShowJobs_ItReadsTextSearchingForVariationsOfYears_18BadWordsInFile() {
        ArrayList<String> expected = new ArrayList<>();
        expected = fileRead.getLinesFromFile(path + "regexTest.txt");
        RegExLookAt regExLookAt = new RegExLookAt();

        int counter = 0;
        for (String s : expected) {
            if (regExLookAt.regExPatternMatch(s, "^[1-9][t0+y-][oey+\\d][\\daye][yrea][rseao]")) {
                counter++;
            }
        }
        assertEquals(18, counter);
    }

    @Test
    void getDatePosted_ItReturnsTheProperDate_StringOfDate()
    {
        JobData jobData = new JobData(path + "goodWordsTest01.htm");
        assertEquals("2019-04-11", jobData.getDatePosted());

        JobData jobData1 = new JobData(path + "CompanySite.htm");
        assertEquals("2019-03-15",jobData1.getDatePosted());
    }

    @Test
    void getDatePosted_invalidSites_StringOfDate()
    {
        JobData jobData = new JobData(path + "cantFindPage.htm");
        assertEquals("can't find site or site is invalid.", jobData.getDatePosted());

        JobData jobData1 = new JobData(path + "expiered.htm");
        assertEquals("can't find site or site is invalid.",jobData1.getDatePosted());
    }

    @Test
    void getDifferenceBetweenDays_itReturnsThedifferenceBetweenTwoDates_returnsNumberOfDays() throws IOException, InterruptedException {
        JobData jobData = new JobData(path + "goodWordsTest01.htm");
        String initialDate = jobData.getDatePosted();
        String  differenceBetweenDays = jobData.getDifferenceBetweenDates("2019-04-12", initialDate, "yyyy-MM-dd");
        assertEquals("1", differenceBetweenDays);

        JobData jobData1 = new JobData(path + "CompanySite.htm");
        initialDate = jobData1.getDatePosted();
        differenceBetweenDays = jobData1.getDifferenceBetweenDates("2019-04-12", initialDate, "yyyy-MM-dd");
        assertEquals("28",differenceBetweenDays);
    }

    @Test
    void getDifferenceBetweenDays_badSites_returnsSiteIsInvalid(){
        String initialDate;
        String differenceBetweenDays;

        JobData jobData3 = new JobData(path + "cantFindPage.htm");
        initialDate = jobData3.getDatePosted();

        differenceBetweenDays = jobData3.getDifferenceBetweenDates("2019-04-12", initialDate, "yyyy-MM-dd");
        assertEquals("can't find site or site is invalid.",differenceBetweenDays);

        JobData jobData4 = new JobData(path + "expiered.htm");
        initialDate = jobData4.getDatePosted();
        differenceBetweenDays = jobData4.getDifferenceBetweenDates("2019-04-12", initialDate, "yyyy-MM-dd");
        assertEquals("can't find site or site is invalid.",differenceBetweenDays);
    }

    @Test
    void getDifferenceBetweenDays_badDates_returns999999(){
        String differenceBetweenDays;
        JobData jobData3 = new JobData(path + "cantFindPage.htm");

        differenceBetweenDays = jobData3.getDifferenceBetweenDates("2019-04-12", "asdf", "yyyy-MM-dd");
        assertEquals("invalid date.",differenceBetweenDays);

        JobData jobData4 = new JobData(path + "expiered.htm");
        differenceBetweenDays = jobData4.getDifferenceBetweenDates("asdfas", "2019-04-12", "yyyy-MM-dd");
        assertEquals("invalid date.",differenceBetweenDays);

    }


    @Test
    void getRankTest() throws InterruptedException {
        JobData jobData1 = new JobData(path + "goodWordsTest01.htm");

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
    void getJobLink() throws IOException {
        String jobSite = path + "goodWordsTest01.htm";
        JobData jobData = new JobData(jobSite);
        assertEquals("https://www.glassdoor.com/job-listing/software-engineer-real-time-innovations-JV_IC1147442_KO0,17_KE18,39.htm?jl=2915387876", jobData.getJobLink());
    }

    @Test
    void getTitle() throws IOException {
        JobData jobData = new JobData(path + "cantFindPage.htm");
        assertEquals("JobTitle: Job expired, no data.", jobData.getJobTitle());

        JobData jobData1 = new JobData(path + "CompanySite.htm");
        assertEquals("Revature Entry Level C# Software Developer Job in Hayward, CA | Glassdoor", jobData1.getJobTitle());
    }

    @Test
    void getApplyType() throws IOException {
        //company site
        JobData jobData = new JobData(path + "CompanySite.htm");
        assertEquals("Apply on Company Site", jobData.getApplyType());
        //Easy apply
        JobData jobData2 = new JobData(path + "easyApply.htm");
        assertEquals("Easy Apply", jobData2.getApplyType());
        //expired
        JobData jobData3 = new JobData(path + "expiered.htm");
        assertEquals("Apply Type: Job expired, no data.", jobData3.getApplyType());
        //partner site
        JobData jobData4 = new JobData(path + "partnerSite.htm");
        assertEquals("Apply on Partner Site", jobData4.getApplyType());
    }
}