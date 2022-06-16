package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IOUtils {
    /**
     * @param BufferedReader
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */

    public Map<String, String> HTTP = new HashMap<>();
    public IOUtils(BufferedReader br) throws IOException {
        String line = br.readLine();;
        int contentLength = -1;
        while (!line.equals("")) {
            line = br.readLine();
            if (line.contains("Cookie:")) {
                String[] tokens = line.split(" ");
                String cookieLine = tokens[tokens.length-1];
                HTTP.put("cookie", cookieLine);
            }
            if (line.contains("Content-Length")) {
                String cl = line.substring("Content-Length:".length()).trim();
                contentLength = Integer.parseInt(cl);
            }
        }
        if (contentLength > 0) {
            char[] body = new char[contentLength];  //<-- http body is here
            br.read(body);
            String httpBody = String.copyValueOf(body);
            HTTP.put("body", httpBody);
        }
    }
}
