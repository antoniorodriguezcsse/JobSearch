package jobsources.files_that_work_with_job_data;

import jobsources.StringTools;
import jobsources.read_write_to_files.FileRead;

import java.io.Serializable;
import java.util.*;

public class JobRanker implements Serializable {

    private String applyType = "";
    private Integer numberOfDaysPosted = 0;
    private JobData jobData;
    private ArrayList<String> linesOfDescription = new ArrayList<>();
    private ArrayList<String> jobDescriptionText = new ArrayList<>();
    private Integer applyTypePoints = 0;
    private Integer jobRank = 0;
    private Integer goodWordTotalPoints = 0;
    private Integer badWordTotalPoints = 0;
    private Integer numberOfDaysPostedPoints = 0;
    private TreeSet<String> badKeyWords;
    private TreeSet<String> goodKeyWords;
    private TreeSet<String> wordsInBodyAndDescription;

    public JobRanker(String applyType, ArrayList<String> jobDescriptionText, Integer numberOfDaysPosted) {
        this.applyType = applyType;
        this.jobDescriptionText = jobDescriptionText;
        this.numberOfDaysPosted = numberOfDaysPosted;

        FileRead goodWordsFile = new FileRead();
        goodKeyWords = lowercaseTreeSet(new TreeSet<>(goodWordsFile.getLinesFromFile("good-keywords.txt")));
        goodKeyWords.removeIf(String::isEmpty);
        goodKeyWords.removeIf(String::isBlank);

        FileRead badWordFile = new FileRead();
        badKeyWords = lowercaseTreeSet(new TreeSet<>(badWordFile.getLinesFromFile("bad-keywords.txt")));
        badKeyWords.removeIf(String::isEmpty);
        badKeyWords.removeIf(String::isBlank);

        setApplyTypePoints();
        if (!jobDescriptionText.isEmpty()) {
            setWordsinBodyAndDescription();
            setGoodWordTotalPoints();
            setBadWordTotalPoints();
        }

        setNumberOfDaysPostedPoints();
        setJobRank();
    }

    private void setJobRank() {
        jobRank = (goodWordTotalPoints * 25) + applyTypePoints - (badWordTotalPoints * 5) - numberOfDaysPostedPoints;
    }

    Integer getJobRank() {
        return jobRank;
    }

    private void setNumberOfDaysPostedPoints() {
        StringTools stringTools = new StringTools();
        numberOfDaysPostedPoints = numberOfDaysPosted;
    }

    private void setApplyTypePoints() {
        if (applyType.equals("Easy Apply")) {
            applyTypePoints = 50;
        }
    }

    private void setWordsinBodyAndDescription() {
        String[] buffer;
        for (String s : jobDescriptionText) {
            s = s.toLowerCase();
            s = s.replace(",", "");
            s = s.replace(".", "");
            s = s.replace(":", "");
            s = s.replace("(", "");
            s = s.replace(")", "");
            s = s.replace("/", " ");
            buffer = s.split(" ");
            wordsInBodyAndDescription = new TreeSet<>(Arrays.asList(buffer));
        }
    }

    private void setGoodWordTotalPoints() {
        Map<String, Integer> goodWordAndOccurances = getMapOfWords(goodKeyWords);
        for (Map.Entry<String, Integer> entry : goodWordAndOccurances.entrySet()) {
            goodWordTotalPoints = goodWordTotalPoints + entry.getValue();
        }
    }

    private void setBadWordTotalPoints() {
        Map<String, Integer> badWordAndOccurances = getMapOfWords(badKeyWords);
        for (Map.Entry<String, Integer> entry : badWordAndOccurances.entrySet()) {
            badWordTotalPoints = badWordTotalPoints + entry.getValue();
        }
    }

    private Map<String, Integer> getMapOfWords(TreeSet<String> wordList) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : wordList) {
            for (String wordInBodyAndDescription : wordsInBodyAndDescription) {
                if (word.toLowerCase().equals(wordInBodyAndDescription)) {
                    addToHashMap(map, word);
                }
            }
        }
        return map;
    }

    private Map<String, Integer> addToHashMap(Map<String, Integer> map, String word) {
        if (map.isEmpty()) {
            map.put(word, 1);
        } else {
            Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
            Boolean wordIsInMap = false;
            // Iterate over the HashMap
            while (iterator.hasNext()) {
                // Get the entry at this iteration
                Map.Entry<String, Integer> entry = iterator.next();

                // Check if this key is the required key
                if (entry.getKey().equals(word)) {
                    wordIsInMap = true;
                }

                if (wordIsInMap) {
                    map.put(word, entry.getValue() + 1);
                    break;
                }
            }
            if (!wordIsInMap) {
                map.put(word, 1);
            }
        }
        return map;
    }

    public static TreeSet<String> lowercaseTreeSet(TreeSet<String> strings) {
        TreeSet<String> buffer = new TreeSet<>();
        for (String string : strings)
            buffer.add(string.toLowerCase());
        return buffer;
    }
}
