package jobsources;

import jobsources.files_that_connect_to_internet.JobSiteData;
import jobsources.read_write_to_files.FileRead;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JobSiteDataTest {
    private FileRead fileRead = new FileRead();
    private JobSiteData jobSiteData = new JobSiteData();
    private String path = "D:/Java/JobSearch/websiteTest/";


    @Test
    public void getHTMLTitle_ThatItGetsTheTitleFromHTML_ReturnsStringTitle() throws IOException {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Revature Entry Level Software Engineer (No Coding Experience Required) Job in Oakland, CA | Glassdoor");
        expected.add("Revature Entry Level C# Software Developer Job in Hayward, CA | Glassdoor");
        expected.add("Tarana Wireless Lead Software Engineer Job in Santa Clara, CA | Glassdoor");
        expected.add("General Dynamics Mission Systems Entry-Level Software Engineer Job in San Jose, California | Glassdoor");

        ArrayList<String> jobSites = new ArrayList<>();
        jobSites.add(path + "jobsite1.htm");
        jobSites.add(path + "companySite.htm");
        jobSites.add(path + "easyApply.htm");
        jobSites.add(path + "partnerSite.htm");

        assertEquals(jobSites.size(), expected.size());
        for (int i = 0; i < jobSites.size(); i++) {
            jobSiteData.connectToJobSite(jobSites.get(i));
            assertEquals(expected.get(i), jobSiteData.getHTMLTitle());
        }
    }

    @Test
    void getHTMLTtle_CallingTheFunctionWhenDataHasNotBeenSet_returnStringTitleNotset() {
        assertEquals("Job title has not been set.", jobSiteData.getHTMLTitle());
    }

//    @Test
//    void getBulletPoints() throws IOException {
//        FileRead fileRead = new FileRead();
//        ArrayList<String> expected = fileRead.getLinesFromFile(path + "bulletPoints.txt");
//
//        ArrayList<String> bulletPoints = new ArrayList<>();
//
//        ArrayList<String> jobSites = new ArrayList<>();
//        jobSites.add(path + "jobsite1.htm");
//        jobSites.add(path + "companySite.htm");
//        jobSites.add(path + "easyApply.htm");
//        jobSites.add(path + "partnerSite.htm");
//
//        ArrayList<String> actual = new ArrayList<>();
//
//        for (String jobSite : jobSites) {
//            jobSiteData.connectToJobSite(jobSite);
//            bulletPoints = jobSiteData.getBulletPoints();
//
//            actual.add("***Title*** " + jobSiteData.getHTMLTitle());
//            actual.addAll(bulletPoints);
//        }
//
//        assertEquals(expected, actual);
//    }

//    @Test
//    void getBulletPoints_WhenDataHasntBeenSet_returnDidntConnectToSite() {
//        ArrayList<String> actual = new ArrayList<>();
//        actual.add(("Site has not been connected."));
//        assertEquals(jobSiteData.getBulletPoints(), actual);
//    }

    @Test
    void setAllText_ItReturnsAllLinesFromSite_arrayListOfLinesFromSite() throws IOException {
       ArrayList<String> expected = new ArrayList<>();
       expected = fileRead.getLinesFromFile(path + "allTextTest.txt");

        ArrayList<String> actual = new ArrayList<>();
        jobSiteData.connectToJobSite(path + "jobSite1.htm");
        actual.add("***Title***Revature Entry Level Software Engineer (No Coding Experience Required) Job in Oakland, CA | Glassdoor");
        actual.addAll(jobSiteData.getAllText());

        jobSiteData.connectToJobSite(path + "companySite.htm");
        actual.add("***Title***Revature Entry Level C# Software Developer Job in Hayward, CA | Glassdoor");
        actual.addAll(jobSiteData.getAllText());

        jobSiteData.connectToJobSite(path + "easyApply.htm");
        actual.add("***Title***" + jobSiteData.getHTMLTitle());
        actual.addAll(jobSiteData.getAllText());

        jobSiteData.connectToJobSite(path + "partnerSite.htm");
        actual.add("***Title***" + jobSiteData.getHTMLTitle());
        actual.addAll(jobSiteData.getAllText());

        jobSiteData.connectToJobSite(path + "goDaddy.htm");
        actual.add("***Title***" + jobSiteData.getHTMLTitle());
        actual.addAll(jobSiteData.getAllText());
        assertEquals(expected, actual);
    }

    @Test
    void getAllText_CalledWhenDataHasntBeenSet_returnsSiteHasNotBeenConnected() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Site had not been connected.");
        assertEquals(expected, jobSiteData.getAllText());
    }

    @Test
    void getJobLink_thatItgetsTheJobLinksFromTheSite_returnsStringOfSiteURL() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("https://www.glassdoor.com/job-listing/entry-level-software-engineer-no-coding-experience-required-revature-JV_IC1147380_KO0,59_KE60,68.htm?jl=3152523852");
        expected.add("https://www.glassdoor.com/job-listing/entry-level-c-software-developer-revature-JV_IC1147358_KO0,32_KE33,41.htm?jl=3152509760");
        expected.add("https://www.glassdoor.com/job-listing/lead-software-engineer-tarana-wireless-JV_IC1147439_KO0,22_KE23,38.htm?jl=2402894079");
        expected.add("https://www.glassdoor.com/job-listing/entry-level-software-engineer-general-dynamics-mission-systems-JV_IC1147436_KO0,29_KE30,62.htm?jl=3151344235");

        ArrayList<String> actual = new ArrayList<>();

        jobSiteData.connectToJobSite(path + "jobSite1.htm");
        actual.add(jobSiteData.getJobLink());
        jobSiteData.connectToJobSite(path + "companySite.htm");
        actual.add(jobSiteData.getJobLink());
        jobSiteData.connectToJobSite(path + "easyApply.htm");
        actual.add(jobSiteData.getJobLink());
        jobSiteData.connectToJobSite(path + "partnerSite.htm");
        actual.add(jobSiteData.getJobLink());
        assertEquals(expected, actual);
    }

    @Test
    void getJobLink_whenDataHasntBeenSet_stringReturnsSitehasNotbeenConnected() {
        assertEquals("Site had not been connected.", jobSiteData.getJobLink());
    }
}