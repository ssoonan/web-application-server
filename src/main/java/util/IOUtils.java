package util;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */

    public static String parseUrlFromBr(BufferedReader br) throws IOException {
        String line = br.readLine();
        return HttpRequestUtils.parseUrlFromLine(line);
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
