package jobsources.files_that_connect_to_internet;

import org.jsoup.nodes.Element;

public class IndeedMainSIteJobLinkExtractor extends MainSiteJobLinkExtractor {
    @Override
    protected void setAllJobLinksFromMainSite() {
        jobContainer = html.select("div.title").select("a[href]");
        for (Element e : jobContainer) {
            allJobLinks.add("https://www.indeed.com" + e.attr("href"));
        }
    }

}
