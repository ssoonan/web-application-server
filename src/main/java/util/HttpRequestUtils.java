package util;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class HttpRequestUtils {
    /**
     * @param queryString
     * @return
     */
    public static String parseQueryStringFromUrl(String url) {
        String[] tokens = url.split("\\?");
        if (tokens.length < 2) {
            return "";
        }
        return tokens[1]; //TODO: 그냥 이렇게 배열로 쉭~?
    }

    public static String ParseUrlFromHeader(String header) {
        return header.split(" ")[1]; // TODO 이렇게 숫자를 넣으면 유지보수에 좋지가 않은데,,;
    }

    public static Map<String, String> parseNameValFromQueryString(String queryString) {
        return parseValues(queryString, "&");
    };

    private static Map<String, String> parseValues(String param, String separator) {
        if (StringUtils.isEmpty(param)) {
            return new HashMap<>();
        }

        String[] eachTokens = param.split(separator); //
        return Arrays.stream(eachTokens).map(string -> string.split("=")).collect(Collectors.toMap(a -> a[0], a -> a[1]));
    }
}
