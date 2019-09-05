package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import jobsources.read_write_to_files.FileRead;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class GlassdoorMainSiteJobLinkExtractorTest {
    FileRead fileRead = new FileRead();
    private GlassdoorMainSiteJobLinkExtractor glassdoorMainSiteJobLinkExtractor = new GlassdoorMainSiteJobLinkExtractor();
    private String result = "";
    private String path = "D:/Java/JobSearch/websiteTest/";
    private String glassDoorPath = "TestingGlassdoor/";
    private String glassDoor = glassDoorPath + "glassdoor(MainSite)_getAllLinksFromOneSite.htm";

    private String google = "https://www.google.com/search?q=software+engineer&ibp=htl;jobs#fpstate=tldetail&htichips=city:IQBpAG2ahYD_rXbwZxNQSg%3D%3D&htidocid=pu9vceLegEGQ2YvoAAAAAA%3D%3D&htischips=city;IQBpAG2ahYD_rXbwZxNQSg%3D%3D:San%20Francisco_comma_%20CA&htivrt=jobs";
    private String careerBuilder = "https://www.careerbuilder.com/jobs?utf8=%E2%9C%93&keywords=Software+engineer&location=San+Francisco%2C+CA";
    private String monster = "https://www.monster.com/jobs/search/?q=software-engineer&where=San-Francisco__2C-CA&intcid=skr_navigation_nhpso_searchMain";
    private String simplyHired = "https://www.simplyhired.com/search?q=software+engineer&l=San+Francisco%2C+CA&job=pbUy1cwTveobBHqVm995a7Nq_0PzBbUTQiRt3OWUwS1ovv5p3ZHyQQ";

    // These are being blocked.
    private String linkUp = "https://www.linkup.com/search/results/software-engineer-jobs?location=san+francisco%2C+ca";
    private String zipRecruiter = "https://www.ziprecruiter.com/candidate/search?search=Software+Engineer&location=San+Francisco%2C+CA&days=&radius=5&refine_by_salary=&refine_by_tags=&refine_by_title=&refine_by_org_name=";
    private String linkedin = "https://www.linkedin.com/jobs/search/?geoId=90000084&keywords=software%20engineer&location=San%20Francisco%20Bay%20Area";


    @Test
    void getlinksFromMainSite() throws CustomExceptions {
        TreeSet<String> jobLinks = new TreeSet<>();
        jobLinks = glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor(MainSite)_getAllLinksFromOneSite.htm");

        for (String s : jobLinks) {
            System.out.println(s);
            assertTrue(s.contains("https://www.glassdoor.com/partner/jobListing.htm?pos="));
        }
    }

    @Test
    void getNextMainSite() throws CustomExceptions {
        glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor(MainSite)_getAllLinksFromOneSite.htm");
        String glassdoorNextMainSite = "https://www.glassdoor.com/Job/san-francisco-software-engineer-jobs-SRCH_IL.0,13_IC1147401_KO14,31_IP2.htm";
        assertEquals(glassdoorNextMainSite, glassdoorMainSiteJobLinkExtractor.getNextMainSite());
    }

    @Test
    void noNextSiteTest() throws CustomExceptions {
        //on last page no more next pages
        glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor_getNextPage_DoesNotHaveANextPage.htm");
        assertEquals("no more pages", glassdoorMainSiteJobLinkExtractor.getNextMainSite());

        //no next pages at all
        glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor_getNextPage_DoesNotHaveAnySubpages.htm");
        assertEquals("no more pages", glassdoorMainSiteJobLinkExtractor.getNextMainSite());
    }

    @Test
    void cantFindDivElementsGlassdoor() {
        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite("invalid link");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: MainSiteJobLinkExtractor.connectToMainWebSite: Could not connect to site.")) {
                    throw e;
                }
            }
        });

        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor(MainSite)_cantFindPageNavigationBar.htm");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: GlassdoorMainSiteJobLinkExtractor: div.pageNavBar.noMargBot can't be found.")) {
                    throw e;
                }
            }
        });

        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor(MainSite)_cantFindPageNavigationBar.htm");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: GlassdoorMainSiteJobLinkExtractor: div.pageNavBar.noMargBot can't be found.")) {
                    throw e;
                }
            }

        });

        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                glassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(glassDoorPath + "glassdoor(MainSite)_cantFindPagingControls.htm");
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: GlassdoorMainSiteJobLinkExtractor: div.pagingControls.cell.middle can't be found.")) {
                    throw e;
                }
            }
        });
    }
}