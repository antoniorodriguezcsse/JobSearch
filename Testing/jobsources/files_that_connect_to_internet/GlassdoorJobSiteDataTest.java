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
    private JobSiteData glassdoorJobSiteData = new JobSiteData();
    private String path = "TestingGlassdoor/";
    //  private JobSiteData glassdoorJobSiteData = new JobSiteData();

    @Test
    void getApplyTypeTest() throws CustomExceptions {
        assertEquals("Apply type has not been set.", glassdoorJobSiteData.getApplyType());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorCompanySite.htm");
        assertEquals("Apply on Company Site", glassdoorJobSiteData.getApplyType());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorEasyApply.htm");
        assertEquals("Easy Apply", glassdoorJobSiteData.getApplyType());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorPartnerSite.htm");
        assertEquals("Apply on Partner Site", glassdoorJobSiteData.getApplyType());
    }

    @Test
    void getJobTitleTest() throws IOException, CustomExceptions {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Revature Entry Level Software Engineer (No Coding Experience Required) Job in Oakland, CA | Glassdoor");
        expected.add("Revature Entry Level C# Software Developer Job in Hayward, CA | Glassdoor");
        expected.add("Tarana Wireless Lead Software Engineer Job in Santa Clara, CA | Glassdoor");
        expected.add("General Dynamics Mission Systems Entry-Level Software Engineer Job in San Jose, California | Glassdoor");

        ArrayList<String> jobSites = new ArrayList<>();
        jobSites.add(path + "glassdoorGetJobTitle1.htm");
        jobSites.add(path + "glassdoorGetJobTitle2.htm");
        jobSites.add(path + "glassdoorGetJobTitle3.htm");
        jobSites.add(path + "glassdoorGetJobTitle4.htm");

        assertEquals(jobSites.size(), expected.size());
        for (int i = 0; i < jobSites.size(); i++) {
            glassdoorJobSiteData.connectToJobSite(jobSites.get(i));
            assertEquals(expected.get(i), glassdoorJobSiteData.getJobTitle());
        }
    }

    @Test
    void getJobTitle_CallingTheFunctionWhenDataHasNotBeenSet_returnStringTitleNotset() {
        assertEquals("Job title has not been set.", glassdoorJobSiteData.getJobTitle());
    }

    @Test
    void getJobLink_whenDataHasntBeenSet_stringReturnsSitehasNotbeenConnected() {
        assertEquals("Job link has not been set.", glassdoorJobSiteData.getJobLink());
    }

    @Test
    void getDatePosted_CalledWhenDataHasntBeenSet() {
        assertEquals("Date posted has not been set.", glassdoorJobSiteData.getDatePosted());
    }

    @Test
    void getDatePosted() throws CustomExceptions {
        glassdoorJobSiteData.connectToJobSite(path + "glassdoorJobSite1.htm");
        assertEquals("03-15-2019", glassdoorJobSiteData.getDatePosted());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorCompanySite.htm");
        assertEquals("03-15-2019", glassdoorJobSiteData.getDatePosted());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorEasyApply.htm");
        assertEquals("03-26-2019", glassdoorJobSiteData.getDatePosted());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorPartnerSite.htm");
        assertEquals("03-14-2019", glassdoorJobSiteData.getDatePosted());
    }

    @Test
    void getJobLinks() throws CustomExceptions {
        glassdoorJobSiteData.connectToJobSite(path + "glassdoorJobSite1.htm");
        assertEquals(path + "glassdoorJobSite1.htm", glassdoorJobSiteData.getJobLink());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorCompanySite.htm");
        assertEquals(path + "glassdoorCompanySite.htm", glassdoorJobSiteData.getJobLink());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorEasyApply.htm");
        assertEquals(path + "glassdoorEasyApply.htm", glassdoorJobSiteData.getJobLink());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorPartnerSite.htm");
        assertEquals(path + "glassdoorPartnerSite.htm", glassdoorJobSiteData.getJobLink());
    }


    @Test
    void getJobDescriptionText_CalledWhenDataHasntBeenSet() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Text from Job Description has not been set.");
        assertEquals(expected, glassdoorJobSiteData.getTextFromJobDescription());

    }

    @Test
    void getJobDescriptionText() throws IOException, CustomExceptions {
        ArrayList<String> expected = new ArrayList<>();
        expected = fileRead.getLinesFromFile(path + "allTextTest.txt");

        ArrayList<String> actual = new ArrayList<>();
        glassdoorJobSiteData.connectToJobSite(path + "glassdoorJobSite1.htm");
        actual.add("***Title***Revature Entry Level Software Engineer (No Coding Experience Required) Job in Oakland, CA | Glassdoor");
        actual.addAll(glassdoorJobSiteData.getTextFromJobDescription());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorCompanySite.htm");
        actual.add("***Title***Revature Entry Level C# Software Developer Job in Hayward, CA | Glassdoor");
        actual.addAll(glassdoorJobSiteData.getTextFromJobDescription());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorEasyApply.htm");
        actual.add("***Title***" + glassdoorJobSiteData.getJobTitle());
        actual.addAll(glassdoorJobSiteData.getTextFromJobDescription());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorPartnerSite.htm");
        actual.add("***Title***" + glassdoorJobSiteData.getJobTitle());
        actual.addAll(glassdoorJobSiteData.getTextFromJobDescription());

        glassdoorJobSiteData.connectToJobSite(path + "glassdoorGoDaddy.htm");
        actual.add("***Title***" + glassdoorJobSiteData.getJobTitle());
        actual.addAll(glassdoorJobSiteData.getTextFromJobDescription());

        assertEquals(expected, actual);
    }

    @Test
    void getJobLink_thatItgetsTheJobLinksFromTheSite_returnsStringOfSiteURL() throws CustomExceptions {
        ArrayList<String> expected = new ArrayList<>();
        expected.add(path + "glassdoorJobSite1.htm");
        expected.add(path + "glassdoorCompanySite.htm");
        expected.add(path + "glassdoorEasyApply.htm");
        expected.add(path + "glassdoorPartnerSite.htm");

        ArrayList<String> actual = new ArrayList<>();
        glassdoorJobSiteData.connectToJobSite(path + "glassdoorJobSite1.htm");
        actual.add(glassdoorJobSiteData.getJobLink());
        glassdoorJobSiteData.connectToJobSite(path + "glassdoorCompanySite.htm");
        actual.add(glassdoorJobSiteData.getJobLink());
        glassdoorJobSiteData.connectToJobSite(path + "glassdoorEasyApply.htm");
        actual.add(glassdoorJobSiteData.getJobLink());
        glassdoorJobSiteData.connectToJobSite(path + "glassdoorPartnerSite.htm");
        actual.add(glassdoorJobSiteData.getJobLink());
        assertEquals(expected, actual);
    }

    @Test
    void verifyDivContainers_cantFindApplyType() throws CustomExceptions {
        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                glassdoorJobSiteData.connectToJobSite(path + "glassdoorCantFindApplyType.htm");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: GlassdoorJobSiteData.getApplyType: div.regToApplyArrowBoxContainer can't be found.")) {
                    throw e;
                }
            }
        });
    }

    @Test
    void verifyDivContainers_CantFindDate()
    {
        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                glassdoorJobSiteData.connectToJobSite(path + "glassdoorCantFindDatePosted.htm");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: GlassdoorJobSiteData.getDatePosted: div.pageInsideContent can't be found.")) {
                    throw e;
                }
            }
        });
    }

    @Test
    void verifyDivContainers_CantFindJobDescription()
    {
        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                glassdoorJobSiteData.connectToJobSite(path + "glassdoorCantFindJobDescriptionText.htm");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: GlassdoorJobSiteData.setTextJobDescription: div.jobDescriptionContent.desc can't be found.")) {
                    throw e;
                }
            }
        });
    }
}