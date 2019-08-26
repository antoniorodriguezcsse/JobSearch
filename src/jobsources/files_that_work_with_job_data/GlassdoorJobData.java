package jobsources.files_that_work_with_job_data;

import jobsources.CustomExceptions;
import jobsources.RegExLookAt;
import jobsources.StringTools;
import jobsources.files_that_connect_to_internet.GlassdoorJobSiteData;
import jobsources.read_write_to_files.FileRead;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GlassdoorJobData extends GlassdoorJobSiteData implements Serializable {
    private String jobLink = "";
    private String jobTitle = "";
    private String applyType = "";
    private String datePosted = "";
    private String rejected = "";
    private String jobID = "";
    private Integer numberOfDaysPosted = 0;
    private GlassdoorJobSiteData glassdoorJobSiteData;
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

    public GlassdoorJobData(String link)  throws CustomExceptions {
        setJobID(link);
        glassdoorJobSiteData = new GlassdoorJobSiteData();
        stringTools = new StringTools();

        String connectionResult = glassdoorJobSiteData.connectToJobSite(link);
        if (connectionResult.equals("Connected.")) {
            jobLink = glassdoorJobSiteData.getJobLink();
            setJobTitle();
            jobDescriptionText = glassdoorJobSiteData.getAllText();
           // setDatePosted();

            if (jobDescriptionText.get(0).equals("Job has expired.")) {
                applyType = "Apply Type: Job expired, no data.";
                jobTitle = "JobTitle: Job expired, no data.";
                numberOfDaysPosted = 0;

            } else {
              //  setApplyType();
                setNumberOfDaysPosted();
                JobRanker jobRanker = new JobRanker(getApplyType(), jobDescriptionText, numberOfDaysPosted);
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
            jobTitle = "JobTitle: Not connected to site.";
        }
    }

    
    public void setMeetsCriteria(GlassdoorJobData glassdoorJobData) {
        meetsCriteria = showJobsCriteria.meetsCriteria(glassdoorJobData);
    }

    
    public void setCriteriaRejection() {
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

    
    public void setJobID(String link) {
        StringTools stringTools = new StringTools();
        jobID = stringTools.removeEverythingBeforeAndIncludingTerm(link, "jobListingId=");
    }

    
    public void setDontShowJobFromLine(String lineFromJobDescription) {
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

    
    public void experienceFilter(String lineFromJobDescription) {
        if (yearsOfExperienceFilter.stringContainsExperienceNumberAndYear(lineFromJobDescription)) {
            if (yearsOfExperienceFilter.showJobFilter(lineFromJobDescription, 1)) {
                return;
            }

            rejected = "REJECTED Experience: " + lineFromJobDescription;
            dontShowJob = true;
        }
    }

    
    public void dontShowKeywordsFilter(String lineFromJobDescription) {
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

//    
//    public boolean jobContainsYearsAndExperience(String stringWithLeadingNumber) {
//        boolean dontShowJobMatch;
//        stringWithLeadingNumber = stringWithLeadingNumber.replaceAll("^[\\d][.][)]", "");
//        dontShowJobMatch = regExLookAt.regExPatternMatch(stringWithLeadingNumber, "^[1-9][t0+y-][oey+\\d][\\daye][yrea][rseao]");
//
//        if (dontShowJobMatch) {
//            return true;
//        }
//
//        if (stringWithLeadingNumber.contains("exp")) {
//            return true;
//        }
//
//        return stringWithLeadingNumber.contains("years") || stringWithLeadingNumber.contains("yrs");
//    }

    
    public void setDontShowJobFromLine(boolean dontShowJob) {
        this.dontShowJob = dontShowJob;
    }

    
    public Boolean dontShowJob() {
        return dontShowJob;
    }

    
    public void setLinesWithGoodKeywords(String lineFromJobDescription) {
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

    
    public ArrayList<String> getLinesWithGoodKeywords() {
        return linesWithGoodKeywords;
    }

    
    public Integer getRank() {
        return rank;
    }

    
    public void setNumberOfDaysPosted() {
        String todaysDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        numberOfDaysPosted = Integer.valueOf(getDifferenceBetweenDates(todaysDate, getDatePosted(), "yyyy-MM-dd"));
    }

    
    public String getDifferenceBetweenDates(String finalDate, String initialDate, String format) {
        if (initialDate.equals("<div class=")) {
            return "can't find site or site is invalid.";
        }

        if (initialDate.equals("can't find site or site is invalid.")) {
            return "can't find site or site is invalid.";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = dateFormat.parse(finalDate);
            date2 = dateFormat.parse(initialDate);
        } catch (ParseException e) {

            return "invalid date.";
            //   e.printStackTrace();
        }

        if (date1 == null || date2 == null) {
            return "date is null.";
        }
        long differenceInMilliseconds = date1.getTime() - date2.getTime();
        return String.valueOf(Math.toIntExact(TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS)));
    }

    
    public Integer getNumberOfDaysPosted() {
        return numberOfDaysPosted;
    }



    
    public String getDatePosted() {
        return datePosted;
    }

//    ArrayList<String> getBulletPoints() {
//        if (jobSiteData.getBulletPoints().isEmpty()) {
//            ArrayList<String> noDescription = new ArrayList<>();
//            noDescription.add("Description was empty.");
//            return noDescription;
//        }
//        return jobSiteData.getBulletPoints();
//    }

    
    public String getJobLink() {
        if (jobLink == null) {
            return "job link can't be found.";
        }
        return jobLink;
    }

    
    public String getJobTitle() {
        return jobTitle;
    }

    
    public void setJobTitle() {
        jobTitle = glassdoorJobSiteData.getHTMLTitle();
    }


    
    public String getApplyType() {
        return applyType;
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
