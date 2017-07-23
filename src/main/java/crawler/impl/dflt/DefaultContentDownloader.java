package crawler.impl.dflt;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import crawler.api.ContentDownloader;

import java.net.URL;

public class DefaultContentDownloader implements ContentDownloader<URL, String> {

    private static final String HEADER_ACCEPT_KEY = "accept";
    private static final String HEADER_ACCEPT_VALUE = "text/css";

    @Override
    public String downloadContent(URL url) {
        String protocol = url.getProtocol();
        if (!protocol.equals("http") && !protocol.equals("https")) {
            throw new IllegalStateException("Unsupported protocol: " + protocol);
        }
        if (url.getHost() == null || url.getHost().isEmpty()) {
            throw new IllegalStateException("Host is missing.");
        }
        try {
            return Unirest.get(url.toExternalForm())
                    .header(HEADER_ACCEPT_KEY, HEADER_ACCEPT_VALUE)
                    .asObject(String.class)
                    .getBody();
        } catch (UnirestException e) {
            throw new IllegalStateException("An error occurred during URL access. Error cause: " + e.getMessage());
        }
    }
}
