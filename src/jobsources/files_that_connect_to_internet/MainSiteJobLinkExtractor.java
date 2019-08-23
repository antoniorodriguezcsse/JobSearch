package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.TreeSet;

public class MainSiteJobLinkExtractor extends AbstractHTMLGrabber {
    private TreeSet<String> allJobLinks = new TreeSet<>();
    private CompanyHTMLElements companyHTMLElements = new CompanyHTMLElements();

    private String nextMainSite;
    private Elements jobContainer;
    private String site;
    private String errorMessage = "";

    TreeSet<String> getAllJobLinksFromSite() {
        return allJobLinks;
    }

    void clearAllJobLinksFromSite() {
        allJobLinks.clear();
    }

    public String getNextMainSite() {
        return companyHTMLElements.getNextMainSite();
    }

    public TreeSet<String> getAllJobLinksFromOneMainSite(String mainSite) throws CustomExceptions {
        TreeSet<String> listOfJobLinksAndNextMainSite = new TreeSet<>();

        try {
            System.out.println("trying to connect to: " + mainSite);
            if (connectToMainWebSite(mainSite).equals("Connected.")) {
                allJobLinks.clear();
                companyHTMLElements.validateHTMLElements(mainSite, html);
                setAllJobLinksFromMainSite();
            } else {
                if (!errorMessage.isEmpty()) {
                    throw new CustomExceptions(errorMessage);
                }
                return allJobLinks;
            }

            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    //    setNextMainSite();
        return allJobLinks;
    }

    private void setAllJobLinksFromMainSite() {
        for (Element e : companyHTMLElements.getJobContainer()) {
            allJobLinks.add(e.attr("href"));
        }
    }

    private String connectToMainWebSite(String website) throws CustomExceptions {
        if (!website.contains("https://www.glassdoor.com") && !website.isEmpty() && !website.contains("TestingGlassdoor")
                && !website.contains("https://www.indeed.com") && !website.contains("TestingIndeed")) {
            errorMessage = "MainSiteJobLinkExtractor.connectToMainWebSite: Not a valid link.";
        }

        String status = connectToWebsite(website);
        if (status.equals("Could not connect to site.") || status.equals("Not a valid URL.")) {
            errorMessage = "MainSiteJobLinkExtractor.connectToMainWebSite: " + status;
        }

        return status;
    }
}
