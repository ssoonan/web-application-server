package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class HttpRequest extends Http {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private String method;
    private String url;
    private String lastUrl;

    // 매 요청이 올 때마다 생성자 단계에서 정보들을 다 세팅, 동기적으
    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();
        processRequestLine(line); // 처음 line에서 세팅할 기본값 세팅
        boolean isBody = false;
        int contentLength = 0;
        while (!line.equals("")) {
            line = br.readLine();
            if (line == null) return;
            if (line.equals("")) {
                isBody = true;
            }
            if (!isBody) this.header.putAll(HttpRequestUtils.parseHeaderFromLine(line)); // body가 아닐 때만 헤더 세팅
            if (line.contains("Cookie:")) {
                String[] tokens = line.split(" ");
                String cookieLine = tokens[tokens.length - 1];
                this.header.put("cookie", cookieLine);
            }
            if (this.method.equals("POST") && line.contains("Content-Length")) { // POST 일 때
                String cl = line.substring("Content-Length:".length()).trim();
                contentLength = Integer.parseInt(cl);
            }
        }
        if (contentLength > 0) { // FIXME: POST 일 때 param 세팅이 여기
            char[] body = new char[contentLength];  //<-- http body is here
            br.read(body);
            String httpBody = String.copyValueOf(body);
            this.param = HttpRequestUtils.parseNameValFromQueryString(httpBody);
        }
    }
    // 첫번쨰 라인에서의 종합적인 세팅
    private void processRequestLine(String requestLine) {
        this.method = HttpRequestUtils.parseHTTPMethodFromLine(requestLine);
        this.url = HttpRequestUtils.parseUrlFromLine(requestLine);
        this.lastUrl = HttpRequestUtils.parseEndUrlFromUrl(url);
        if (this.method.equals("GET")) this.param = HttpRequestUtils.parseNameValFromQueryString(this.lastUrl); // FIXME: GET일 땐 param 세팅을 여기
    }

    public String getHttpMethod() {
        return this.method;
    }

    public String getUrl() {
        return this.url;
    }

    public String getLastUrl() {
        return this.lastUrl;
    }

}
