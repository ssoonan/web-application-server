import static org.junit.Assert.*;

import org.junit.Test;
import util.HttpRequestUtils;

import java.util.Map;

public class RequestTest {

    @Test
    public void extractHeaderTest() {
        String line = "GET /asd HTTP/1.1";
        String url = HttpRequestUtils.ParseUrlFromHeader(line);
        assertEquals("/asd", url);
    }

    @Test
    public void isQueryUrlTest() {
        String url = "/user/create?userid=asd&password=12345";
        assertTrue(HttpRequestUtils.isQueryUrl(url));
    }
    @Test
    public void parseQueryStringFromWholeUrl() {
        String url = "/user/create?userid=asd&password=12345";
        Map<String, String> map = HttpRequestUtils.parseQueryString(url);
        assertEquals(map.get("userid"), "asd");
        assertEquals(map.get("password"), "12345");
    }
}
