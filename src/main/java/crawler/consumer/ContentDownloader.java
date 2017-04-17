package crawler.consumer;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import crawler.api.CrawlerConsumer;
import crawler.api.CrawlerRunner;
import crawler.event.ContentToProcessEvent;
import crawler.event.NewLinkAvailableEvent;

import static java.util.Objects.requireNonNull;

public class ContentDownloader extends CrawlerConsumer<NewLinkAvailableEvent> {

    private static final String ACCEPT = "accept";
    private static final String TEXT_CSS = "text/css";
    private static final String ERROR_MESSAGE = "An error occurred during content downloading. ";

    public ContentDownloader(CrawlerRunner runner) {
        super(runner);
    }

    @Override
    public void accept(NewLinkAvailableEvent event) {
        requireNonNull(event.getData());
        String content = null;
        try {
            content = Unirest.get(event.getData().toExternalForm())
                    .header(ACCEPT, TEXT_CSS)
                    .asObject(String.class)
                    .getBody();
        } catch (UnirestException e) {
            this.logger.error(ERROR_MESSAGE, event.getData().getPath(), e);
        }

        if (content != null) {
            runner.getPublisher().publish(ContentToProcessEvent.instance(content, event.getData()));
        }
    }

}