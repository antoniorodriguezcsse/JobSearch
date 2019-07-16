package jobsources.files_that_connect_to_internet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public abstract class AbstractHTMLGrabber {
    Document html = Document.createShell("");
    transient Document parsedHTML;

    String connectToWebsite(String mainSite/* JListAllJobsGUI jListAllJobsGUI*/) {
        Document documentFromFile;
        try {
            if(mainSite.contains("https://")) {
                html = Jsoup.connect(mainSite).timeout(30 * 1000).get();
            }
            else{
                File input = new File(mainSite);
                documentFromFile = Jsoup.parse(input, "UTF-8");
                if (documentDoesNotContainHTML(documentFromFile)) {
                    return "not a valid HTML file.";
                } else {
                    html = documentFromFile;
                }
            }

            parsedHTML = Jsoup.parse(String.valueOf(html));
            return "Connected.";
        } catch (IOException e) {
            return "Could not connect to site.";
        } catch (IllegalArgumentException e) {
            return "Not a valid URL.";
        }
    }

    private boolean documentDoesNotContainHTML(Document doc) {
        return !String.valueOf(doc).toLowerCase().contains("<!doctype html>");
    }
}
