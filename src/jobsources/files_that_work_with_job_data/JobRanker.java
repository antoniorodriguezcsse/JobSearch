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
    private ArrayList<String> goodKeyWords = new ArrayList<>();
    private ArrayList<String> jobDescriptionText = new ArrayList<>();
    private Integer applyTypePoints = 0;
    private Integer jobRank = 0;
    private Integer goodWordTotalPoints = 0;
    private Integer badWordTotalPoints = 0;
    private Integer numberOfDaysPostedPoints = 0;
    private ArrayList<String> badKeyWords = new ArrayList<>();
    private ArrayList<String> wordsInBodyAndDescription = new ArrayList<>();

    public JobRanker(String applyType, ArrayList<String> jobDescriptionText, Integer numberOfDaysPosted) {
        // jobData = new JobData(jobSite);
        this.applyType = applyType;
        this.jobDescriptionText = jobDescriptionText;
        this.numberOfDaysPosted = numberOfDaysPosted;

        FileRead goodWordsFile = new FileRead();
        goodKeyWords = goodWordsFile.getLinesFromFile("good-keywords.txt");

        FileRead badWordFile = new FileRead();
        badKeyWords = badWordFile.getLinesFromFile("bad-keywords.txt");

        setApplyTypePoints();
        setWordsinBodyAndDescription();
        setGoodWordTotalPoints();
        setBadWordTotalPoints();
        setNumberOfDaysPostedPoints();
        setJobRank();
    }

    private void setJobRank() {
        jobRank = (goodWordTotalPoints * 25) + applyTypePoints - badWordTotalPoints - numberOfDaysPostedPoints;
    }

    public Integer getJobRank() {
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
            s = s.replace(",", "");
            s = s.replace(".", "");
            s = s.replace(":", "");
            s = s.replace("(", "");
            s = s.replace(")", "");
            s = s.replace("/", " ");
            buffer = s.split(" ");
            wordsInBodyAndDescription.addAll(Arrays.asList(buffer));
        }
    }

    private void setGoodWordTotalPoints() {
        Map<String, Integer> goodWordAndOccurances = getMapOfWords(goodKeyWords);
        Set<Map.Entry<String, Integer>> st = goodWordAndOccurances.entrySet();
        for (Map.Entry<String, Integer> me : st) {
//            System.out.print(me.getKey() + ":");
//            System.out.println(me.getValue());
            goodWordTotalPoints = goodWordTotalPoints + me.getValue();
        }
    }

    private void setBadWordTotalPoints() {
        //  System.out.println("bad words");
        Map<String, Integer> badWordAndOccurances = getMapOfWords(badKeyWords);
        Set<Map.Entry<String, Integer>> st = badWordAndOccurances.entrySet();
        for (Map.Entry<String, Integer> me : st) {
//            System.out.print("bad words: " + me.getKey() + ":");
//            System.out.println(me.getValue());

            badWordTotalPoints = badWordTotalPoints + me.getValue();
        }
    }

    private Map<String, Integer> getMapOfWords(ArrayList<String> wordList) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : wordList) {
            for (String wordInBodyAndDescription : wordsInBodyAndDescription) {
                if (word.toLowerCase().equals(wordInBodyAndDescription.toLowerCase())) {
                    map = addToHashMap(map, word);
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
}
