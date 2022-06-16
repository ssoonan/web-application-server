package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }
    private static boolean ifLogined = false;

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            String method = HttpRequestUtils.parseHTTPMethodFromLine(line);
            String url = HttpRequestUtils.parseUrlFromLine(line);
            String lastUrl = HttpRequestUtils.parseEndUrlFromUrl(url);
            DataOutputStream dos = new DataOutputStream(out);

            if (method.equals("POST") && lastUrl.endsWith("create")) { //TODO: code가 개판인데.. 리팩토링을 어떻게 할 지가 감도 안 잡히네,,
                String body = IOUtils.parseHTTPBody(br);
                Map<String, String> map = HttpRequestUtils.parseNameValFromQueryString(body);
                User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
                DataBase.addUser(user);
                log.debug(user.toString());
                response302(dos, "/index.html");
            }

            if (method.equals("POST") && lastUrl.endsWith("login")) {
                String body = IOUtils.parseHTTPBody(br);
                Map<String, String> map = HttpRequestUtils.parseNameValFromQueryString(body);
                User user = DataBase.findUserById(map.get("userId"));
                if (user == null){
                    response302(dos, "/user/login_failed.html");
                }
                ifLogined = true;
                response302(dos, "/index.html");
            }

            if (lastUrl.endsWith("html")) {
                byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
                response200Header(dos, body.length, ifLogined);
                responseBody(dos, body);
            }
            br.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, boolean ifLogined) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Set-Cookie: logined=" + ifLogined + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
