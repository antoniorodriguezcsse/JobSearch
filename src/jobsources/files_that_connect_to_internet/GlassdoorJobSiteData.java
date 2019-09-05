package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import jobsources.StringTools;
import jobsources.files_that_work_with_job_data.HTMLTagCleaner;
import jobsources.files_that_work_with_job_data.InterfaceHTMLTagCleaner;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GlassdoorJobSiteData implements InterfaceJobSiteData {
    private transient Document parsedHTML;
    private ArrayList<String> textFromJobDescription = new ArrayList<>();

    @Override
    public void setParsedHTML(Document parsedHTML) {
        this.parsedHTML = parsedHTML;
    }

    @Override
    public String getApplyType() {
        return parsedHTML.select("div.regToApplyArrowBoxContainer").text();
    }

    @Override
    public String getJobTitle() throws CustomExceptions {
        return parsedHTML.title();
    }

    @Override
    public ArrayList<String> getJobDescriptionText() throws CustomExceptions {
        setTextFromJobDescription();
        return textFromJobDescription;
    }

    @Override
    public String getDatePosted() {
        Elements applyDiv = parsedHTML.select("div.pageInsideContent");
        StringTools stringTools = new StringTools();
        String datePosted = "";
        for (Element e : applyDiv) {
            datePosted = stringTools.removeEverythingBeforeAndIncludingTerm(String.valueOf(e), "\"datePosted\": \"");
            datePosted = stringTools.removeEverythingAfterAndIncludingTerm(datePosted, "\"");
        }
        if (datePosted.equals("<div class=")) {
            return "can't find site or site is invalid.";
        }

        Date dateObject = null;
        try {
            dateObject = new SimpleDateFormat("yyyy-MM-dd").parse(datePosted);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat output = new SimpleDateFormat("MM-dd-yyyy");
        datePosted = output.format(dateObject);
        return datePosted;
    }

    private void setTextFromJobDescription() throws CustomExceptions {
        Element jobDescriptionElement = null;
        try {
            jobDescriptionElement = parsedHTML.select("div.jobDescriptionContent.desc").get(0);
        } catch (IndexOutOfBoundsException e) {
            textFromJobDescription.add("No job data.");
            return;
        }

        if (jobDescriptionElement.toString().isEmpty()) {
            textFromJobDescription.add("No job data.");
            return;
        }

        textFromJobDescription.clear();
        InterfaceHTMLTagCleaner htmlTagCleaner = new HTMLTagCleaner(jobDescriptionElement.toString());
        textFromJobDescription = htmlTagCleaner.getHtmlText();
    }

    public String getJobID()
    {
        StringTools stringTools = new StringTools();
  //      jobID = stringTools.removeEverythingBeforeAndIncludingTerm(link, "jobListingId=");
        return "blah";
    }

    @Override
    public boolean getJobExpiered() {
        String expiered = parsedHTML.select("div.gdAlertBox.neutralBox").text();
        return expiered.equals("This job has expired, but there are others like it below.");
    }

    @Override
    public boolean getCantFindPage()
    {
        String cantFindPage = parsedHTML.select("div.headline").text();
        return cantFindPage.equals("Sorry, we can't find that page.");
    }

    @Override
    public void verifyDivContainers() throws CustomExceptions {
        if (parsedHTML.select("div.regToApplyArrowBoxContainer").isEmpty()) {
            throw new CustomExceptions("GlassdoorJobSiteData.getApplyType: div.regToApplyArrowBoxContainer can't be found.");
        }

        if (parsedHTML.select("div.pageInsideContent").isEmpty()) {
            throw new CustomExceptions("GlassdoorJobSiteData.getDatePosted: div.pageInsideContent can't be found.");
        }

        if (parsedHTML.select("div.jobDescriptionContent.desc").isEmpty()) {
            throw new CustomExceptions("GlassdoorJobSiteData.setTextJobDescription: div.jobDescriptionContent.desc can't be found.");
        }
    }
}
