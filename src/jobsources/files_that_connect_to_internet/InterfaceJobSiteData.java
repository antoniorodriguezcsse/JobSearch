package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public interface InterfaceJobSiteData {

    void setParsedHTML(Document parsedHTML);

  //  Element getJobDescriptionElements() throws CustomExceptions;

    String getApplyType();

    String getJobTitle();

    ArrayList<String> getJobDescriptionText() throws CustomExceptions;

    String getDatePosted();
}

