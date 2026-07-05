package it.web.routex.utility.text;

public final class TextUtils {

    private TextUtils() {}

    public static String sanitize(String s) {
        return (s == null) ? null : s.trim();
    }
}
