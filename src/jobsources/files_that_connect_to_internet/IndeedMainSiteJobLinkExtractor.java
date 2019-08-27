package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.TreeSet;

public class IndeedMainSiteJobLinkExtractor implements InterfaceMainSiteJobLinkExtractor {
    private Elements jobContainer;
    private String errorMessage = "";
    private Elements links;
    private Document html;
    private InterfaceHTMLExtractor interfaceHtmlExtractor;
    @Override
    public TreeSet<String> getAllJobLinksFromOneMainSite(String mainSite) throws CustomExceptions {
        setupHTMLExtractor(new HTMLExtractor());
        try {
            System.out.println("trying to connect to: " + mainSite);
            if (connectToMainWebSite(mainSite).equals("Connected.")) {
                if (!errorMessage.isEmpty()) {
                    throw new CustomExceptions(errorMessage);
                }
                html = interfaceHtmlExtractor.getHTML();
                setupHTMLElements();
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

    private void setupHTMLExtractor(HTMLExtractor htmlExtractor) {
        this.interfaceHtmlExtractor = htmlExtractor;
    }

    @Override
    public TreeSet<String> getAllJobLinksFromSite() {
        return allJobLinks;
    }

    void clearAllJobLinksFromSite() {
        allJobLinks.clear();
    }

    @Override
    public String getNextMainSite() {
        if (links.last() == null || links.isEmpty() || !links.last().toString().contains("&nbsp;")) {
            return "no more pages";
        } else {
            return links.last().attr("href");
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
        jobContainer = html.select("div.title").select("a[href]");
        links = html.select("div.pagination").select("a[href]");
    }

    private String connectToMainWebSite(String website) throws CustomExceptions {
        if (!website.contains("https://www.indeed.com") && !website.contains("TestingIndeed")) {
            errorMessage = "MainSiteJobLinkExtractor.connectToMainWebSite: Not a valid link.";
        }

        String status = interfaceHtmlExtractor.connectToWebsite(website);
        if (status.equals("Could not connect to site.") || status.equals("Not a valid URL.")) {
            errorMessage = "MainSiteJobLinkExtractor.connectToMainWebSite: " + status;
        }

        return status;
    }
}
