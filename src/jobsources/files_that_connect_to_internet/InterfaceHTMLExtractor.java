package jobsources.files_that_connect_to_internet;

import org.jsoup.nodes.Document;

public interface InterfaceHTMLExtractor {
    String connectToWebsite(String mainSite/* JListAllJobsGUI jListAllJobsGUI*/);

    Document getHTML();

    Document getParsedHTML();
}
