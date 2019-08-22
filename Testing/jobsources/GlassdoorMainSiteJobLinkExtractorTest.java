package jobsources;

import jobsources.files_that_connect_to_internet.MainSiteJobLinkExtractor;
import jobsources.read_write_to_files.FileRead;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class GlassdoorMainSiteJobLinkExtractorTest {
    FileRead fileRead = new FileRead();
    private MainSiteJobLinkExtractor mainSiteJobLinkExtractor = new MainSiteJobLinkExtractor();
    private String result = "";
    private String path = "D:/Java/JobSearch/websiteTest/";
    private String glassDoorPath = "TestingGlassdoor/";
    private String indeedPath = "TestingIndeed/";
    private String mainSite = "https://www.glassdoor.com/Job/concord-software-engineer-entry-level-jobs-SRCH_IL.0,7_IC1147340_KE8,37.htm?radius=50";
    private Method method;

    private String glassDoor = glassDoorPath + "glassdoor(MainSite)_getAllLinksFromOneSite.htm";
    private String indeed = indeedPath + "indeed(MainSite)_getAllLinksFromOneSite.htm";
    private String google = "https://www.google.com/search?q=software+engineer&ibp=htl;jobs#fpstate=tldetail&htichips=city:IQBpAG2ahYD_rXbwZxNQSg%3D%3D&htidocid=pu9vceLegEGQ2YvoAAAAAA%3D%3D&htischips=city;IQBpAG2ahYD_rXbwZxNQSg%3D%3D:San%20Francisco_comma_%20CA&htivrt=jobs";
    private String careerBuilder = "https://www.careerbuilder.com/jobs?utf8=%E2%9C%93&keywords=Software+engineer&location=San+Francisco%2C+CA";
    private String monster = "https://www.monster.com/jobs/search/?q=software-engineer&where=San-Francisco__2C-CA&intcid=skr_navigation_nhpso_searchMain";
    private String simplyHired = "https://www.simplyhired.com/search?q=software+engineer&l=San+Francisco%2C+CA&job=pbUy1cwTveobBHqVm995a7Nq_0PzBbUTQiRt3OWUwS1ovv5p3ZHyQQ";

    // These are being blocked.
    private String linkUp = "https://www.linkup.com/search/results/software-engineer-jobs?location=san+francisco%2C+ca";
    private String zipRecruiter = "https://www.ziprecruiter.com/candidate/search?search=Software+Engineer&location=San+Francisco%2C+CA&days=&radius=5&refine_by_salary=&refine_by_tags=&refine_by_title=&refine_by_org_name=";
    private String linkedin = "https://www.linkedin.com/jobs/search/?geoId=90000084&keywords=software%20engineer&location=San%20Francisco%20Bay%20Area";

    void accessPrivateMethod(String methodName) {
        try {
            method = MainSiteJobLinkExtractor.class.
                    getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    void accessPrivateMethodOneParameter(String methodName) {
        try {
            method = MainSiteJobLinkExtractor.class.
                    getDeclaredMethod(methodName,p);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getlinksFromMainSite() throws CustomExceptions {
        TreeSet<String> jobLinks = new TreeSet<>();
        jobLinks = mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor(MainSite)_getAllLinksFromOneSite.htm");

        for (String s : jobLinks) {
            assertTrue(s.contains("https://www.glassdoor.com/partner/jobListing.htm?pos="));
        }

        jobLinks = mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(indeedPath + "indeed(MainSite)_getAllLinksFromOneSite.htm");
        for (String s : jobLinks) {
            assertTrue(s.contains("https://www.indeed.com/"));
        }

       // jobLinks = mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "not a valid link or name");

        assertThrows(jobsources.CustomExceptions.class, () -> {
          // mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "not a valid link or name");
        //    accessPrivateMethod("setupGlassdoor");
            accessPrivateMethod();
            method.setAccessible(true);

            try {
                method.invoke(mainSiteJobLinkExtractor);
            } catch (InvocationTargetException e) {
                if (e.getCause().toString().equals("jobsources.CustomExceptions: MainSiteJobLinkExtractor: div.pageNavBar.noMargBot can't be found.")) {
                    throw e.getCause();
                }
            }
        });

    }

    @Test
    void getNextMainSite() throws CustomExceptions {
        mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor(MainSite)_getAllLinksFromOneSite.htm");
        String glassdoorNextMainSite = "https://www.glassdoor.com/Job/san-francisco-software-engineer-jobs-SRCH_IL.0,13_IC1147401_KO14,31_IP2.htm";
        assertEquals(glassdoorNextMainSite, mainSiteJobLinkExtractor.getNextMainSite());

        mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(indeedPath + "indeed(MainSite)_getAllLinksFromOneSite.htm");
        String indeedNextMainSite = "https://www.indeed.com/jobs?q=software+engineer&l=San+Francisco%2C+CA&start=10";
        assertEquals(indeedNextMainSite, mainSiteJobLinkExtractor.getNextMainSite());
    }

    @Test
    void noNextSiteTest() throws CustomExceptions {
        //on last page no more next pages
        mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor_getNextPage_DoesNotHaveANextPage.htm");
        assertEquals("no more pages", mainSiteJobLinkExtractor.getNextMainSite());

        //no next pages at all
        mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor_getNextPage_DoesNotHaveAnySubpages.htm");
        assertEquals("no more pages", mainSiteJobLinkExtractor.getNextMainSite());

        //OnLast page no more next pages
        mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(indeedPath + "indeed_getNextPage_DoesNotHaveANextPage.htm");
        assertEquals("no more pages", mainSiteJobLinkExtractor.getNextMainSite());

        //no pages at all
        mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(indeedPath + "indeed_getNextPage_DoesNotHaveAnySubpages.htm");
        assertEquals("no more pages", mainSiteJobLinkExtractor.getNextMainSite());

    }

    @Test
    void cantFindDivElementsGlassdoor() throws CustomExceptions, InvocationTargetException, IllegalAccessException {
        //can't find paging control
        assertThrows(jobsources.CustomExceptions.class, () -> {
            mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor(MainSite)_cantFindPageNavigationBar.htm");
            accessPrivateMethod("setupGlassdoor");
            method.setAccessible(true);

            try {
                method.invoke(mainSiteJobLinkExtractor);
            } catch (InvocationTargetException e) {
                if (e.getCause().toString().equals("jobsources.CustomExceptions: MainSiteJobLinkExtractor: div.pageNavBar.noMargBot can't be found.")) {
                    throw e.getCause();
                }
            }
        });

        assertThrows(jobsources.CustomExceptions.class, () -> {
            mainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor(MainSite)_cantFindPagingControls.htm");
            accessPrivateMethod("setupGlassdoor");
            method.setAccessible(true);

            try {
                method.invoke(mainSiteJobLinkExtractor);
            } catch (InvocationTargetException e) {
                if (e.getCause().toString().equals("jobsources.CustomExceptions: MainSiteJobLinkExtractor: div.pagingControls.cell.middle can't be found.")) {
                    throw e.getCause();
                }
            }
        });
    }
}