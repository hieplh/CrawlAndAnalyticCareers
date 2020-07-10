/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.crawl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 *
 * @author Admin
 */
public class CrawlerHelper {

    public static InputStream openConnect(String href) throws TransformerException, IOException {
        if (href != null) {
            URL url = new URL(href);
            return url.openStream();
        }
        return null;
    }
    /*
    public static InputStream openConnect(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.addRequestProperty("Accept-Language", "vi-VN,vi;q=0.8");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.addRequestProperty("User-Agent", "Mozilla");
        conn.addRequestProperty("Referer", "google.com");

        boolean redirect = false;

        int status = conn.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER) {
                redirect = true;
            }
        }

        if (redirect) {
            String newUrl = conn.getHeaderField("Location");
            String cookies = conn.getHeaderField("Set-Cookie");

            // open the new connnection again
            conn = (HttpURLConnection) new URL(newUrl).openConnection();
            conn.setRequestProperty("Cookie", cookies);
            conn.addRequestProperty("Accept-Language", "vi-VN,vi;q=0.8");
            conn.addRequestProperty("User-Agent", "Mozilla");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.addRequestProperty("Referer", "google.com");
        }
        return conn.getInputStream();
    }*/

    protected boolean checkStartElement(String line, String element) {
        if (line.contains("<" + element) && line.startsWith("<" + element)) {
            return true;
        }
        return false;
    }

    protected boolean checkEndElement(String line, boolean flag, String element) {
        if (flag) {
            if ((line.startsWith("<" + element) && line.endsWith("/>")) || line.endsWith("</" + element + ">")) {
                return true;
            }
        }

        return false;
    }

    protected String checkEndElement(String line) {
        String[] element = {"img", "input"};

        for (String string : element) {
            int index = line.indexOf(string);
            if (index != -1) {
                for (int i = index; i < line.length(); i++) {
                    if (line.charAt(i) == '>') {
                        if (line.charAt(i - 1) == '/') {
                            return line;
                        } else {
                            String tmp = line.substring(i + 1);
                            if (tmp.contains("</" + string + ">")) {
                                return line;
                            } else {
                                return line.substring(0, i) + "/>" + tmp;
                            }
                        }
                    }
                }
            }
        }

        return line;
    }

    protected String checkDuplicateAttribute(String content, String tag) {
        content = content.trim();
        StringBuilder sb = new StringBuilder();
        Map<String, String> map = new HashMap<>();

        int start = content.indexOf(tag);
        int end = content.length();
        for (int i = start; i < content.length(); i++) {
            if (content.charAt(i) == '>') {
                end = i;
                break;
            }
        }
        String s = content.substring(start, end + 1);

        if (s.contains(" ")) {
            String tmp = s.substring(s.indexOf(" ") + 1);

            String suffix = ">";
            if (tmp.contains("/>")) {
                suffix = "/>";
            }
            tmp = tmp.replace(suffix, "");

            int startPosKey = 0;
            int endPosKey = 0;
            String key;

            int startPosValue = 0;
            int endPosValue = 0;
            String value;

            int count = 0;
            boolean isKey = false;
            int length = tmp.length();
            for (int i = 0; i < length; i++) {
                if (!isKey) {
                    if (tmp.charAt(i) == '=') {
                        isKey = true;
                        endPosKey = i;
                    }
                } else {
                    key = tmp.substring(startPosKey, endPosKey);
                    if (tmp.charAt(i) == '"') {
                        count++;
                        if (count % 2 != 0) {
                            startPosValue = i;
                        } else {
                            endPosValue = i;
                            value = tmp.substring(startPosValue + 1, endPosValue);
                            map.put(key, value);
                        }
                    } else if (count % 2 == 0 && tmp.charAt(i) == ' ') {
                        startPosKey = i + 1;
                        isKey = false;
                    }
                }
            }

            sb.append(s.substring(0, s.indexOf(" ")));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(" ");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append("\"");
                sb.append(entry.getValue());
                sb.append("\"");
            }
            sb.append(suffix);
        }
        if (end == content.length() - 1) {
            return sb.toString();
        }
        return content.substring(0, start) + sb.toString() + content.substring(end + 1);
    }

    protected String checkMissingCloseTagDivOfLiTag(String line) {
        return "</div>\n" + line;
    }

    protected String handleLinkTag(String line) {
        if (line.matches("<link( \\w\")*>")) {
            return line + "</link>";
        }
        return line;
    }
    
    protected String handleWrongSign(String line) {
        int start = -1;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '"') {
                if (start == -1) {
                    start = i;
                } else {
                    String tmp = line.substring(start + 1, i);
                    tmp = tmp.replace("<", "&lt;");
                    tmp = tmp.replace(">", "&gt;");
                    line = line.substring(0, start + 1) + tmp + line.substring(i);
                    start = -1;
                }
            }
        }
        return line;
    }
}
