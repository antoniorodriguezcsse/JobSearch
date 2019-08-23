package jobsources.files_that_connect_to_internet;


import jobsources.CustomExceptions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CompanyHTMLElementsTest {
    private String glassDoorPath = "TestingGlassdoor/";

    @Test
    void getJobContainer() throws IOException, CustomExceptions {
        String mainSite = glassDoorPath + "glassdoor(MainSite)_getAllLinksFromOneSite.htm";
        Document html = getHTML(mainSite);

        CompanyHTMLElements companyHTMLElements = new CompanyHTMLElements();
    //    assertTrue(companyHTMLElements.validateHTMLElements(mainSite, html));
    }

    @Test
    void verifyGlassdoor_cantFindJobContainer() throws IOException, CustomExceptions {
        String mainSite = glassDoorPath + "glassdoor(MainSite)_cantFindDivJobContainer.htm";
        Document html = getHTML(mainSite);

        CompanyHTMLElements companyHTMLElements = new CompanyHTMLElements();

        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {
                companyHTMLElements.validateHTMLElements(mainSite, html);
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: CompanyHTMLElements: div.jobContainer can't be found.")) {
                    throw e;
                }
            }
        });
    }

    @Test
    void verifyGlassdoor_cantFindPaginControls() throws IOException, CustomExceptions {
        String mainSite = glassDoorPath + "glassdoor(MainSite)_cantFindPagingControls.htm";
        Document html = getHTML(mainSite);

        CompanyHTMLElements companyHTMLElements = new CompanyHTMLElements();

        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {

                companyHTMLElements.validateHTMLElements(mainSite, html);
            } catch (CustomExceptions e) {
                System.out.println("Error message: " + e);
                if (e.toString().equals("jobsources.CustomExceptions: CompanyHTMLElements: div.pagingControls.cell.middle can't be found.")) {
                    throw e;
                }
            }
        });
    }

    @Test
    void verifyGlassdoor_cantFindPageNavigationBarBottoms() throws IOException, CustomExceptions {
        String mainSite = glassDoorPath + "glassdoor(MainSite)_cantFindPageNavigationBar.htm";
        Document html = getHTML(mainSite);

        CompanyHTMLElements companyHTMLElements = new CompanyHTMLElements();

        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {

                companyHTMLElements.validateHTMLElements(mainSite, html);
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: CompanyHTMLElements: div.pageNavBar.noMargBot can't be found.")) {
                    throw e;
                }
            }
        });
    }

    @Test
    void verifyGlassdoor_cantFindDivCellMiddleHighMob() throws IOException, CustomExceptions {
        String mainSite = glassDoorPath + "glassdoor(MainSite)_cantFindCellMidHighMob.htm";
        Document html = getHTML(mainSite);

        CompanyHTMLElements companyHTMLElements = new CompanyHTMLElements();

        assertThrows(jobsources.CustomExceptions.class, () -> {
            try {

                companyHTMLElements.validateHTMLElements(mainSite, html);
            } catch (CustomExceptions e) {
                if (e.toString().equals("jobsources.CustomExceptions: CompanyHTMLElements: div.cell.middle.hideMob.padVertSm.")) {
                    throw e;
                }
            }
        });
    }

    Document getHTML(String mainSite) throws IOException {
        File input = new File(mainSite);
        return Jsoup.parse(input, "UTF-8");
    }

}