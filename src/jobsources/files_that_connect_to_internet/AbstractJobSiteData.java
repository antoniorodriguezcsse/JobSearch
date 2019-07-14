package jobsources.files_that_connect_to_internet;

import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.util.ArrayList;

abstract class AbstractJobSiteData extends AbstractHTMLGrabber implements Serializable  {

   // transient Document parsedHTML;
    private transient Document html;
    String bodyAndDescriptionText = "";
    final ArrayList<String> bulletPoints = new ArrayList<>();
    ArrayList<String> allText = new ArrayList<>();
    String jobLink;

    abstract public ArrayList<String> getBulletPoints();
    abstract void setAllText();
    abstract void setJobLink();

    public String getHTMLTitle() {
        if(parsedHTML == null)
        {
            return "Job title has not been set.";
        }
        return parsedHTML.title();
    }

    public Document getParsedHTML() {
        return parsedHTML;
    }

    public ArrayList<String> getAllText() {
        if(allText.isEmpty())
        {
            allText.add("Site had not been connected.");
        }
        return allText;
    }

    public String getJobLink() {
        if(jobLink == null)
        {
            return "Site had not been connected.";
        }
        return jobLink;
    }
}
