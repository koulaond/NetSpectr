package crawler.api;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import crawler.ContentDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import static java.util.Objects.requireNonNull;

public class DefaultContentDownloader implements ContentDownloader<URL, String>{

    private static final String ACCEPT = "accept";
    private static final String TEXT_CSS = "text/css";
    private static final String ERROR_MESSAGE = "An error occurred during content downloading. ";

    private final Logger LOGGER;

    public DefaultContentDownloader() {
        this.LOGGER = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public String downloadContent(URL url) {
        requireNonNull(url);
        String content = null;
        try {
            content = Unirest.get(url.toExternalForm())
                    .header(ACCEPT, TEXT_CSS)
                    .asObject(String.class)
                    .getBody();
        } catch (UnirestException e) {
            this.LOGGER.error(ERROR_MESSAGE, url.getPath(), e);
        }
        return content;
    }

}
