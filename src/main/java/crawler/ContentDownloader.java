package crawler;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import crawler.event.HtmlDownloadedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.bus.Event;

import java.net.URL;

import static java.util.Objects.requireNonNull;

public class ContentDownloader extends CrawlerConsumer<Event<URL>> {

    private static final String ACCEPT = "accept";
    private static final String TEXT_CSS = "text/css";
    private static final String ERROR_MESSAGE = "An error occurred during content downloading. ";

    @Autowired
    private EventPublisher publisher;

    public ContentDownloader(CrawlerContext crawlerContext) {
        super(crawlerContext);
    }

    @Override
    public void accept(Event<URL> event) {
        requireNonNull(event.getData());
        String content = null;
        try {
            content = Unirest.get(event.getData().getPath())
                    .header(ACCEPT, TEXT_CSS)
                    .asObject(String.class)
                    .getBody();
        } catch (UnirestException e) {
            this.logger.error(ERROR_MESSAGE, event.getData().getPath(), e);
        }

        if (content != null) {
            publisher.publish(new HtmlDownloadedEvent(content, crawlerContext));
        }
    }
}
