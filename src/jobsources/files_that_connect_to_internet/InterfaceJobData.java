package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;

import java.util.ArrayList;

public interface InterfaceJobData {
    String connectToJobSite(String jobLink) throws CustomExceptions;

    String getApplyType();

    String getJobTitle();

    String getJobLink();

    ArrayList<String> getTextFromJobDescription();

    String getDatePosted();
}
