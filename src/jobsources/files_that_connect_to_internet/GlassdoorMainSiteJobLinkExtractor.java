package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlassdoorMainSiteJobLinkExtractor extends AbstractMainSiteJobLinkExtractor {
    private Integer numberOfLinksToGet = 0;

    public HashMap<ArrayList<String>, String> getAllJobLinksFromOneMainSite(String mainSite) throws CustomExceptions {
        HashMap<ArrayList<String>,String> listOfJobLinksAndNextMainSite = new HashMap<>();
        try {
            System.out.println("trying to connect to: " + mainSite);
            if (connectToMainWebSite(mainSite).equals("Connected.")) {
                setAllJobLinksFromMainSite();
            } else {
                throw new CustomExceptions("GlassdoorMainSiteJobLinkExtractor.getAllJobLinksFromOneMainSite: Connection error.");
            }

            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CustomExceptions c) {
            setNextMainSite();
            listOfJobLinksAndNextMainSite.put(allJobLinks,getNextMainSite());
            return listOfJobLinksAndNextMainSite;
        }
        setNextMainSite();
        listOfJobLinksAndNextMainSite.put(allJobLinks,getNextMainSite());
        return listOfJobLinksAndNextMainSite;
    }

    public ArrayList<String> getAllJobLinksFromAllMainSites(String numberOfSites, String mainSite) throws CustomExceptions {
        String nextMainSite = mainSite;
        boolean numberOfSitesIsNumber = isNumberOfSitesANumber(numberOfSites);
        ArrayList<String> jobSites = new ArrayList<>();

        do {
            try {
                if (connectToMainWebSite(nextMainSite).equals("Connected.")) {
                    setAllJobLinksFromMainSite();
                } else {
                    throw new CustomExceptions("GlassdoorMainSiteJobLinkExtractor.getAllJobLinksFromAllMainSites: Connection error.");
                }

                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (CustomExceptions c) {
                return jobSites;
            }

            jobSites.addAll(getAllJobLinksFromSite());
            clearAllJobLinksFromSite();

            if (numberOfSitesIsNumber) {
                if (jobSites.size() > Integer.parseInt(numberOfSites)) {
                    jobSites.subList(Integer.parseInt(numberOfSites), jobSites.size()).clear();
                    return jobSites;
                }
            }

            System.out.println(allJobLinks.size());

            setNextMainSite();
            nextMainSite = getNextMainSite();
        } while (!nextMainSite.equals("no more pages"));

        return jobSites;
    }

    private boolean isNumberOfSitesANumber(String numberOfSites) {
        boolean numberOfSitesIsNumber = false;
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(numberOfSites);

        if (matcher.lookingAt()) {
            numberOfSitesIsNumber = true;
        }
        return numberOfSitesIsNumber;
    }

    @Override
    protected void setAllJobLinksFromMainSite() {
        Elements jobContainer = html.select("div.jobContainer");
        Elements list = jobContainer.select("a.jobLink.jobInfoItem.jobTitle");
        Set<String> noDuplicateJobLinks = new HashSet<>();

        String jobLink = "";
        for (Element element : list) {

            if (!element.attr("href").contains("https")) {
                jobLink = "https://www.glassdoor.com" + element.attr("href");
            } else {
                jobLink = element.attr("href");
                System.out.println("link: " + jobLink);
            }

            noDuplicateJobLinks.add(jobLink);
        }
        allJobLinks.addAll(noDuplicateJobLinks);
    }

    @Override
    protected void setNextMainSite() {
        String pageThatsDisabled = html.select("li.next").select("span.disabled").text();

        if (pageThatsDisabled.equals("Next") || html.select("li.next").isEmpty()) {
            nextMainSite = "no more pages";
            return;
        }

        nextMainSite = html.select("li.next").select("a[href]").attr("href");
        //websiteTest is part of the path of the local file. Thus if it's not a local file go to the website.
        if (!nextMainSite.contains("websiteTest")) {
            nextMainSite = "https://www.glassdoor.com" + nextMainSite;
        }
    }


    private String connectToMainWebSite(String website) throws CustomExceptions {
        if (!website.contains("https://www.glassdoor.com") && !website.isEmpty() && !website.contains("websiteTest")) {
            throw new CustomExceptions("GlassdoorMainSiteJobLinkExtractor.connectToMainWebSite: Not a glassdoor link.");
        }

        String status = connectToWebsite(website);
        if (status.equals("Could not connect to site.") || status.equals("Not a valid URL.")) {
            throw new CustomExceptions("GlassdoorMainSiteJobLinkExtractor.connectToMainWebSite: " + status);
        }

        if (validateJobContainer().equals("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: div.jobContainer can't be found.")) {
            throw new CustomExceptions("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: div.jobContainer can't be found.");
        }

        if (validateJobLinkContainer().equals("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: a.jobLink.jobInfoItem.jobTitle can't be found.")) {
            throw new CustomExceptions("GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: a.jobLink.jobInfoItem.jobTitle can't be found.");
        }

        return status;
    }

    private String validateJobContainer() {
        if (!divJobContainer()) {
            return "GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: div.jobContainer can't be found.";
        }
        return "GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: div.jobContainer found.";
    }

    private Boolean divJobContainer() {
        return html.select("div.jobContainer").size() != 0;
    }

    private String validateJobLinkContainer() {
        if (!divJobLinkContainer()) {
            return "GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: a.jobLink.jobInfoItem.jobTitle can't be found.";
        }
        return "GlassdoorMainSiteJobLinkExtractor.setAllJobLinksFromMainSite: a.jobLink.jobInfoItem.jobTitle found.";
    }


    private Boolean divJobLinkContainer() {
        Elements jobContainer = html.select("div.jobContainer");
        Elements list = jobContainer.select("a.jobLink.jobInfoItem.jobTitle");
        return list.size() != 0;
    }
}
