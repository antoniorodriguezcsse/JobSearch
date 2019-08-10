package jobsources.files_that_work_with_job_data;

import jobsources.RegExLookAt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class YearsOfExperienceFilter {
    private RegExLookAt regExLookAt = new RegExLookAt();
    private TreeSet<String> words = new TreeSet<>();
    private TreeSet<String> wordsFromLine = new TreeSet<>();
    private HashMap<String, String> numbers = new HashMap<>();
    //  private String lowerCaseLineOfJobDescription;

    private TreeSet<String> hyphen = new TreeSet<>();

    //work on lines that only contain numbers,
    boolean showJobFilter(String lineFromJobDescription, final int yearsOfExperience) {
        setupNumberHashMap();
        String lowerCaseLineFromDescription = lineFromJobDescription.toLowerCase();
        int numberFromJobDescription = 0;
        if (stringContainsExperienceNumberAndYear(lowerCaseLineFromDescription)) {

            if (lineContainsNumber(lowerCaseLineFromDescription)) {
                if (stringContainsNumberWithHyphenAndSpace(lowerCaseLineFromDescription)) {
                    lineFromJobDescription = removeSpaceBetweenNumbersAndHyphen(lowerCaseLineFromDescription);
                }

                if (findIndexOfFirstNumber(lowerCaseLineFromDescription) > -1)
                    lineFromJobDescription = deleteCharacterBeforeFirstNumberOrLetter(lowerCaseLineFromDescription);

                if (firstCharacterIsADigit(lowerCaseLineFromDescription)) {
                    numberFromJobDescription = extractNumberFromString(lowerCaseLineFromDescription);

                    wordsFromLine.clear();
                    return numberFromJobDescription <= yearsOfExperience;
                } else {
                    for (Map.Entry<String, String> entry : numbers.entrySet()) {
                        if (!wordsFromLine.add(entry.getKey())) {
                            lowerCaseLineFromDescription = lowerCaseLineFromDescription.replace(entry.getKey(), entry.getValue());
                            break;
                        }
                        wordsFromLine.remove(entry.getKey());
                    }

                    numberFromJobDescription = extractNumberFromString(lowerCaseLineFromDescription);
                    wordsFromLine.clear();
                    return numberFromJobDescription <= yearsOfExperience;
                }
            }
        }
        return false;
    }

    private void setupNumberHashMap() {
        String test = "hello";
        numbers.put("one", "1");
        numbers.put("two", "2");
        numbers.put("three", "3");
        numbers.put("four", "4");
        numbers.put("five", "5");
        numbers.put("six", "6");
        numbers.put("seven", "7");
        numbers.put("eight", "8");
        numbers.put("nine", "9");
        numbers.put("ten", "10");
    }

    private String deleteCharacterBeforeFirstNumberOrLetter(String lineFromJobDescription) {
        // int indexOfFirstLetter = findIndexOfFirstLetter(lineFromJobDescription);
        int indedOfFirstNumber = findIndexOfFirstNumber(lineFromJobDescription);

        StringBuilder stringBuilder = new StringBuilder(lineFromJobDescription);
        lineFromJobDescription = stringBuilder.replace(0, indedOfFirstNumber, "").toString();
        return lineFromJobDescription;
    }

    private boolean firstCharacterIsNotADigitOrLetter(String string) {
        return !Character.isDigit(string.charAt(0)) && !Character.isLetter(string.charAt(0));
    }

    private int findIndexOfFirstLetter(String string) {
        Pattern p = Pattern.compile("[a-zA-z]");
        Matcher m = p.matcher(string);
        if (m.find()) {
            return m.start();
        }
        return -1;
    }

    private int findIndexOfFirstNumber(String string) {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(string);
        if (m.find()) {
            return m.start();
        }

        Integer m1 = FindLocationOfWordNumber(string);
        if (m1 != null) return m1;
        return -1;
    }

    private Integer FindLocationOfWordNumber(String string) {
        Pattern p;
        Matcher m;
        String[] numberVariations = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        for (String numberVariation : numberVariations) {
            p = Pattern.compile(numberVariation);
            m = p.matcher(string);
            if (m.find()) {
                return m.start();
            }
        }
        return -1;
    }

    private int extractNumberFromString(String lineFromJobDescription) {
        StringBuilder stringBuilder = new StringBuilder(lineFromJobDescription);
        char firstCharacter = lineFromJobDescription.charAt(0);

        if (Character.isDigit(firstCharacter)) {
            char secondCharacter = lineFromJobDescription.charAt(1);
            if (Character.isDigit(secondCharacter)) {
                stringBuilder.replace(2, stringBuilder.length(), "");
            } else {
                stringBuilder.replace(1, stringBuilder.length(), "");
            }

        } else {
            int indexOfFirstNumber = findIndexOfFirstNumber(lineFromJobDescription);
            stringBuilder.replace(0, indexOfFirstNumber, "");
            char secondCharacter = stringBuilder.charAt(1);

            if (Character.isDigit(secondCharacter)) {
                stringBuilder.replace(2, stringBuilder.length(), "");
            } else {
                stringBuilder.replace(1, stringBuilder.length(), "");
            }
        }
        return Integer.parseInt(stringBuilder.toString());
    }

    private boolean secondCharacterIsADigit(String lineFromJobDescription) {
        char secondCharacter = lineFromJobDescription.charAt(1);
        return Character.isDigit(secondCharacter);
    }

    private boolean firstCharacterIsADigit(String string) {
        return (Character.isDigit(string.charAt(0)));
    }

    private boolean stringContainsNumberWithHyphenAndSpace(String lineFromJobDescription) {
        return regExLookAt.regExPatternMatch(lineFromJobDescription, ".*\\d\\s+-\\s+\\d");
    }

    public boolean stringContainsExperienceNumberAndYear(String string) {
        setWordsInToTreeSets(string.toLowerCase());
        if (!lineContainsExperience()) {
            return false;
        }
        if (!lineContainsNumber(string)) {
            return false;
        }
        return lineContainsYears();
    }

    private boolean lineContainsExperience() {
        String[] experienceVariations = {"exp", "experience", "exp."};
        for (String s : experienceVariations) {
            if (!wordsFromLine.add(s)) {
                return true;
            }
            wordsFromLine.remove(s);
        }

        return false;
    }

    private boolean lineContainsNumber(String string) {
        String[] numberVariations = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        if (string.matches(".*\\d.*")) {
            return true;
        }

        TreeSet<String> checkForNumber = extractWords(string);

        for (String s : numberVariations) {
            if (!checkForNumber.add(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean lineContainsYears() {
        String[] yearVariations = {"year", "years", "yrs", "yrs."};

        for (String s : yearVariations) {
            if (!wordsFromLine.add(s)) {
                return true;
            }
            wordsFromLine.remove(s);
        }
        return false;
    }

    private void setWordsInToTreeSets(String string) {
        wordsFromLine = extractWords(string);
        words.addAll(wordsFromLine);
    }

    private String removeSpaceBetweenNumbersAndHyphen(String string) {
        return string.replaceAll("\\s+-\\s+", "-");
    }

    private TreeSet<String> extractWords(String string) {
        String specialCharactcersRemoved = "";
        TreeSet<String> wordsFromLine = new TreeSet<>();
        specialCharactcersRemoved = string.replaceAll("[^a-zA-Z0-9]", " ");

        String[] splitWords = specialCharactcersRemoved.split("\\s+");
        return new TreeSet<String>(Arrays.asList(splitWords));
    }

    public TreeSet<String> getWords() {
        return words;
    }

    private TreeSet<String> getHyphen() {
        return hyphen;
    }
}
