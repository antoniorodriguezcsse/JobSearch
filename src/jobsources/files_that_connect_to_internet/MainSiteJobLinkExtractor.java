package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.TreeSet;

public class MainSiteJobLinkExtractor extends AbstractHTMLGrabber {
    TreeSet<String> allJobLinks = new TreeSet<>();

  private String nextMainSite;
    Elements jobContainer;
    private String site;

    TreeSet<String> getAllJobLinksFromSite() {
        return allJobLinks;
    }

    void clearAllJobLinksFromSite() {
        allJobLinks.clear();
    }

    public String getNextMainSite() {
        return nextMainSite;
    }

    public TreeSet<String> getAllJobLinksFromOneMainSite(String mainSite) throws CustomExceptions {
        TreeSet<String> listOfJobLinksAndNextMainSite = new TreeSet<>();
        try {
            System.out.println("trying to connect to: " + mainSite);
            if (connectToMainWebSite(mainSite).equals("Connected.")) {
                if (mainSite.contains("glassdoor")) {
                    setupGlassdoor();
                }
                if (mainSite.contains("indeed")) {
                    setupIndeed();
                }
                setAllJobLinksFromMainSite();
            } else {
                throw new CustomExceptions("GlassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite: Connection error.");
            }

            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CustomExceptions c) {
            setNextMainSite();
            return allJobLinks;
        }
        setNextMainSite();
        return allJobLinks;
    }

    protected void setAllJobLinksFromMainSite() {
        for (Element e : jobContainer) {
            allJobLinks.add(site + e.attr("href"));
        }
    }

    private void setNextMainSite() {
        if(nextMainSite.isEmpty()){
            nextMainSite = "no more pages";
            return;
        }

        nextMainSite = site +  nextMainSite;
    }

    private void setupGlassdoor() {
        jobContainer = html.select("div.jobContainer").select("a[href]");
        site = "https://www.glassdoor.com";
        nextMainSite = html.select("li.next").select("a[href]").attr("href");
    }

    private void setupIndeed() {
        jobContainer = html.select("div.title").select("a[href]");
        site = "https://www.indeed.com";

        Elements links = html.select("div.pagination").select("a[href]");
        if (links.last() == null || links.isEmpty() || !links.last().toString().contains("&nbsp;")) {
            nextMainSite = "";
        }
        else {
            nextMainSite = links.last().attr("href");
        }
    }


    private String connectToMainWebSite(String website) throws CustomExceptions {
        if (!website.contains("https://www.glassdoor.com") && !website.isEmpty() && !website.contains("websiteTest") && !website.contains("https://www.indeed.com")) {
            throw new CustomExceptions("GlassdoorMainSiteJobLinkExtractor.connectToMainWebSite: Not a glassdoor link.");
        }

        String status = connectToWebsite(website);
        if (status.equals("Could not connect to site.") || status.equals("Not a valid URL.")) {
            throw new CustomExceptions("MainSiteJobLinkExtractor.connectToMainWebSite: " + status);
        }

//        if (validateJobContainer().equals("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: div.jobContainer can't be found.")) {
//            throw new CustomExceptions("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: div.jobContainer can't be found.");
//        }
//
//        if (validateJobLinkContainer().equals("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: a.jobLink.jobInfoItem.jobTitle can't be found.")) {
//            throw new CustomExceptions("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: a.jobLink.jobInfoItem.jobTitle can't be found.");
//        }

        return status;
    }


}
