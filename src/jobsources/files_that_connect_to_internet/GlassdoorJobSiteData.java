package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import jobsources.StringTools;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GlassdoorJobSiteData extends AbstractJobSiteData {
    private String jobLink = "";
    private Element jobDescription;
    private Document parsedHTML;
    private String applyType;
    private String datePosted;
    private HTMLGrabber HTMLGrabber;

    public String connectToJobSite(String jobLink) throws CustomExceptions {
        HTMLGrabber = new HTMLGrabber();
        String result = HTMLGrabber.connectToWebsite(jobLink);
        if (result.equals("Could not connect to site.")) {
            return result;
        } else {
            setParsedHTML(HTMLGrabber);
            parsedHTML = HTMLGrabber.getParsedHTML();
            setJobDescriptionElements();
            setAllText(jobDescription);
            setApplyType();
            setDatePosted();
            this.jobLink = jobLink;
            return result;
        }
    }

    @Override
    public String getJobLink() {
        if(jobLink.isEmpty())
        {
            return "Site has not been connected.";
        }
        return jobLink;
    }

    public String getApplyType() {
        return applyType;
    }

    private void setApplyType() {
        Elements applyDiv = HTMLGrabber.getParsedHTML().select("div.regToApplyArrowBoxContainer");
        applyType = applyDiv.text();
    }

    public String getDatePosted() {
        return datePosted;
    }

    private void setDatePosted() {
        Elements applyDiv = HTMLGrabber.getParsedHTML().select("div.pageInsideContent");
        StringTools stringTools = new StringTools();
        String datePosted = "";
        for (Element e : applyDiv) {
            datePosted = stringTools.removeEverythingBeforeAndIncludingTerm(String.valueOf(e), "\"datePosted\": \"");
            datePosted = stringTools.removeEverythingAfterAndIncludingTerm(datePosted, "\"");
        }
        if (datePosted.equals("<div class=")) {
            this.datePosted = "can't find site or site is invalid.";
            return;
        }

        this.datePosted = datePosted;
    }

    private void setJobDescriptionElements() throws CustomExceptions {
        try {
            jobDescription = parsedHTML.select("div.jobDescriptionContent.desc").get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new CustomExceptions("GlassdoorJobSiteData.setJobDescriptionElement: div.jobDescriptionContent.desc can't be found.");
        }
    }




}
