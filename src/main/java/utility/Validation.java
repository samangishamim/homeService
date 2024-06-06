package utility;

import java.util.regex.Pattern;

public class Validation {
    private static final Pattern EMAIL_PATTERN ;
    static {
        EMAIL_PATTERN = Pattern.compile("^(.+)@(\\S+)$");
    }
    public static boolean validateEmail(String email) {

        return EMAIL_PATTERN.matcher(email).matches();

    }
}
