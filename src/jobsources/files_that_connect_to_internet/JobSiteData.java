package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.util.ArrayList;

public class JobSiteData implements Serializable {
    private ArrayList<String> textFromJobDescription = new ArrayList<>();
    private transient Document parsedHTML;
    private InterfaceHTMLExtractor interfaceHtmlExtractor;
    private String applyType;
    private String jobLink = "";
    private String datePosted;
    private String jobTitle;
    private InterfaceJobSiteData dataFromJobSite;

    public String connectToJobSite(String jobLink) throws CustomExceptions {
        setupHTMLExtractor(new HTMLExtractor());

        String result = interfaceHtmlExtractor.connectToWebsite(jobLink);
        if (result.equals("Could not connect to site.")) {
            return result;
        } else {
            parsedHTML = interfaceHtmlExtractor.getParsedHTML();
            setupSiteData(new GlassdoorJobSiteData());

            this.jobLink = jobLink;
            return result;
        }
    }

    private void setupSiteData(InterfaceJobSiteData jobSiteData) throws CustomExceptions {
        jobSiteData.setParsedHTML(parsedHTML);
        dataFromJobSite = jobSiteData;
        applyType = jobSiteData.getApplyType();
        jobTitle = jobSiteData.getJobTitle();
        textFromJobDescription = jobSiteData.getJobDescriptionText();
        datePosted = jobSiteData.getDatePosted();
    }

    public String getApplyType() {

        return applyType;
    }

    public String getJobTitle() {
        if (dataFromJobSite == null) {
            return "Job title has not been set.";
        }
        return dataFromJobSite.getJobTitle();
    }

    ArrayList<String> getTextFromJobDescription() {
        if (textFromJobDescription.isEmpty()) {
            textFromJobDescription.add("Site has not been connected.");
        }
        return textFromJobDescription;
    }

    public String getJobLink() {
        if (jobLink.isEmpty()) {
            return "Site has not been connected.";
        }
        return jobLink;
    }

    public String getDatePosted() {
        return datePosted;
    }

    private void setupHTMLExtractor(HTMLExtractor htmlExtractor) {
        this.interfaceHtmlExtractor = htmlExtractor;
    }

    void setParsedHTML(InterfaceHTMLExtractor interfaceHtmlExtractor) {
        parsedHTML = interfaceHtmlExtractor.getParsedHTML();
    }
}
