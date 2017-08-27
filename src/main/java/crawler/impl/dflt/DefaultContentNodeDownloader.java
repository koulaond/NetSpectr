package crawler.impl.dflt;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import crawler.api.ContentNodeDownloader;

import java.net.URL;

public class DefaultContentNodeDownloader implements ContentNodeDownloader<URL, HtmlMetaData> {

    private static final String HEADER_ACCEPT_KEY = "accept";
    private static final String HEADER_ACCEPT_VALUE = "text/css";

    @Override
    public HtmlMetaData downloadContent(URL url) {
        String protocol = url.getProtocol();
        if (!protocol.equals("http") && !protocol.equals("https")) {
            throw new IllegalStateException("Unsupported protocol: " + protocol);
        }
        if (url.getHost() == null || url.getHost().isEmpty()) {
            throw new IllegalStateException("Host is missing.");
        }
        try {
            String rawHtml = Unirest.get(url.toExternalForm())
                    .header(HEADER_ACCEPT_KEY, HEADER_ACCEPT_VALUE)
                    .asObject(String.class)
                    .getBody();
            MetaDataProcessor processor = new MetaDataProcessor();
            HtmlMetaData metaData = processor.process(rawHtml, url);
            return metaData;
        } catch (UnirestException e) {
            throw new IllegalStateException("An error occurred during URL access. Error cause: " + e.getMessage());
        }
    }
}
