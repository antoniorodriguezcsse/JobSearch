package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class IndeedJobSiteData extends AbstractJobSiteData {
    private String jobLink;
    private Element jobDescription;
    private Document parsedHTML;

    public String connectToJobSite(String jobLink) throws CustomExceptions {
        HTMLGrabber HTMLGrabber = new HTMLGrabber();
        String result = HTMLGrabber.connectToWebsite(jobLink);
        if (result.equals("Could not connect to site.")) {
            return result;
        } else {
            setParsedHTML(HTMLGrabber);
            parsedHTML = HTMLGrabber.getParsedHTML();
            setJobDescriptionElements();
            setAllText(jobDescription);
            this.jobLink = jobLink;
            return result;
        }
    }

    private void setJobDescriptionElements() throws CustomExceptions {
        try {
            jobDescription = parsedHTML.select("div.jobsearch-jobDescriptionText").get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new CustomExceptions("IndeedJobSiteData.setJobDescriptionElement: div.jobSearch-jobDescriptionText can't be found.");
        }
    }

    @Override
    public String getJobLink(){
        return jobLink;
    }

}
