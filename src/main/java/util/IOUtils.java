package util;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    /**
     * @param BufferedReader
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */

    public static String parseCookieLine(BufferedReader br) throws IOException {
        boolean ifCookie = false;
        String line = "";
        while (!ifCookie) {
            line = br.readLine();
            if (line.startsWith("Cookie:")) {
                return line;
            }
        }
        return line;
    }

    public static String parseHTTPBody(BufferedReader br) throws IOException {
        boolean headersFinished = false;
        int contentLength = -1;

        while (!headersFinished) {
            String line = br.readLine();
            headersFinished = line.isEmpty();

            if (line.startsWith("Content-Length:")) {
                String cl = line.substring("Content-Length:".length()).trim();
                contentLength = Integer.parseInt(cl);
            }
        }

        char[] body = new char[contentLength];  //<-- http body is here
        br.read(body);
        return String.copyValueOf(body);
    }
}
