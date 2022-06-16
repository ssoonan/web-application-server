import static org.junit.Assert.*;

import org.junit.Test;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class RequestTest {

    String param = "a=1&b=2&c=3";

    @Test
    public void parsingUrlFromHeader() throws IOException {
        String headerLine = "GET /index.html HTTP/1.1";
        StringReader sr = new StringReader(headerLine);
        BufferedReader br = new BufferedReader(sr);
        assertEquals("/index.html", IOUtils.parseUrlFromBr(br));
    }
    @Test
    public void parsingUrl() {
        String url = "/asd/bcd?a=1&b=2&c=3";
        String noUrl = "/asd/bcd";
        assertEquals("a=1&b=2&c=3", HttpRequestUtils.parseQueryStringFromUrl(url));
        assertEquals("", HttpRequestUtils.parseQueryStringFromUrl(noUrl));
    }

    @Test
    public void parsingQueryString() {
        Map<String, String> result = HttpRequestUtils.parseNameValFromQueryString(param);
        assertEquals(result.get("a"), "1");
        assertEquals(result.get("b"), "2");
        assertEquals(result.get("c"), "3");
    }

    @Test
    public void emptyQueryString() {
        String param = "";
        Map<String, String> result = HttpRequestUtils.parseNameValFromQueryString(param);
        assertEquals(new HashMap<String, String>(), result);

    }

    @Test
    public void onlyOneQueryString() {
        String param = "a=1";
        Map<String, String> result = HttpRequestUtils.parseNameValFromQueryString(param);
        assertTrue(result.containsKey("a"));
        assertTrue(result.containsValue("1"));
    }
}
