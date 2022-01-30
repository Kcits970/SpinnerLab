package util;

public class MyStringFunctions {
    public static String truncate(String s, int length) {
        return (s.length() > length) ? s.substring(0, length) + "..." : s;
    }
}
