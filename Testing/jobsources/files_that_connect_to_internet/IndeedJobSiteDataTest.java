package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import jobsources.files_that_work_with_job_data.DateTools;
import jobsources.files_that_work_with_job_data.InterfaceDateTools;
import jobsources.read_write_to_files.FileRead;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IndeedJobSiteDataTest {
    private JobSiteData indeedJobSiteData = new JobSiteData();
    private String indeedPath = "TestingIndeed/";

    @Test
    void connectToJobSite_invalidSite() throws CustomExceptions {
        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_InvalidSite(404).htm");
        assertEquals("JobSiteData: Page can't be found: no date posted.",indeedJobSiteData.getDatePosted());
        assertEquals("TestingIndeed/indeed_jobSiteData_InvalidSite(404).htm",indeedJobSiteData.getJobLink());
        assertEquals("JobSiteData: Page can't be found: No apply type data.",indeedJobSiteData.getApplyType());
        assertEquals("JobSiteData: Page can't be found: No job title.",indeedJobSiteData.getJobTitle());

        ArrayList<String> error = new ArrayList<>();
        error.add("JobSiteData: Page can't be found: No job description.");
        assertEquals(error,indeedJobSiteData.getTextFromJobDescription());
    }

    @Test
    void getJobTitle() throws CustomExceptions {
        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getHTMLTitle.htm");
        assertEquals("Embedded Software Engineer SmartBins San Francisco, CA", indeedJobSiteData.getJobTitle());
    }

    @Test
    void verifyDivContainers_cantFindJobTitle() throws CustomExceptions {
        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_CantFindJobTitle.htm");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: IndeedJobSiteData.jobTitleContainer: div.jobsearch-DesktopStickyContainer can't be found.")) {
                    throw e;
                }
            }
        });
    }

    @Test
    void getJobDescriptionText() throws CustomExceptions {
        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getHTMLTitle.htm");

        FileRead fileRead = new FileRead();
        ArrayList<String> linesFromFile = fileRead.getLinesFromFile(indeedPath + "getJobDescriptionTest.txt");

        assertEquals(linesFromFile, indeedJobSiteData.getTextFromJobDescription());
    }

    @Test
    void verifyDivContainers_cantFindJobDescription() throws CustomExceptions {
        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_CantFindJobDescription.htm");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: IndeedJobSiteData.setTextFromJobDescription: div.jobsearch-jobDescriptionText can't be found.")) {
                    throw e;
                }
            }
        });
    }

    @Test
    void getDate() throws CustomExceptions {
        InterfaceDateTools differenceBetweenTwoDates = new DateTools();

        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getDate.htm");
        String date = differenceBetweenTwoDates.getDateMinusDays("30", "MM-dd-yyyy");
        assertEquals(date, indeedJobSiteData.getDatePosted());

        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getDate1DayAgo.htm");
        date = differenceBetweenTwoDates.getDateMinusDays("1", "MM-dd-yyyy");
        assertEquals(date, indeedJobSiteData.getDatePosted());

        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getDate2DaysAgo.htm");
        date = differenceBetweenTwoDates.getDateMinusDays("2", "MM-dd-yyyy");
        assertEquals(date, indeedJobSiteData.getDatePosted());

        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getDate4HoursAgo.htm");
        date = differenceBetweenTwoDates.getDateMinusDays("0", "MM-dd-yyyy");
        assertEquals(date, indeedJobSiteData.getDatePosted());

        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getDate21DaysAgo.htm");
        date = differenceBetweenTwoDates.getDateMinusDays("21", "MM-dd-yyyy");
        assertEquals(date, indeedJobSiteData.getDatePosted());

        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getDateToday.htm");
        date = differenceBetweenTwoDates.getDateMinusDays("0", "MM-dd-yyyy");
        assertEquals(date, indeedJobSiteData.getDatePosted());
    }

    @Test
    void verifyDivContainers_cantFindDate() throws CustomExceptions {
        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_CantFindDate.htm");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: IndeedJobSiteData.setDatePosted: div.jobsearch-JobMetadataFooter can't be found.")) {
                    throw e;
                }
            }
        });
    }

    @Test
    void getApplyType_EasyApply() throws CustomExceptions {
        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_EasyApply.htm");
        assertEquals("Easy Apply", indeedJobSiteData.getApplyType());

        indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_getDateToday.htm");
        assertEquals("Apply On Company Site", indeedJobSiteData.getApplyType());
    }

    @Test
    void verifyDivContainers_cantFindApplyType() throws CustomExceptions {
        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                indeedJobSiteData.connectToJobSite(indeedPath + "indeed_jobSiteData_CantFindApplyTypeCompanySite.htm");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: IndeedJobSiteData.getApplyType: div.icl-u-xs-hide.icl-u-lg-block.icl-u-lg-textCenter can't be found.")) {
                    throw e;
                }
            }
        });
    }


}