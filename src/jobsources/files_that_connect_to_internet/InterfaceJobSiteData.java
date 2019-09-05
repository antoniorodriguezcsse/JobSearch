package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public interface InterfaceJobSiteData {

    void setParsedHTML(Document parsedHTML);

    String getApplyType();

    boolean getJobExpiered();

    boolean getCantFindPage();

    void verifyDivContainers() throws CustomExceptions;

    String getJobTitle() throws CustomExceptions;

    ArrayList<String> getJobDescriptionText() throws CustomExceptions;

    String getDatePosted();
}

