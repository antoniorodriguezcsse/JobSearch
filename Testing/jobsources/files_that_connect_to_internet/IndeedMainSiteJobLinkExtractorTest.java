package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IndeedMainSiteJobLinkExtractorTest {
    private IndeedMainSiteJobLinkExtractor indeedMainSiteJobLinkExtractor = new IndeedMainSiteJobLinkExtractor();
    private String indeedPath = "TestingIndeed/";

    @Test
    void getLinksFromMainWebsite() throws CustomExceptions {
        TreeSet<String> jobLinks;
        jobLinks = indeedMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(indeedPath + "indeed(MainSite)_getAllLinksFromOneSite.htm");
        for (String s : jobLinks) {
            assertTrue(s.contains("https://www.indeed.com/"));
        }
    }

    @Test
    void getNextMainSite() throws CustomExceptions {
        indeedMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(indeedPath + "indeed(MainSite)_getAllLinksFromOneSite.htm");
        String indeedNextMainSite = "https://www.indeed.com/jobs?q=software+engineer&l=San+Francisco%2C+CA&start=10";
        assertEquals(indeedNextMainSite, indeedMainSiteJobLinkExtractor.getNextMainSite());
    }

    @Test
    void noNextSiteTest() throws CustomExceptions {
        //OnLast page no more next pages
        indeedMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(indeedPath + "indeed_getNextPage_DoesNotHaveANextPage.htm");
        assertEquals("no more pages", indeedMainSiteJobLinkExtractor.getNextMainSite());

        //no pages at all
        indeedMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite(indeedPath + "indeed_getNextPage_DoesNotHaveAnySubpages.htm");
        assertEquals("no more pages", indeedMainSiteJobLinkExtractor.getNextMainSite());
    }
}