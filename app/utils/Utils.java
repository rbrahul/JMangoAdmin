package utils;

import play.mvc.Http;

/**
 * := Coded with love by Sakib Sami on 9/3/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class Utils {
    public static boolean isPostMethod(Http.Request request) {
        return request.method().toLowerCase().contains("post");
    }

    public static boolean isEqual(String x, String y) {
        return x == y || x.equals(y);
    }

    public static boolean hasValidParam(String x) {
        return x != null && !x.trim().isEmpty();
    }
}
