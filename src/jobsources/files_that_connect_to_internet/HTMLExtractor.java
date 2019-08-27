package jobsources.files_that_connect_to_internet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

  class HTMLExtractor implements InterfaceHTMLExtractor {
    private Document html = Document.createShell("");
    private transient Document parsedHTML;

    @Override
    public String connectToWebsite(String mainSite/* JListAllJobsGUI jListAllJobsGUI*/) {
        Document documentFromFile;
        try {
            if (mainSite.contains("https://")) {
                html = Jsoup.connect(mainSite).userAgent("Mozilla").timeout(30000).get();
            } else {
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

    @Override
    public Document getHTML() {
        return html;
    }

     @Override
     public Document getParsedHTML() {
        return parsedHTML;
    }

    private boolean documentDoesNotContainHTML(Document doc) {
        String documentLowerCase = String.valueOf(doc).toLowerCase();
        return !(documentLowerCase.contains("<!doctype html>") || documentLowerCase.contains(" <head>"));
    }
}
