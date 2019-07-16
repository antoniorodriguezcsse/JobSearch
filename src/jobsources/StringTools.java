package jobsources;

import java.io.Serializable;

public class StringTools implements Serializable {

    public String removeEverythingBefore(String phrase, String term) {
        if (phrase.contains(term)) {
            String s1 = phrase.substring(phrase.indexOf(term));
            return s1.trim();
        } else {
            return phrase;
        }
    }

    public String removeEverythingBeforeAndIncludingTerm(String phrase, String term) {
        if(phrase == null)
        {
            return "phrase is null.";
        }
        if (phrase.contains(term)) {
            String s1 = phrase.substring(phrase.indexOf(term) + term.length());
            return s1.trim();
        } else {
            return phrase;
        }
    }

    public String removeEverythingAfter(String phrase, String term) {
        if (phrase.contains(term)) {
            StringBuilder myString = new StringBuilder(phrase);
            myString.delete(phrase.indexOf(term) + term.length(), phrase.length());
            return String.valueOf(myString);
        } else {
            return phrase;
        }
    }

    public String removeEverythingAfterAndIncludingTerm(String phrase, String term) {
        if (phrase.contains(term)) {
            StringBuilder myString = new StringBuilder(phrase);
            myString.delete(phrase.indexOf(term), phrase.length());
            return String.valueOf(myString);
        } else {
            return phrase;
        }
    }
}
