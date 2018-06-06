package util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupUtil {

    public static Document getDocument(String url) throws IOException {
        Connection connection =  Jsoup.connect(url);
        connection.header("Cache-Control","no-cache");
        connection.timeout(60000);
        connection.ignoreContentType(true);
        try {
            Document document = connection.get();
            return document;
        } catch (IOException e) {
            throw e;
        }
    }

    public static Document getDocumentRetry(String url, int i) throws IOException {
        try {
            return getDocument(url);
        } catch (IOException e) {
            System.out.println("失败"+url + "次数" + i);
            if (i >= 3) {
                throw e;
            }
            i++;
            return getDocumentRetry(url, i);
        }
    }

}
