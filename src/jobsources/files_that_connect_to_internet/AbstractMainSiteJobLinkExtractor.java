package jobsources.files_that_connect_to_internet;

import java.util.ArrayList;

public abstract class AbstractMainSiteJobLinkExtractor extends AbstractHTMLGrabber {
    ArrayList<String> allJobLinks = new ArrayList<String>();
    String nextMainSite;

     ArrayList<String> getAllJobLinksFromSite() {
        return allJobLinks;
    }

    protected void clearAllJobLinksFromSite()
    {
        allJobLinks.clear();
    }

    public String getNextMainSite() {
        return nextMainSite;
    }

    abstract protected void setAllJobLinksFromMainSite();
    abstract protected void setNextMainSite();
}
