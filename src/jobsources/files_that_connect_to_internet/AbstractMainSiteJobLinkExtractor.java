package jobsources.files_that_connect_to_internet;

import java.util.ArrayList;

abstract class AbstractMainSiteJobLinkExtractor extends AbstractHTMLGrabber {
    ArrayList<String> allJobLinks = new ArrayList<>();
    String nextMainSite;

    ArrayList<String> getAllJobLinksFromSite() {
        return allJobLinks;
    }

    void clearAllJobLinksFromSite() {
        allJobLinks.clear();
    }

    String getNextMainSite() {
        return nextMainSite;
    }

    abstract protected void setAllJobLinksFromMainSite();

    abstract protected void setNextMainSite();
}
