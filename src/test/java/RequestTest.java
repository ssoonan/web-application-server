import static org.junit.Assert.*;

import org.junit.Test;
import util.HttpRequestUtils;

public class RequestTest {

    @Test
    public void extractHeaderTest() {
        String line = "GET /asd HTTP/1.1";
        String url = HttpRequestUtils.ParseUrlFromHeader(line);
        assertEquals("/asd", url);
    }
}
