package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class CompanyHTMLElements {
    private Document html;
    private String errorMessage = "";
    private String nextMainSite = "";

    void validateHTMLElements(String link, Document html) throws CustomExceptions {
        this.html = html;

        if (link.contains("glassdoor")) {
            verifyGlassDoor();
            if (!errorMessage.isEmpty()) {
                throw new CustomExceptions(errorMessage);
            } else {
                if (nextMainSite.isEmpty()) {
                    setNextMainSite();
                }
            }
        }

        if (link.contains("indeed")) {
            Elements jobContainer = html.select("div.title").select("a[href]");
            Elements links = html.select("div.pagination").select("a[href]");

            if (links.last() == null || links.isEmpty() || !links.last().toString().contains("&nbsp;")) {
                nextMainSite = "";
            } else {
                nextMainSite = links.last().attr("href");
            }
        }
    }


    private void verifyGlassDoor() {
        Elements jobContainer = html.select("div.jobContainer").select("a[href]");
        if (jobContainer.isEmpty()) {
            errorMessage = "CompanyHTMLElements: div.jobContainer can't be found.";
            return;
        }

        Elements barWithNumberOfPages = html.select("div.cell.middle.hideMob.padVertSm");
        if (barWithNumberOfPages.text().equals("Page 1 of 1")) {
            nextMainSite = "no more pages";
            return;
        }

        if (barWithNumberOfPages.isEmpty()) {
            errorMessage = "CompanyHTMLElements: div.cell.middle.hideMob.padVertSm.";
            return;
        }

        Elements pageNavigationBarBottom = html.select("div.pageNavBar.noMargBot");
        if (pageNavigationBarBottom.isEmpty()) {
            errorMessage = "CompanyHTMLElements: div.pageNavBar.noMargBot can't be found.";
            return;
        }

        Elements nextButton = html.select("li.next");
        if (nextButton.isEmpty()) {
            errorMessage = "CompanyHTMLElements: li.next can't be found.";
        }

        Elements pagingControlMiddle = pageNavigationBarBottom.select("div.pagingControls.cell.middle");
        if (pagingControlMiddle.isEmpty()) {
            errorMessage = "CompanyHTMLElements: div.pagingControls.cell.middle can't be found.";
        }
    }

    private void setNextMainSite() {
        Elements nextButton = html.select("li.next");
        nextMainSite = nextButton.select("a[href]").attr("href");
    }

    String getNextMainSite() {
        if (nextMainSite.isEmpty()) {
            return "no more pages";
        }
        return nextMainSite;
    }

    Elements getJobContainer() {
        return html.select("div.jobContainer").select("a[href]");
    }
}


