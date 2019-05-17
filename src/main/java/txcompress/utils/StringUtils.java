package txcompress.utils;

public class StringUtils {

    public static String repeat(String s, int count) {
        return new String(new char[count]).replace("\0", s);
    }

}
