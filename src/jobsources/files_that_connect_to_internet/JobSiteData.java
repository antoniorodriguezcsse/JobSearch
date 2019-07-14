package jobsources.files_that_connect_to_internet;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

 public class JobSiteData extends AbstractJobSiteData {

    public String connectToJobSite(String jobLink) {
        allText = new ArrayList<>();
        String result = connectToWebsite(jobLink);
        if (result.equals("Could not connect to site.")) {
            return result;
        } else {
            setAllText();
            setJobLink();
            return result;
        }
    }

    @Override
    public ArrayList<String> getBulletPoints() {
        if (parsedHTML == null) {
            bulletPoints.add("Site has not been connected.");
            return bulletPoints;
        }
        if (bulletPoints.size() > 0) {
            bulletPoints.clear();
        }

        Elements jobBodyElements = parsedHTML.select("div.jobDescriptionContent.desc");
        Elements list = jobBodyElements.select("li");

        for (Element element : list) {
            bulletPoints.add(element.text());
        }

        if (bulletPoints.isEmpty()) {
            bulletPoints.add("No bullet points found.");
        }
        return bulletPoints;
    }

    @Override
    void setAllText() {

        if(parsedHTML.select("div.jobDescriptionContent.desc").isEmpty())
        {
            allText.add("Job has expired.");
            return;
        }

        Element jobBodyElement = parsedHTML.select("div.jobDescriptionContent.desc").get(0);//.select("div").get(0).childNodes().get(1).childNodes();
        String textFromJob = String.valueOf(jobBodyElement);
        textFromJob = removeDivClassDivAndNewLines(textFromJob);

        String[] splitLines = splitByEndTagsAndBreaksExeceptem(textFromJob);
        for (String splitLine : splitLines) {
            if (splitLine.isBlank()) {
                continue;
            }
            splitLine = removeTagsAndCleanUpString(splitLine);
            if (splitLine.contains("’")) {
                splitLine = splitLine.replaceAll("’", "'");
            }

            allText.add(splitLine.trim());
        }
    }

    private String removeTagsAndCleanUpString(String s) {
        s = s.replaceAll("amp;", "");
        s = s.replaceAll("&nbsp;", "");
        s = s.replaceAll("<[^>]*>", "");
        s = s.trim();
        s = s.replace("  ", " ");
        return s;
    }

    private String[] splitByEndTagsAndBreaksExeceptem(String textFromJob) {
        return textFromJob.split("<br>|<\\/li>|<ul>|<\\/div>");
    }

    private String removeDivClassDivAndNewLines(String textFromJob) {
        textFromJob = textFromJob.replace("<div class=\"jobDescriptionContent desc\">", "");
        textFromJob = textFromJob.replace("<div>", "");
        textFromJob = textFromJob.replace("\n", "");
        return textFromJob;
    }

    @Override
    void setJobLink() {
        Elements links = parsedHTML.getElementsByTag("link");
        String link;

        for (Element element : links) {
            link = String.valueOf(element.attr("abs:href"));
            if (link.contains("/job-listing")) {
                jobLink = link;
                break;
            }
        }
    }
}
