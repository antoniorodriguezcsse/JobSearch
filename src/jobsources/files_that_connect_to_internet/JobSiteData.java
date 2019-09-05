package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.util.ArrayList;

public class JobSiteData implements Serializable, InterfaceJobData {
    private ArrayList<String> textFromJobDescription = new ArrayList<>();
    private transient Document parsedHTML;
    private String applyType = "";
    private String jobLink = "";
    private String datePosted = "";
    private String jobTitle = "";

    @Override
    public String connectToJobSite(String jobLink) throws CustomExceptions {
        InterfaceHTMLExtractor interfaceHtmlExtractor = new HTMLExtractor();

        String result = interfaceHtmlExtractor.connectToWebsite(jobLink);
        if (result.equals("Could not connect to site.")) {
            setError("JobSiteData: Could not connect to site:");
            return result;

        } else {

            parsedHTML = interfaceHtmlExtractor.getParsedHTML();

            if (jobLink.contains("glassdoor")) {
                setupSiteData(new GlassdoorJobSiteData());
            } else if (jobLink.contains("indeed")) {
                setupSiteData(new IndeedJobSiteData());
            } else {
                setError("JobSiteData: Not a valid job link");
                //   throw new CustomExceptions("Not a valid job link.");
            }

            this.jobLink = jobLink;
            return result;
        }
    }

    private void setupSiteData(InterfaceJobSiteData jobSiteData) throws CustomExceptions {
        jobSiteData.setParsedHTML(parsedHTML);

        if (jobSiteData.getJobExpiered()) {
            setError("JobSiteData: Job expiered");
        } else if (jobSiteData.getCantFindPage()) {
            setError("JobSiteData: Page can't be found");
        } else {
            jobSiteData.verifyDivContainers();
            applyType = jobSiteData.getApplyType();
            jobTitle = jobSiteData.getJobTitle();
            textFromJobDescription = jobSiteData.getJobDescriptionText();
            datePosted = jobSiteData.getDatePosted();
        }
    }

    private void setError(String error) {
        applyType = error + ": No apply type data.";
        jobTitle = error + ": No job title.";
        textFromJobDescription.add(error + ": No job description.");
        datePosted = error + ": no date posted.";
    }

    void setParsedHTML(InterfaceHTMLExtractor interfaceHtmlExtractor) {
        parsedHTML = interfaceHtmlExtractor.getParsedHTML();
    }

    @Override
    public String getApplyType() {
        if (applyType.isEmpty()) {
            return "Apply type has not been set.";
        }
        return applyType;
    }

    @Override
    public String getJobTitle() {
        if (jobTitle.isEmpty()) {
            return "Job title has not been set.";
        }
        return jobTitle;
    }

    @Override
    public String getJobLink() {
        if (jobLink.isEmpty()) {
            return "Job link has not been set.";
        }
        return jobLink;
    }

    @Override
    public ArrayList<String> getTextFromJobDescription() {
        if (textFromJobDescription.isEmpty()) {
            textFromJobDescription.add("Text from Job Description has not been set.");
        }
        return textFromJobDescription;
    }

    @Override
    public String getDatePosted() {
        if (datePosted.isEmpty()) {
            return "Date posted has not been set.";
        }
        return datePosted;
    }
}
