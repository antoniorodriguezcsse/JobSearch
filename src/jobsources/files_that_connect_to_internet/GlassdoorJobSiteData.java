package jobsources.files_that_connect_to_internet;

import jobsources.CustomExceptions;
import jobsources.StringTools;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

class GlassdoorJobSiteData implements InterfaceJobSiteData {
    private transient Document parsedHTML;
    private ArrayList<String> textFromJobDescription = new ArrayList<>();
    private Element jobDescriptionElement;

    @Override
    public void setParsedHTML(Document parsedHTML){
        this.parsedHTML = parsedHTML;
    }

    @Override
    public String getApplyType() {
        return parsedHTML.select("div.regToApplyArrowBoxContainer").text();
    }

    @Override
    public String getJobTitle() {
        if (parsedHTML == null) {
            return "Job title has not been set.";
        }
        return parsedHTML.title();
    }

    @Override
    public ArrayList<String> getJobDescriptionText() throws CustomExceptions {
        setTextFromJobDescription();
        return textFromJobDescription;
    }

    @Override
    public String getDatePosted() {
        Elements applyDiv = parsedHTML.select("div.pageInsideContent");
        StringTools stringTools = new StringTools();
        String datePosted = "";
        for (Element e : applyDiv) {
            datePosted = stringTools.removeEverythingBeforeAndIncludingTerm(String.valueOf(e), "\"datePosted\": \"");
            datePosted = stringTools.removeEverythingAfterAndIncludingTerm(datePosted, "\"");
        }
        if (datePosted.equals("<div class=")) {
            return "can't find site or site is invalid.";
        }

        return datePosted;
    }

    private void setJobDescriptionElements() throws CustomExceptions {
        try {
            jobDescriptionElement = parsedHTML.select("div.jobDescriptionContent.desc").get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new CustomExceptions("GlassdoorJobSiteData.setJobDescriptionElement: div.jobDescriptionContent.desc can't be found.");
        }
    }

    private void setTextFromJobDescription() throws CustomExceptions {
        setJobDescriptionElements();
        if (jobDescriptionElement.toString().isEmpty()) {
            textFromJobDescription.add("No job data.");
            return;
        }

        textFromJobDescription.clear();
        String textFromJob = String.valueOf(jobDescriptionElement);
        textFromJob = removeDivClassDivAndNewLines(textFromJob);

        String[] splitLines = splitByEndTagsAndBreaksExeceptem(textFromJob);
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        for (String splitLine : splitLines) {
            if (splitLine.isBlank()) {
                continue;
            }
            splitLine = removeTagsAndCleanUpString(splitLine);
            if (splitLine.contains("’")) {
                splitLine = splitLine.replaceAll("’", "'");
            }
            textFromJobDescription.addAll(extractSentences(iterator, splitLine.trim()));
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
        return textFromJob.split("<br>|</li>|<ul>|</div>");
    }

    private ArrayList<String> extractSentences(BreakIterator bi, String source) {
        int counter = 0;
        bi.setText(source);
        ArrayList<String> linesFromJobDescription = new ArrayList<>();
        int lastIndex = bi.first();
        while (lastIndex != BreakIterator.DONE) {
            int firstIndex = lastIndex;
            lastIndex = bi.next();

            if (lastIndex != BreakIterator.DONE) {
                String sentence = source.substring(firstIndex, lastIndex);
                linesFromJobDescription.add(removeTagsAndCleanUpString(sentence));
            }
        }
        return linesFromJobDescription;
    }

    private String removeDivClassDivAndNewLines(String textFromJob) {
        textFromJob = textFromJob.replace("<div class=\"jobDescriptionContent desc\">", "");
        textFromJob = textFromJob.replace("<div>", "");
        textFromJob = textFromJob.replace("\n", "");
        return textFromJob;
    }

}
