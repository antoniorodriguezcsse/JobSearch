package jobsources.files_that_connect_to_internet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.Serializable;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

abstract class AbstractJobSiteData implements Serializable {

    private ArrayList<String> allText = new ArrayList<>();
    private transient Document parsedHTML;

    void setAllText(Element jobDescription) {
        if (jobDescription.toString().isEmpty()) {
            allText.add("No job data.");
            return;
        }

        allText.clear();
        String textFromJob = String.valueOf(jobDescription);
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
            allText.addAll(extractSentences(iterator, splitLine.trim()));
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


    void setParsedHTML(HTMLGrabber HTMLGrabber) {
        parsedHTML = HTMLGrabber.getParsedHTML();
    }

    public String getHTMLTitle() {
        if (parsedHTML == null) {
            return "Job title has not been set.";
        }
        return parsedHTML.title();
    }

//    public Document getParsedHTML() {
//        return parsL;
//    }

    public ArrayList<String> getAllText() {
        if (allText.isEmpty()) {
            allText.add("Site has not been connected.");
        }
        return allText;
    }

    abstract public String getJobLink();
}
