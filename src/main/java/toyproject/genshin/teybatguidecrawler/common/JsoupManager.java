package toyproject.genshin.teybatguidecrawler.common;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupManager {

    public static Connection.Response ConnectJsoupExecute(String url) throws IOException {
        if (url == null || url.isEmpty()) {
            return null;
        }
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.6613.85 Safari/537.36")
                .timeout(6000)
                .execute();
    }

    public static Document ConnectJsoupGet(String url) throws IOException {
        if (url == null || url.isEmpty()) {
            return null;
        }
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.6613.85 Safari/537.36")
                .timeout(6000)
                .get();
    }

    public static boolean isStatusOK(Connection.Response response) {
        return response != null && response.statusCode() == 200;
    }

    public static boolean isDocumentNull(Document document) {
        return document == null;
    }


}
