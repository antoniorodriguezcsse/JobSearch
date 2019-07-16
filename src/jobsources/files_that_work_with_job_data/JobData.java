package jobsources.files_that_work_with_job_data;

import jobsources.RegExLookAt;
import jobsources.StringTools;
import jobsources.files_that_connect_to_internet.JobSiteData;
import jobsources.read_write_to_files.FileRead;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JobData implements Serializable {
    private JobRanker jobRanker;
    private String jobLink = "";
    private String jobTitle = "";
    private String applyType = "";
    private String datePosted = "";
    private Integer numberOfDaysPosted = 0;
    private JobSiteData jobSiteData;
    private StringTools stringTools;
    private Integer rank = 0;
    private ArrayList<String> dontShowKeywords = new ArrayList<>();
    private ArrayList<String> goodKeywords = new ArrayList<>();
    private ArrayList<String> linesWithGoodKeywords = new ArrayList<>();
    private ArrayList<String> jobDescriptionText = new ArrayList<>();
    private Boolean dontShowJob = false;
    private RegExLookAt regExLookAt = new RegExLookAt();


    public JobData(String link) {
        jobSiteData = new JobSiteData();
        stringTools = new StringTools();

        String connectionResult = jobSiteData.connectToJobSite(link);
        if (connectionResult.equals("Connected.")) {
            jobLink = jobSiteData.getJobLink();
            setJobTitle();
            jobDescriptionText = jobSiteData.getAllText();
            setDatePosted();
            if (jobDescriptionText.get(0).equals("Job has expired.")) {
                applyType = "Apply Type: Job expired, no data.";
                jobTitle = "JobTitle: Job expired, no data.";
                numberOfDaysPosted = 0;

            } else {
                setApplyType();
                setNumberOfDaysPosted();
                jobRanker = new JobRanker(getApplyType(), jobDescriptionText, numberOfDaysPosted);
                rank = jobRanker.getJobRank();

                FileRead dontShowKeywordsFile = new FileRead();
                dontShowKeywords = dontShowKeywordsFile.getLinesFromFile("dont-show-keywords.txt");
                //FileRead("dont-show-keywords.txt");
                //  dontShowKeywords = dontShowKeywordsFile.getParsedLinesFromFile();

                FileRead goodKeywordFile = new FileRead();
                goodKeywords = goodKeywordFile.getLinesFromFile("good-keywords.txt");

                for (String linesFromJobDescription : jobDescriptionText) {
                    setdontShowJob(linesFromJobDescription);
                    setLinesWithGoodKeywords(linesFromJobDescription);
                }

            }

//        setLinesWithGoodKeywords();
        } else {
            jobLink = "JobLink: Not connected to site.";
            applyType = "ApplyType: Not connected to site.";
            jobTitle = "JobTitle: Not connected to site.";
        }

    }

    private void setdontShowJob(String lineFromJobDescription) {
        if (dontShowJob) {
            return;
        }
        String lowerCaseAndSpacesRemoved = lineFromJobDescription.toLowerCase();
        lowerCaseAndSpacesRemoved = lowerCaseAndSpacesRemoved.replace(" ", "");

        String[] split = lowerCaseAndSpacesRemoved.split("(?m)^[^\\d][a-zA-Z\\W]*");
        String stringWithLeadingNumber = "";
        for (String s : split) {
            if (s.isBlank()) {
                continue;
            }
            stringWithLeadingNumber = s;
        }
        if (stringWithLeadingNumber.isBlank()) {
            return;
        }
        boolean dontShowJobMatch;
        dontShowJobMatch = regExLookAt.regExPatternMatch(stringWithLeadingNumber, "^[1-9][t0+y-][oey+\\d][\\daye][yrea][rseao]");
        if (dontShowJobMatch) {
            dontShowJob = true;
        }
    }

    public Boolean dontShowJob() {
        return dontShowJob;
    }

    private void setLinesWithGoodKeywords(String lineFromJobDescription) {
        String regex = "[/\\s,()-]|\\.\\s";
        ArrayList<String> words = new ArrayList<String>(Arrays.asList(lineFromJobDescription.split(regex)));

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

    private void setNumberOfDaysPosted() {
        String todaysDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        numberOfDaysPosted = Integer.valueOf(getDifferenceBetweenDates(todaysDate, getDatePosted(), "yyyy-MM-dd"));
    }

    public String getDifferenceBetweenDates(String finalDate, String initialDate, String format) {
       if(initialDate.equals("<div class="))
       {
           return "can't find site or site is invalid.";
       }

       if(initialDate.equals("can't find site or site is invalid."))
       {
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

    Integer getNumberOfDaysPosted() {
        return numberOfDaysPosted;
    }

    private void setDatePosted()
    {
        Elements applyDiv = jobSiteData.getParsedHTML().select("div.pageInsideContent");

        String datePosted = "";
        for (Element e : applyDiv) {
            datePosted = stringTools.removeEverythingBeforeAndIncludingTerm(String.valueOf(e), "\"datePosted\": \"");
            datePosted = stringTools.removeEverythingAfterAndIncludingTerm(datePosted, "\"");
        }
        if(datePosted.equals("<div class="))
        {
            this.datePosted = "can't find site or site is invalid.";
            return;
        }

        this.datePosted = datePosted;
    }

    public String getDatePosted() {
     return datePosted;
    }

    public ArrayList<String> getBulletPoints() {
        if (jobSiteData.getBulletPoints().isEmpty()) {
            ArrayList<String> noDescription = new ArrayList<>();
            noDescription.add("Description was empty.");
            return noDescription;
        }
        return jobSiteData.getBulletPoints();
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
        jobTitle = jobSiteData.getHTMLTitle();
    }

    private void setApplyType() {
        Elements applyDiv = jobSiteData.getParsedHTML().select("div.regToApplyArrowBoxContainer");
        applyType = applyDiv.text();
    }

    public String getApplyType() {
        return applyType;
    }

    public ArrayList<String> getJobDescriptionText() {
        return jobDescriptionText;
    }
}
