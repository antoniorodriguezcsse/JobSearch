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
                allJobLinks.clear();
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
          //  setNextMainSite();
            c.printStackTrace();
            return allJobLinks;
        }
        setNextMainSite();
        return allJobLinks;
    }

    protected void setAllJobLinksFromMainSite() {
        for (Element e : jobContainer) {
            allJobLinks.add(e.attr("href"));
        }
    }

    private void setNextMainSite() {
        if (nextMainSite.isEmpty()) {
            nextMainSite = "no more pages";
        }
    }

    private void setupGlassdoor() throws CustomExceptions {
//        jobContainer = html.select("div.jobContainer").select("a[href]");
//        nextMainSite = html.select("li.next").select("a[href]").attr("href");
        jobContainer = html.select("div.jobContainer").select("a[href]");

        Elements bottomBar = html.select("div.pageNavBar.noMargBot");
        if(bottomBar.isEmpty())
        {
            throw new CustomExceptions("MainSiteJobLinkExtractor: div.pageNavBar.noMargBot can't be found.");


        }
     //   System.out.println("bottomBar: " + bottomBar);

       Elements nextButton = html.select("li.next");
//
//        System.out.println("nextbutton good: " + nextButton.next());
//        System.out.println("nextbutton bad: " + html.select("asdf").text());
//        if(nextButton.isEmpty()){
//            throw  new CustomExceptions("glassdoor li.next cant be found");
//        }

        nextMainSite = nextButton .select("a[href]").attr("href");
//        System.out.println("site good: " + html.select("li.next"));
//      System.out.println("Site: " + html.select("li.basf"));
    }

    private void setupIndeed() {
        jobContainer = html.select("div.title").select("a[href]");

        Elements links = html.select("div.pagination").select("a[href]");
        if (links.last() == null || links.isEmpty() || !links.last().toString().contains("&nbsp;")) {
            nextMainSite = "";
        } else {
            nextMainSite = links.last().attr("href");
        }
    }

    private String connectToMainWebSite(String website) throws CustomExceptions {
        if (!website.contains("https://www.glassdoor.com") && !website.isEmpty() && !website.contains("TestingGlassdoor")
                && !website.contains("https://www.indeed.com") && !website.contains("TestingIndeed")) {
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
