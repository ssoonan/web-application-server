package util;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class HttpRequestUtils {
    /**
     * @param queryString
     * @return
     */

    public static String parseHTTPMethodFromLine(String line) {
        if (line == null) {
            return "";
        }
        return line.split(" ")[0];
    }

    public static String parseEndUrlFromUrl(String url) {
        String[] token = url.split("/");
        return token[token.length-1];
    }

    public static String parseUrlFromLine(String line) {
        if (line == null) {
            return "";
        }
        return line.split(" ")[1];
    }

    public static Map<String, String> parseNameValFromQueryString(String queryString) {
        return parseValues(queryString, "&");
    };

    public static Map<String, String> parseCookie(String line) {
        String[] tokens = line.split(" "); // Cookie: logined=true;a=b -> map으로 각각을 분리해서 리턴
        String cookieLine = tokens[tokens.length-1];
        return parseValues(cookieLine, ";");
    };

    private static Map<String, String> parseValues(String param, String separator) {
        if (StringUtils.isEmpty(param)) {
            return new HashMap<>();
        }

        String[] eachTokens = param.split(separator); //
        return Arrays.stream(eachTokens).map(string -> string.split("=")).collect(Collectors.toMap(a -> a[0], a -> a[1]));
    }
}
