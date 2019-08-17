package jobsources;

import jobsources.files_that_connect_to_internet.MainSiteJobLinkExtractor;
import jobsources.read_write_to_files.FileRead;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlassdoorMainSiteJobLinkExtractorTest {
    FileRead fileRead = new FileRead();
    private MainSiteJobLinkExtractor mainSiteJobLinkExtractor = new MainSiteJobLinkExtractor();
    private String result = "";
    private String path = "D:/Java/JobSearch/websiteTest/";
    private String mainSite = "https://www.glassdoor.com/Job/concord-software-engineer-entry-level-jobs-SRCH_IL.0,7_IC1147340_KE8,37.htm?radius=50";
    private Method method;

    private String glassDoor = "https://www.glassdoor.com/Job/jobs.htm?suggestCount=0&suggestChosen=false&clickSource=searchBtn&typedKeyword=Software+Engineer&sc.keyword=Software+Engineer&locT=C&locId=1147401&jobType=";
    private String google = "https://www.google.com/search?q=software+engineer&ibp=htl;jobs#fpstate=tldetail&htichips=city:IQBpAG2ahYD_rXbwZxNQSg%3D%3D&htidocid=pu9vceLegEGQ2YvoAAAAAA%3D%3D&htischips=city;IQBpAG2ahYD_rXbwZxNQSg%3D%3D:San%20Francisco_comma_%20CA&htivrt=jobs";
    private String careerBuilder = "https://www.careerbuilder.com/jobs?utf8=%E2%9C%93&keywords=Software+engineer&location=San+Francisco%2C+CA";
    private String monster = "https://www.monster.com/jobs/search/?q=software-engineer&where=San-Francisco__2C-CA&intcid=skr_navigation_nhpso_searchMain";
    private String simplyHired = "https://www.simplyhired.com/search?q=software+engineer&l=San+Francisco%2C+CA&job=pbUy1cwTveobBHqVm995a7Nq_0PzBbUTQiRt3OWUwS1ovv5p3ZHyQQ";
    private String indeed = "https://www.indeed.com/jobs?q=software+engineer&l=San+Francisco%2C+CA";

    // These are being blocked.
    private String linkUp = "https://www.linkup.com/search/results/software-engineer-jobs?location=san+francisco%2C+ca";
    private String zipRecruiter = "https://www.ziprecruiter.com/candidate/search?search=Software+Engineer&location=San+Francisco%2C+CA&days=&radius=5&refine_by_salary=&refine_by_tags=&refine_by_title=&refine_by_org_name=";
    private String linkedin = "https://www.linkedin.com/jobs/search/?geoId=90000084&keywords=software%20engineer&location=San%20Francisco%20Bay%20Area";

    @Test
    void getlinksFromMainSite() throws CustomExceptions {
        TreeSet<String> jobLinks = new TreeSet<>();
        jobLinks = mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoor);

        for (String s : jobLinks) {
            assertTrue(s.contains("https://www.glassdoor.com/partner/jobListing.htm?pos="));
        }

        jobLinks = mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(indeed);
        for (String s : jobLinks) {
            assertTrue(s.contains("https://www.glassdoor.com/partner/jobListing.htm?pos="));
        }
    }

    @Test
    void noNextSiteTest() throws CustomExceptions {

        //on last page no more next pages
        mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite("https://www.glassdoor.com/Job/san-ramon-software-engineer-jobs-SRCH_IL.0,9_IC1147410_KO10,27_IP3.htm?radius=0&fromAge=14");
        assertEquals("no more pages", mainSiteJobLinkExtractor.getNextMainSite());

////no next pages at all
        mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite("https://www.glassdoor.com/Job/jobs.htm?sc.keyword=software%20engineer&locT=C&locId=1147410&locKeyword=San%20Ramon,%20CA&jobType=all&fromAge=1&minSalary=0&includeNoSalaryJobs=true&radius=0&cityId=-1&minRating=0.0&industryId=-1&sgocId=-1&seniorityType=all&companyId=-1&employerSizes=0&applicationType=0&remoteWorkType=0");
        assertEquals("no more pages", mainSiteJobLinkExtractor.getNextMainSite());

        //OnLast page no more next pages
        mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite("https://www.indeed.com/jobs?q=software+engineer&l=Concord%2C+CA&radius=0&start=60");
        System.out.println(mainSiteJobLinkExtractor.getNextMainSite());
        assertEquals("no more pages", mainSiteJobLinkExtractor.getNextMainSite());

        //no pages at all
        mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite("https://www.indeed.com/jobs?q=baker&l=Concord%2C+CA&radius=0");
        assertEquals("no more pages", mainSiteJobLinkExtractor.getNextMainSite());

    }
//    void accessPrivateMethod(String methodName) {
//        try {
//            method = MainSiteJobLinkExtractor.class.
//                    getDeclaredMethod(methodName);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//    }
//
//    void accessPrivateMethodOneParameter(String methodName) {
//        try {
//            method = MainSiteJobLinkExtractor.class.
//                    getDeclaredMethod(methodName, String.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void connectToMainSite_ItConnectsSuccessfully_returnConnected() throws InvocationTargetException, IllegalAccessException {
//        accessPrivateMethodOneParameter("connectToMainWebSite");
//        method.setAccessible(true);
//
//        String returnValue = (String) method.invoke(mainSiteJobLinkExtractor, mainSite);
//
//        assertEquals("Connected.", returnValue);
//    }
//
//    @Test
//    void connectToFile_itConnectSuccessfully_ReturnsConnected() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        String mainPath = path + "jobsite1.htm";
//
//        accessPrivateMethodOneParameter("connectToMainWebSite");
//        method.setAccessible(true);
//
//        String returnValue = (String) method.invoke(mainSiteJobLinkExtractor, mainPath);
//        assertEquals("Connected.", returnValue);
//    }
//
//    @Test
//    void connectToMainSite_invalidURL_ThrowsExceptionCouldNotConnectToSite() {
//        String emptyPath = "";
//
//        assertThrows(jobsources.CustomExceptions.class, () -> {
//            accessPrivateMethodOneParameter("connectToMainWebSite");
//            method.setAccessible(true);
//            try {
//                String returnValue = (String) method.invoke(mainSiteJobLinkExtractor, emptyPath);
//            } catch (InvocationTargetException e) {
//                Throwable cause = e.getCause();
//
//                if (String.valueOf(cause).equals("jobsources.CustomExceptions: GlassdoorMainSiteJobLinkExtractor.connectToMainWebSite Could not connect to site.")) {
//                    throw cause;
//                }
//            }
//        });
//    }
//
//    @Test
//    void connectToMainSite_CantFindsJobContainer_ThrowsExceptionCantFindJobContainer() {
//        assertThrows(jobsources.CustomExceptions.class, () -> {
//            accessPrivateMethodOneParameter("connectToMainWebSite");
//            method.setAccessible(true);
//            try {
//                String returnValue = (String) method.invoke(mainSiteJobLinkExtractor, path + "jobsite1.htm");
//            } catch (InvocationTargetException e) {
//                Throwable cause = e.getCause();
//                if (String.valueOf(cause).equals("jobsources.CustomExceptions: GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: div.jobContainer can't be found.")) {
//                    throw cause;
//                }
//            }
//        });
//    }
//
//    @Test
//    void connectToMainSite_CantFindsJobLinkContainer_ThrowsExceptionCantFindJobLinkContainer() {
//        assertThrows(jobsources.CustomExceptions.class, () -> {
//            accessPrivateMethodOneParameter("connectToMainWebSite");
//            method.setAccessible(true);
//            try {
//                String returnValue = (String) method.invoke(mainSiteJobLinkExtractor, path + "NoJobLinkContainer.htm");
//            } catch (InvocationTargetException e) {
//                Throwable cause = e.getCause();
//                if (String.valueOf(cause).equals("jobsources.CustomExceptions: GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: a.jobLink.jobInfoItem.jobTitle can't be found.")) {
//                    throw cause;
//                }
//            }
//        });
//    }
//
//
//    @Test
//    void connectToFile_NotAValidHTMLFile_StringNotValidHTMLFile() throws InvocationTargetException, IllegalAccessException {
//        String mainPath = path + "allJobLinksFromALlSites.txt";
//        accessPrivateMethodOneParameter("connectToMainWebSite");
//        method.setAccessible(true);
//
//        String returnValue = (String) method.invoke(mainSiteJobLinkExtractor, mainPath);
//        assertEquals("not a valid HTML file.", returnValue);
//    }
//
//    @Test
//    void validateJobContainer_ItFindTheJobContainer_StringJobContainerFound() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        accessPrivateMethodOneParameter("connectToMainWebSite");
//        method.setAccessible(true);
//        method.invoke(mainSiteJobLinkExtractor, mainSite);
//
//        accessPrivateMethod("validateJobContainer");
//        method.setAccessible(true);
//        String returnValue = (String)
//                method.invoke(mainSiteJobLinkExtractor);
//
//        assertEquals("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: div.jobContainer found.", returnValue);
//    }
//
//    @Test
//    void validateJobContainer_ItCantFindJobContainer_StringJobContainerNotFound() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        String mainPath = path + "jobsite1.htm";
//
//        accessPrivateMethodOneParameter("connectToMainWebSite");
//        method.setAccessible(true);
//        method.invoke(mainSiteJobLinkExtractor, mainPath);
//
//        accessPrivateMethod("validateJobContainer");
//        method.setAccessible(true);
//        String returnValue = (String)
//                method.invoke(mainSiteJobLinkExtractor);
//
//        assertEquals("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: div.jobContainer can't be found.", returnValue);
//    }
//
//    @Test
//    void validateJobLinkContainer_ItCanFindTheLinkContainer_returnsFound() throws InvocationTargetException, IllegalAccessException {
//        accessPrivateMethodOneParameter("connectToMainWebSite");
//        method.setAccessible(true);
//        method.invoke(mainSiteJobLinkExtractor, mainSite);
//
//        accessPrivateMethod("validateJobLinkContainer");
//        method.setAccessible(true);
//        String returnValue = (String) method.invoke(mainSiteJobLinkExtractor);
//
//        assertEquals("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: a.jobLink.jobInfoItem.jobTitle found.", returnValue);
//    }
//
//    @Test
//    void validateJobLinkContainer_ItCantFindTheLinkContainer_returnsFound() throws InvocationTargetException, IllegalAccessException {
//        String mainPath = path + "jobsite1.htm";
//        accessPrivateMethodOneParameter("connectToMainWebSite");
//        method.setAccessible(true);
//        method.invoke(mainSiteJobLinkExtractor, mainPath);
//
//        accessPrivateMethod("validateJobLinkContainer");
//        method.setAccessible(true);
//        String returnValue = (String) method.invoke(mainSiteJobLinkExtractor);
//
//        assertEquals("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: a.jobLink.jobInfoItem.jobTitle can't be found.", returnValue);
//    }
//
//    @Test
//    void getAllTwentyLinks() throws CustomExceptions {
//        String nextMainSite = "https://www.glassdoor.com/Job/concord-software-engineer-jobs-SRCH_IL.0,7_IC1147340_KO8,25_IP3.htm?srs=RECENT_SEARCHES";
//        ArrayList<String> allSites = new ArrayList<>();
//        allSites = mainSiteJobLinkExtractor.getAllJobLinksFromAllMainSites("20", nextMainSite);
//
//        for (String s : allSites) {
//            assert (s.contains("https://www.glassdoor.com/partner/jobListing.htm?pos"));
//            System.out.println(s);
//        }
//
//        assertEquals(20, allSites.size());
//    }

}