package jobsources;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExLookAt implements Serializable {

   public Boolean regExPatternMatch(String stringWithLeadingNumber,String stringPattern) {
        Pattern pattern = Pattern.compile(stringPattern);
        Matcher matcher = pattern.matcher(stringWithLeadingNumber);

        return matcher.lookingAt();
//        if (matcher.lookingAt()) {
//            return true;
//            //dontShowJob = true;
//        }
//        return false;
    }
}
