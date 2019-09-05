package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import jobsources.StringTools;
import jobsources.files_that_work_with_job_data.DateTools;
import jobsources.files_that_work_with_job_data.HTMLTagCleaner;
import jobsources.files_that_work_with_job_data.InterfaceDateTools;
import jobsources.files_that_work_with_job_data.InterfaceHTMLTagCleaner;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

//
//import jobsources.CustomExceptions;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//
class IndeedJobSiteData implements InterfaceJobSiteData {
    private transient Document parsedHTML;
    private ArrayList<String> textFromJobDescription = new ArrayList<>();
    private Element jobDescriptionElement;
    private String datePosted = "";


    @Override
    public void setParsedHTML(Document parsedHTML) {
        this.parsedHTML = parsedHTML;
    }

    @Override
    public String getDatePosted() {
        setDatePosted();
        return datePosted;
    }

    @Override
    public String getApplyType() {
        Elements applyOnSite = parsedHTML.select("div.icl-u-xs-hide.icl-u-lg-block.icl-u-lg-textCenter");
        if (applyOnSite.toString().contains("Apply On Company Site")) {
            return "Apply On Company Site";
        }

        return "Easy Apply";
    }

    @Override
    public boolean getJobExpiered() {
        return false;
    }

    @Override
    public boolean getCantFindPage() {
        String message = "Not found The requested URL was not found on this server. If you need assistance you can contact us or visit our home page";
        return parsedHTML.select("table.system_msg").text().equals(message);
    }

    @Override
    public String getJobTitle() throws CustomExceptions {
        String jobTitle = jobTitleContainer().text();
        StringTools stringTools = new StringTools();
        jobTitle = stringTools.removeEverythingAfterAndIncludingTerm(jobTitle, " Apply");
        return jobTitle;
    }

    private Elements jobTitleContainer() throws CustomExceptions {
        return parsedHTML.select("div.jobsearch-DesktopStickyContainer");
    }

    @Override
    public ArrayList<String> getJobDescriptionText() throws CustomExceptions {
        setTextFromJobDescription();
        return textFromJobDescription;
    }

    private void setTextFromJobDescription() throws CustomExceptions {
        jobDescriptionElement = parsedHTML.select("div.jobsearch-jobDescriptionText").get(0);
        if (jobDescriptionElement.toString().isEmpty()) {
            textFromJobDescription.add("No job data.");
            return;
        }

        textFromJobDescription.clear();
        InterfaceHTMLTagCleaner htmlTagCleaner = new HTMLTagCleaner(jobDescriptionElement.toString());
        textFromJobDescription = htmlTagCleaner.getHtmlText();
    }

    private void setJobDescriptionElements() throws CustomExceptions {
        try {
            jobDescriptionElement = parsedHTML.select("div.jobsearch-jobDescriptionText").get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new CustomExceptions("IndeedJobSiteData.setJobDescriptionElement: div.jobSearch-jobDescriptionText can't be found.");
        }
    }

    private void setDatePosted() {
        Elements element = parsedHTML.select("div.jobsearch-JobMetadataFooter");

        StringTools stringTools = new StringTools();
        String numberOfDays = stringTools.removeEverythingBeforeAndIncludingTerm(element.toString(), "Glassdoor</span> - ");

        if (numberOfDays.contains("jobsearch-JobMetadataFooter")) {
            numberOfDays = stringTools.removeEverythingBeforeAndIncludingTerm(numberOfDays, " - ");
        }

        if (numberOfDays.contains("Just posted") || numberOfDays.contains("Today")) {
            numberOfDays = "0";
        }

        numberOfDays = stringTools.removeEverythingAfterAndIncludingTerm(numberOfDays, "+");
        numberOfDays = stringTools.removeEverythingAfterAndIncludingTerm(numberOfDays, " day");

        InterfaceDateTools differenceBetweenTwoDates = new DateTools();
        datePosted = differenceBetweenTwoDates.getDateMinusDays(numberOfDays, "MM-dd-yyyy");
    }

    @Override
    public void verifyDivContainers() throws CustomExceptions {
        if (parsedHTML.select("div.jobsearch-DesktopStickyContainer").toString().isEmpty()) {
            throw new CustomExceptions("IndeedJobSiteData.jobTitleContainer: div.jobsearch-DesktopStickyContainer can't be found.");
        }

        if (parsedHTML.select("div.jobsearch-jobDescriptionText").isEmpty()) {
            throw new CustomExceptions("IndeedJobSiteData.setTextFromJobDescription: div.jobsearch-jobDescriptionText can't be found.");
        }

        if (parsedHTML.select("div.jobsearch-JobMetadataFooter").isEmpty()) {
            throw new CustomExceptions("IndeedJobSiteData.setDatePosted: div.jobsearch-JobMetadataFooter can't be found.");
        }

        if (parsedHTML.select("div.icl-u-xs-hide.icl-u-lg-block.icl-u-lg-textCenter").isEmpty()) {
            throw new CustomExceptions("IndeedJobSiteData.getApplyType: div.icl-u-xs-hide.icl-u-lg-block.icl-u-lg-textCenter can't be found.");
        }
    }
}
