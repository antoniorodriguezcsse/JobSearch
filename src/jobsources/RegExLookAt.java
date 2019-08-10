package jobsources;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExLookAt implements Serializable {

    public Boolean regExPatternMatch(String stringtoLookAt, String stringPattern) {
        Pattern pattern = Pattern.compile(stringPattern);
        Matcher matcher = pattern.matcher(stringtoLookAt);

        return matcher.find();
        // return matcher.lookingAt();
    }
}
