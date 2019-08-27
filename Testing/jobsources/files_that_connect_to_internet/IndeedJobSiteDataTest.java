//package jobsources.files_that_connect_to_internet;
//
//import jobsources.CustomExceptions;
//import jobsources.read_write_to_files.FileRead;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class IndeedJobSiteDataTest {
//    private String indeedPath = "TestingIndeed/";
//    @Test
//    void getHTMLTitle() throws CustomExceptions {
//        IndeedJobSiteData indeedJobSiteData = new IndeedJobSiteData();
//        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getHTMLTitle.htm");
//        assertEquals("Embedded Software Engineer - San Francisco, CA - Indeed.com",indeedJobSiteData.getHTMLTitle());
//    }
//
//    @Test
//    void getJobDescriptionText() throws CustomExceptions {
//        IndeedJobSiteData indeedJobSiteData = new IndeedJobSiteData();
//        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getHTMLTitle.htm");
//
//        FileRead fileRead = new FileRead();
//        ArrayList<String> linesFromFile = fileRead.getLinesFromFile(indeedPath + "getJobDescriptionTest.txt");
//
//        assertEquals(linesFromFile,indeedJobSiteData.getTextFromJobDescription());
//    }
//
//    @Test
//    void getJobLink_JobDescriptionCantBeFound() throws CustomExceptions {
//        IndeedJobSiteData indeedJobSiteData = new IndeedJobSiteData();
//        assertThrows(jobsources.CustomExceptions.class, () -> {
//            try {
//                indeedJobSiteData.connectToJobSite("https://www.google.com/");
//            } catch (CustomExceptions e) {
//                if (e.toString().equals("jobsources.CustomExceptions: IndeedJobSiteData.setJobDescriptionElement: div.jobSearch-jobDescriptionText can't be found.")) {
//                    throw e;
//                }
//            }
//        });
//    }
//
//    @Test
//    void getJobLink_thatItgetsTheJobLinksFromTheSite_returnsStringOfSiteURL() throws CustomExceptions {
//        IndeedJobSiteData indeedJobSiteData = new IndeedJobSiteData();
//        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getHTMLTitle.htm");
//        assertEquals(indeedPath + "indeed_jobSiteData_getHTMLTitle.htm", indeedJobSiteData.getJobLink());
//    }
//}