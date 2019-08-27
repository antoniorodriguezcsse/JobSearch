package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import jobsources.read_write_to_files.FileRead;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GlassdoorJobSiteDataTest {
    private FileRead fileRead = new FileRead();
    private JobSiteData jobSiteData = new JobSiteData();
    private String path = "D:/Java/JobSearch/websiteTest/";


    @Test
    void getHTMLTitle_ThatItGetsTheTitleFromHTML_ReturnsStringTitle() throws IOException, CustomExceptions {
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
            assertEquals(expected.get(i), jobSiteData.getJobTitle());
        }
    }

    @Test
    void getHTMLTtle_CallingTheFunctionWhenDataHasNotBeenSet_returnStringTitleNotset() {
        assertEquals("Job title has not been set.", jobSiteData.getJobTitle());
    }

    @Test
    void setAllText_ItReturnsAllLinesFromSite_arrayListOfLinesFromSite() throws IOException, CustomExceptions {
       ArrayList<String> expected = new ArrayList<>();
       expected = fileRead.getLinesFromFile(path + "allTextTest.txt");

        ArrayList<String> actual = new ArrayList<>();
        jobSiteData.connectToJobSite(path + "jobSite1.htm");
        actual.add("***Title***Revature Entry Level Software Engineer (No Coding Experience Required) Job in Oakland, CA | Glassdoor");
        actual.addAll(jobSiteData.getTextFromJobDescription());

        jobSiteData.connectToJobSite(path + "companySite.htm");
        actual.add("***Title***Revature Entry Level C# Software Developer Job in Hayward, CA | Glassdoor");
        actual.addAll(jobSiteData.getTextFromJobDescription());

        jobSiteData.connectToJobSite(path + "easyApply.htm");
        actual.add("***Title***" + jobSiteData.getJobTitle());
        actual.addAll(jobSiteData.getTextFromJobDescription());

        jobSiteData.connectToJobSite(path + "partnerSite.htm");
        actual.add("***Title***" + jobSiteData.getJobTitle());
        actual.addAll(jobSiteData.getTextFromJobDescription());

        jobSiteData.connectToJobSite(path + "goDaddy.htm");
        actual.add("***Title***" + jobSiteData.getJobTitle());
        actual.addAll(jobSiteData.getTextFromJobDescription());

        assertEquals(expected, actual);
    }

    @Test
    void getAllText_CalledWhenDataHasntBeenSet_returnsSiteHasNotBeenConnected() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Site has not been connected.");
        assertEquals(expected, jobSiteData.getTextFromJobDescription());
    }

    @Test
    void getJobLink_thatItgetsTheJobLinksFromTheSite_returnsStringOfSiteURL() throws CustomExceptions {
        ArrayList<String> expected = new ArrayList<>();
        expected.add(path + "jobSite1.htm");
        expected.add(path + "companySite.htm");
        expected.add(path + "easyApply.htm");
        expected.add(path + "partnerSite.htm");

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
        assertEquals("Site has not been connected.", jobSiteData.getJobLink());
    }

    @Test
    void getJobLink_JobDescriptionCantBeFound() throws CustomExceptions {
        JobSiteData glassdoorJobSiteData = new JobSiteData();
        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                glassdoorJobSiteData.connectToJobSite("https://www.google.com/");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: GlassdoorJobSiteData.setJobDescriptionElement: div.jobDescriptionContent.desc can't be found.")) {
                    throw e;
                }
            }
        });
    }
}