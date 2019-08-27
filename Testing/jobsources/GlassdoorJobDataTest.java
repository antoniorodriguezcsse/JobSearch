//package jobsources;
//
//import jobsources.files_that_work_with_job_data.GlassdoorJobData;
//import jobsources.read_write_to_files.FileRead;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
////import org.junit.jupiter.api.Test;
//
////import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class GlassdoorJobDataTest {
//    private String path = "D:/Java/JobSearch/websiteTest/";
//    FileRead fileRead = new FileRead();
//
//    @Test
//    void testReflection() {
////        Class reflectClass = JobData.class;
////        Method[] classmMethods = reflectClass.getMethods();
////        for (Method method : classmMethods) {
////            System.out.println(method.getName());
////        }
//    }
//    @Test
//    void getLinesWithGoodKeywords_itDoesntConfuseJavaWithJavaScript_ArrayListOfGoodWordsIsEmpty() throws CustomExceptions {
//        ArrayList<String> goodWordsList = new ArrayList<String>();
//
//        GlassdoorJobData glassdoorJobData = new GlassdoorJobData(path + "goodWordsTest02-Javascript.htm");
//        ArrayList<String> goodLines = glassdoorJobData.getLinesWithGoodKeywords();
//        assert (goodLines.isEmpty());
//    }
//
//    @Test
//    void getJobDescriptionText_itReturnsAllLinesFromJobDescription_ArrayListOfAllText() throws CustomExceptions {
//        ArrayList<String> goodWordsList = new ArrayList<String>();
//        ArrayList<String> expected = new ArrayList<>();
//        expected = fileRead.getLinesFromFile(path + "AllTextTest.txt");
//
//        ArrayList<String> actual = new ArrayList<>();
//        GlassdoorJobData glassdoorJobData = new GlassdoorJobData(path + "jobsite1.htm");
//        actual.add("***Title***" + glassdoorJobData.getJobTitle());
//        actual.addAll(glassdoorJobData.getJobDescriptionText());
//
//        GlassdoorJobData glassdoorJobData1 = new GlassdoorJobData(path + "companySite.htm");
//        actual.add("***Title***" + glassdoorJobData1.getJobTitle());
//        actual.addAll(glassdoorJobData1.getJobDescriptionText());
//
//        GlassdoorJobData glassdoorJobData3 = new GlassdoorJobData(path + "easyApply.htm");
//        actual.add("***Title***" + glassdoorJobData3.getJobTitle());
//        actual.addAll(glassdoorJobData3.getJobDescriptionText());
//
//        GlassdoorJobData glassdoorJobData4 = new GlassdoorJobData(path + "partnerSite.htm");
//        actual.add("***Title***" + glassdoorJobData4.getJobTitle());
//        actual.addAll(glassdoorJobData4.getJobDescriptionText());
//
//        GlassdoorJobData glassdoorJobData5 = new GlassdoorJobData(path + "goDaddy.htm");
//        actual.add("***Title***" + glassdoorJobData5.getJobTitle());
//        actual.addAll(glassdoorJobData5.getJobDescriptionText());
//        assertEquals(expected,actual);
//    }
//
//    @Test
//    void getLinesWithGoodKeywords_itReadsJobDescriptions_ArrayListOfStringsWithGoodKeywords() throws CustomExceptions {
//        ArrayList<String> goodWordsList = new ArrayList<String>();
//        goodWordsList.add("Develop new features using C/C++, Java or C#");
//        goodWordsList.add("Develop new features using C/C++, Java or C#");
//        goodWordsList.add("Experienced programmer (through internships, university projects, open source projects, etc.). At RTI, you will work in C/C++/Java/C#/Python/Lua. We are looking for somebody who knows how to program. Don't worry if you do not master all of these programming languages. We believe a good programmer will pick up other languages quickly.");
//        goodWordsList.add("Experienced programmer (through internships, university projects, open source projects, etc.). At RTI, you will work in C/C++/Java/C#/Python/Lua. We are looking for somebody who knows how to program. Don't worry if you do not master all of these programming languages. We believe a good programmer will pick up other languages quickly.");
//
//        GlassdoorJobData glassdoorJobData = new GlassdoorJobData(path + "goodWordsTest01.htm");
//        ArrayList<String> goodLines = glassdoorJobData.getLinesWithGoodKeywords();
//        assertEquals(goodWordsList, goodLines);
//    }
//
//    @Test
//    void getDontShowJobs_ItReadsTextSearchingForVariationsOfYears_18BadWordsInFile() {
//        ArrayList<String> expected = new ArrayList<>();
//        expected = fileRead.getLinesFromFile(path + "regexTest.txt");
//        RegExLookAt regExLookAt = new RegExLookAt();
//
//        int counter = 0;
//        for (String s : expected) {
//            if (regExLookAt.regExPatternMatch(s, "^[1-9][t0+y-][oey+\\d][\\daye][yrea][rseao]")) {
//                counter++;
//            }
//        }
//        assertEquals(18, counter);
//    }
//
//    @Test
//    void getDatePosted_ItReturnsTheProperDate_StringOfDate() throws CustomExceptions {
//        GlassdoorJobData glassdoorJobData = new GlassdoorJobData(path + "goodWordsTest01.htm");
//        assertEquals("2019-04-11", glassdoorJobData.getDatePosted());
//
//        GlassdoorJobData glassdoorJobData1 = new GlassdoorJobData(path + "CompanySite.htm");
//        assertEquals("2019-03-15", glassdoorJobData1.getDatePosted());
//    }
//
//    @Test
//    void getDatePosted_invalidSites_StringOfDate() throws CustomExceptions {
//        GlassdoorJobData glassdoorJobData = new GlassdoorJobData(path + "cantFindPage.htm");
//        assertEquals("can't find site or site is invalid.", glassdoorJobData.getDatePosted());
//
//        GlassdoorJobData glassdoorJobData1 = new GlassdoorJobData(path + "expiered.htm");
//        assertEquals("can't find site or site is invalid.", glassdoorJobData1.getDatePosted());
//    }
//
//    @Test
//    void getDifferenceBetweenDays_itReturnsThedifferenceBetweenTwoDates_returnsNumberOfDays() throws IOException, InterruptedException, CustomExceptions {
//        GlassdoorJobData glassdoorJobData = new GlassdoorJobData(path + "goodWordsTest01.htm");
//        String initialDate = glassdoorJobData.getDatePosted();
//        String  differenceBetweenDays = glassdoorJobData.getDifferenceBetweenDates("2019-04-12", initialDate, "yyyy-MM-dd");
//        assertEquals("1", differenceBetweenDays);
//
//        GlassdoorJobData glassdoorJobData1 = new GlassdoorJobData(path + "CompanySite.htm");
//        initialDate = glassdoorJobData1.getDatePosted();
//        differenceBetweenDays = glassdoorJobData1.getDifferenceBetweenDates("2019-04-12", initialDate, "yyyy-MM-dd");
//        assertEquals("28",differenceBetweenDays);
//    }
//
//    @Test
//    void getDifferenceBetweenDays_badSites_returnsSiteIsInvalid() throws CustomExceptions {
//        String initialDate;
//        String differenceBetweenDays;
//
//        GlassdoorJobData glassdoorJobData3 = new GlassdoorJobData(path + "cantFindPage.htm");
//        initialDate = glassdoorJobData3.getDatePosted();
//
//        differenceBetweenDays = glassdoorJobData3.getDifferenceBetweenDates("2019-04-12", initialDate, "yyyy-MM-dd");
//        assertEquals("can't find site or site is invalid.",differenceBetweenDays);
//
//        GlassdoorJobData glassdoorJobData4 = new GlassdoorJobData(path + "expiered.htm");
//        initialDate = glassdoorJobData4.getDatePosted();
//        differenceBetweenDays = glassdoorJobData4.getDifferenceBetweenDates("2019-04-12", initialDate, "yyyy-MM-dd");
//        assertEquals("can't find site or site is invalid.",differenceBetweenDays);
//    }
//
//    @Test
//    void getDifferenceBetweenDays_badDates_returns999999() throws CustomExceptions {
//        String differenceBetweenDays;
//        GlassdoorJobData glassdoorJobData3 = new GlassdoorJobData(path + "cantFindPage.htm");
//
//        differenceBetweenDays = glassdoorJobData3.getDifferenceBetweenDates("2019-04-12", "asdf", "yyyy-MM-dd");
//        assertEquals("invalid date.",differenceBetweenDays);
//
//        GlassdoorJobData glassdoorJobData4 = new GlassdoorJobData(path + "expiered.htm");
//        differenceBetweenDays = glassdoorJobData4.getDifferenceBetweenDates("asdfas", "2019-04-12", "yyyy-MM-dd");
//        assertEquals("invalid date.",differenceBetweenDays);
//
//    }
//
//
//    @Test
//    void getRankTest() throws InterruptedException, CustomExceptions {
//        GlassdoorJobData glassdoorJobData1 = new GlassdoorJobData(path + "goodWordsTest01.htm");
//
////        String mainSite = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software+engineer+entry+level&locT=C&locId=1147340&srs=JOBS_HOME_RECENT_SEARCHES&radius=50";
////        //String newJobs = "https://www.glassdoor.com/Job/jobs.htm?sc.keyword=lead%20software%20engineer&locT=C&locId=1147439&locKeyword=Santa%20Clara,%20CA&jobType=all&fromAge=1&minSalary=0&includeNoSalaryJobs=true&radius=25&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0";
////        // String jobSite = "https://www.glassdoor.com/job-listing/entry-level-software-engineer-i-relevante-JV_IC1147400_KO0,31_KE32,41.htm?jl=3146625416";
////        GlassdoorMainSiteJobLinkExtractor mainJobLinkExtractor = new GlassdoorMainSiteJobLinkExtractor();
////        mainJobLinkExtractor.connectToWebsite(mainSite);
////
////        ArrayList<String> jobLinks = mainJobLinkExtractor.getAllJobLinksFromSite();
////        for (String s : jobLinks) {
////            //  System.out.println(s);
////            JobData jobSiteParser = new JobData(s);
////            System.out.println(jobSiteParser.getJobLink());
////            System.out.println(jobSiteParser.getRank());
////            Thread.sleep(2000);
////        }
//    }
//
//    @Test
//    void getJobLink() throws IOException, CustomExceptions {
//        String jobSite = path + "goodWordsTest01.htm";
//        GlassdoorJobData glassdoorJobData = new GlassdoorJobData(jobSite);
//        assertEquals("https://www.glassdoor.com/job-listing/software-engineer-real-time-innovations-JV_IC1147442_KO0,17_KE18,39.htm?jl=2915387876", glassdoorJobData.getJobLink());
//    }
//
//    @Test
//    void getTitle() throws IOException, CustomExceptions {
//        GlassdoorJobData glassdoorJobData = new GlassdoorJobData(path + "cantFindPage.htm");
//        assertEquals("JobTitle: Job expired, no data.", glassdoorJobData.getJobTitle());
//
//        GlassdoorJobData glassdoorJobData1 = new GlassdoorJobData(path + "CompanySite.htm");
//        assertEquals("Revature Entry Level C# Software Developer Job in Hayward, CA | Glassdoor", glassdoorJobData1.getJobTitle());
//    }
//
//    @Test
//    void getApplyType() throws IOException, CustomExceptions {
//        //company site
//        GlassdoorJobData glassdoorJobData = new GlassdoorJobData(path + "CompanySite.htm");
//        assertEquals("Apply on Company Site", glassdoorJobData.getApplyType());
//        //Easy apply
//        GlassdoorJobData glassdoorJobData2 = new GlassdoorJobData(path + "easyApply.htm");
//        assertEquals("Easy Apply", glassdoorJobData2.getApplyType());
//        //expired
//        GlassdoorJobData glassdoorJobData3 = new GlassdoorJobData(path + "expiered.htm");
//        assertEquals("Apply Type: Job expired, no data.", glassdoorJobData3.getApplyType());
//        //partner site
//        GlassdoorJobData glassdoorJobData4 = new GlassdoorJobData(path + "partnerSite.htm");
//        assertEquals("Apply on Partner Site", glassdoorJobData4.getApplyType());
//    }
//}