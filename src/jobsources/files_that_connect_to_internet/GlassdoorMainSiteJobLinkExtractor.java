package jobsources.files_that_connect_to_internet;

import org.jsoup.select.Elements;

public class GlassdoorMainSiteJobLinkExtractor extends MainSiteJobLinkExtractor {

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
