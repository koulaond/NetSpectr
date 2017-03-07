package crawler;

import crawler.event.CrawlerConsumer;
import reactor.bus.Event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ContentDownloader extends CrawlerConsumer<URL> {

    public String getHtml(String urlStr) throws IOException {
        return getHtml(new URL(urlStr));
    }

    public String getHtml(URL url) throws IOException {
        URLConnection connection = url.openConnection();

        StringBuilder htmlContentBuilder = new StringBuilder();
        BufferedReader contentReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = null;
        while ((line = contentReader.readLine()) != null) {
            htmlContentBuilder.append(line);
            htmlContentBuilder.append('\n');
        }
        return htmlContentBuilder.toString();
    }

    public void accept(Event<URL> event) {

    }
}
