import static org.junit.Assert.*;

import org.junit.Test;
import util.HttpRequestUtils;
import util.IOUtils;
import webserver.HttpRequest;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = Files.newInputStream(new File(testDirectory + "Http_Get.txt").toPath());
        HttpRequest request = new HttpRequest(in);

        assertEquals("GET", request.getHttpMethod());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("*/*", request.getHeader("Accept"));
        assertEquals("asd", request.getParam("userId"));
    }
    @Test
    public void request_POST() throws Exception {
        InputStream in = Files.newInputStream(new File(testDirectory + "Http_Post.txt").toPath());
        HttpRequest request = new HttpRequest(in);

        assertEquals("POST", request.getHttpMethod());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("33", request.getHeader("Content-Length"));
        assertEquals("asd", request.getParam("userId"));
    }
}