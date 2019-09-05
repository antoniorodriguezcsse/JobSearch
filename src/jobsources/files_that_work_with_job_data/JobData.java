package jobsources.files_that_work_with_job_data;

import jobsources.CustomExceptions;
import jobsources.RegExLookAt;
import jobsources.StringTools;
import jobsources.files_that_connect_to_internet.InterfaceJobData;
import jobsources.files_that_connect_to_internet.JobSiteData;
import jobsources.read_write_to_files.FileRead;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TreeSet;

public class JobData implements Serializable {
    private String jobLink = "";
    private String jobTitle = "";
    private String applyType = "";
    private String datePosted = "";
    private String rejected = "";
    private String jobID = "";
    private Integer numberOfDaysPosted = 0;
    // private JobSiteData glassdoorJobSiteData;
    private StringTools stringTools;
    private Integer rank = 0;
    private TreeSet<String> dontShowKeywords;
    private ArrayList<String> goodKeywords = new ArrayList<>();
    private ArrayList<String> linesWithGoodKeywords = new ArrayList<>();
    private ArrayList<String> jobDescriptionText = new ArrayList<>();
    private boolean dontShowJob = false;
    private boolean meetsCriteria = false;
    private RegExLookAt regExLookAt = new RegExLookAt();
    private YearsOfExperienceFilter yearsOfExperienceFilter = new YearsOfExperienceFilter();
    private ShowJobsCriteria showJobsCriteria = new ShowJobsCriteria();
    private boolean applied = false;
    private InterfaceJobData client;

    public JobData(String link) throws CustomExceptions {
        setJobID(link);

        setupClient(new JobSiteData());
        stringTools = new StringTools();

        String connectionResult = client.connectToJobSite(link);
        if (connectionResult.equals("Connected.")) {
            jobLink = client.getJobLink();
            setJobTitle();
            jobDescriptionText = client.getTextFromJobDescription();
            if (jobDescriptionText.get(0).equals("Job expiered: No job description.")) {
                applyType = "Apply Type: Job expired, no data.";
                jobTitle = "JobTitle: Job expired, no data.";
                numberOfDaysPosted = -1;

            }

            else if(jobDescriptionText.get(0).equals("JobSiteData: Page can't be found: No job description.")){
                applyType = "Apply Type: Can't find page, no data.";
                jobTitle = "JobTitle: Can't find page, no data.";
                numberOfDaysPosted = -1;
                linesWithGoodKeywords.add("Can't find page. no data.");
            }
            else {
                setNumberOfDaysPosted();
                InterfaceJobRanker jobRanker = new JobRanker(getApplyType(), jobDescriptionText, numberOfDaysPosted);
                rank = jobRanker.getJobRank();

                FileRead dontShowKeywordsFile = new FileRead();
                dontShowKeywords = new TreeSet<>(dontShowKeywordsFile.getLinesFromFile("dont-show-keywords.txt"));
                dontShowKeywords.removeIf(String::isEmpty);
                dontShowKeywords.removeIf(String::isBlank);

                FileRead goodKeywordFile = new FileRead();
                goodKeywords = goodKeywordFile.getLinesFromFile("good-keywords.txt");
                goodKeywords.removeIf(String::isEmpty);
                goodKeywords.removeIf(String::isBlank);

                for (String linesFromJobDescription : jobDescriptionText) {
                    if (dontShowJob) {
                        continue;
                    }
                    setDontShowJobFromLine(linesFromJobDescription);
                    setLinesWithGoodKeywords(linesFromJobDescription);
                }

                setMeetsCriteria(this);
                setCriteriaRejection();
            }

        } else {
            jobLink = "JobLink: Not connected to site.";
            applyType = "ApplyType: Not connected to site.";
            jobTitle = client.getJobTitle();
        }
    }

    private void setupClient(InterfaceJobData jobSiteData) {
        client = jobSiteData;
    }

    private void setMeetsCriteria(JobData jobData) {
        meetsCriteria = showJobsCriteria.meetsCriteria(jobData);
    }

    private void setCriteriaRejection() {
        if (!showJobsCriteria.getRejected().isEmpty()) {
            rejected = showJobsCriteria.getRejected();
        }
    }

    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
        setMeetsCriteria(this);
    }

    public boolean getMeetsCriteria() {
        return meetsCriteria;
    }

    private void setJobID(String link) {
        StringTools stringTools = new StringTools();
        jobID = stringTools.removeEverythingBeforeAndIncludingTerm(link, "jobListingId=");
    }

    private void setDontShowJobFromLine(String lineFromJobDescription) {
        if (dontShowJob) {
            return;
        }

        dontShowKeywordsFilter(lineFromJobDescription);
        experienceFilter(lineFromJobDescription);
    }

    public void setDontShowJob(boolean dontShow) {
        this.dontShowJob = dontShow;
        setMeetsCriteria(this);
    }

    private void experienceFilter(String lineFromJobDescription) {
        if (yearsOfExperienceFilter.stringContainsExperienceNumberAndYear(lineFromJobDescription)) {
            if (yearsOfExperienceFilter.showJobFilter(lineFromJobDescription, 1)) {
                return;
            }

            rejected = "REJECTED Experience: " + lineFromJobDescription;
            dontShowJob = true;
        }
    }

    private void dontShowKeywordsFilter(String lineFromJobDescription) {
        for (String s : dontShowKeywords) {
            if (lineFromJobDescription.contains(s)) {
                rejected = "REJECTED: description contains: " + s;
                dontShowJob = true;
                return;
            }
            if (jobTitle.contains(s)) {
                rejected = "REJECTED: Job title contains: " + s;
                dontShowJob = true;
                return;
            }
        }
    }

    public void setDontShowJobFromLine(boolean dontShowJob) {
        this.dontShowJob = dontShowJob;
    }

    Boolean dontShowJob() {
        return dontShowJob;
    }

    private void setLinesWithGoodKeywords(String lineFromJobDescription) {
        String regex = "[/\\s,()-]|\\.\\s";
        ArrayList<String> words = new ArrayList<>(Arrays.asList(lineFromJobDescription.split(regex)));

        for (String goodKeyword : goodKeywords) {
            for (String word : words) {
                if (word.toLowerCase().equals(goodKeyword.toLowerCase())) {
                    linesWithGoodKeywords.add(lineFromJobDescription);
                }
            }
        }
    }

    ArrayList<String> getLinesWithGoodKeywords() {
        return linesWithGoodKeywords;
    }

    public Integer getRank() {
        return rank;
    }

    public void setNumberOfDaysPosted() {
        if(getDatePosted().equals("JobSiteData: Page can't be found: no date posted.") || getDatePosted().equals("JobSiteData: Job expiered: no date posted."))
        {
            numberOfDaysPosted = -1;
            return;
        }
        String todaysDate = new SimpleDateFormat("MM-dd-yyyy").format(Calendar.getInstance().getTime());
        numberOfDaysPosted = Integer.valueOf(getDifferenceBetweenDatesInDays(todaysDate, getDatePosted(), "MM-dd-yyyy"));
    }

    String getDifferenceBetweenDatesInDays(String finalDate, String initialDate, String format) {
        if (initialDate.equals("<div class=")) {
            return "can't find site or site is invalid.";
        }

        if (initialDate.equals("can't find site or site is invalid.")) {
            return "can't find site or site is invalid.";
        }

        InterfaceDateTools differenceBetweenTwoDates = new DateTools();
        return differenceBetweenTwoDates.getDifferenceBetweenDatesInDays(finalDate, initialDate, format);
    }

    Integer getNumberOfDaysPosted() {
        return numberOfDaysPosted;
    }

    public String getDatePosted() {
        return client.getDatePosted();
    }

    public String getJobLink() {
        if (jobLink == null) {
            return "job link can't be found.";
        }
        return jobLink;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    private void setJobTitle() {
        jobTitle = client.getJobTitle();
    }

    public String getApplyType() {
        return client.getApplyType();
    }

    public ArrayList<String> getJobDescriptionText() {
        return jobDescriptionText;
    }

    public String getRejectedString() {
        return rejected;
    }

   public String getJobID() {
        return jobID;
    }
}
