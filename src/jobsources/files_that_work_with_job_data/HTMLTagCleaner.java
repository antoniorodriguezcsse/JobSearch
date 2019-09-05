package jobsources.files_that_work_with_job_data;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

public class HTMLTagCleaner implements InterfaceHTMLTagCleaner {
    private ArrayList<String> htmlText = new ArrayList<>();
    ;

    public HTMLTagCleaner(String html) {
        html = removeDivClassDivAndNewLines(html);

        String[] splitLines = splitByEndTagsAndBreaksExeceptem(html);
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        for (String splitLine : splitLines) {
            if (splitLine.isBlank()) {
                continue;
            }
            splitLine = removeTagsAndCleanUpString(splitLine);
            if (splitLine.contains("’")) {
                splitLine = splitLine.replaceAll("’", "'");
            }
            htmlText.addAll(extractSentences(iterator, splitLine.trim()));
        }

    }

    @Override
    public ArrayList<String> getHtmlText() {
        return htmlText;
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
