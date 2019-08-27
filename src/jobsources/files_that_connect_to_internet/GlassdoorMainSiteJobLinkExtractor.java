package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.TreeSet;

public class GlassdoorMainSiteJobLinkExtractor implements InterfaceMainSiteJobLinkExtractor {
   // private TreeSet<String> allJobLinks = new TreeSet<>();
    private Elements jobContainer;
    private Elements barWithNumberOfPages;
    private Elements nextButton;
    private Elements pageNavigationBarBottom;
    private Elements pagingControlMiddle;
    private Document html;
    private String nextMainSite;
    private String site;
    private String errorMessage = "";
    private InterfaceHTMLExtractor interfaceHtmlExtractor;

    @Override
    public TreeSet<String> getAllJobLinksFromOneMainSite(String mainSite) throws CustomExceptions {
        setupHTMLExtractor(new HTMLExtractor());

        try {
            System.out.println("trying to connect to: " + mainSite);
            if (connectToMainWebSite(mainSite).equals("Connected.")) {
                html = interfaceHtmlExtractor.getHTML();
                setupHTMLElements();
                verifyGlassdoorElements();
                if (!errorMessage.isEmpty()) {
                    throw new CustomExceptions(errorMessage);
                }
                allJobLinks.clear();
                setAllJobLinksFromMainSite();
            } else {
                throw new CustomExceptions(errorMessage);
            }

            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //setNextMainSite();
        return allJobLinks;
    }
    @Override
    public TreeSet<String> getAllJobLinksFromSite() {
        return allJobLinks;
    }



    @Override
    public String getNextMainSite() {
        nextMainSite = nextButton.select("a[href]").attr("href");
        if (nextMainSite.isEmpty()) {
            return "no more pages";
        } else {
            return nextMainSite;
        }
    }

    @Override
    public void setAllJobLinksFromMainSite() {
        for (Element e : jobContainer) {
            allJobLinks.add(e.attr("href"));
        }
    }

    @Override
    public void setupHTMLElements() {
        jobContainer = html.select("div.jobContainer").select("a[href]");
        barWithNumberOfPages = html.select("div.cell.middle.hideMob.padVertSm");
        nextButton = html.select("li.next");
        pageNavigationBarBottom = html.select("div.pageNavBar.noMargBot");
        pagingControlMiddle = pageNavigationBarBottom.select("div.pagingControls.cell.middle");
        nextButton = html.select("li.next");
    }


    void clearAllJobLinksFromSite() {
        allJobLinks.clear();
    }

    private void setupHTMLExtractor(InterfaceHTMLExtractor interfaceHtmlExtractor) {
        this.interfaceHtmlExtractor = interfaceHtmlExtractor;
    }

    private void verifyGlassdoorElements() {
        if (jobContainer.isEmpty()) {
            errorMessage = "GlassdoorMainSiteJobLinkExtractor: div.jobContainer can't be found.";
            return;
        }

        if (barWithNumberOfPages.text().equals("Page 1 of 1")) {
            nextMainSite = "no more pages";
            return;
        }

        if (barWithNumberOfPages.isEmpty()) {
            errorMessage = "GlassdoorMainSiteJobLinkExtractor: div.cell.middle.hideMob.padVertSm.";
            return;
        }

        if (pageNavigationBarBottom.isEmpty()) {
            errorMessage = "GlassdoorMainSiteJobLinkExtractor: div.pageNavBar.noMargBot can't be found.";
            return;
        }


        if (nextButton.isEmpty()) {
            errorMessage = "GlassdoorMainSiteJobLinkExtractor: li.next can't be found.";
        }

        if (pagingControlMiddle.isEmpty()) {
            errorMessage = "GlassdoorMainSiteJobLinkExtractor: div.pagingControls.cell.middle can't be found.";
        }
    }

    private String connectToMainWebSite(String website) throws CustomExceptions {
        if (!website.contains("https://www.glassdoor.com") && !website.isEmpty() && !website.contains("TestingGlassdoor")) {
            errorMessage = "MainSiteJobLinkExtractor.connectToMainWebSite: Not a valid link.";
        }

        String status = interfaceHtmlExtractor.connectToWebsite(website);
        if (status.equals("Could not connect to site.") || status.equals("Not a valid URL.")) {
            errorMessage = "MainSiteJobLinkExtractor.connectToMainWebSite: " + status;
        }

        return status;
    }

}
