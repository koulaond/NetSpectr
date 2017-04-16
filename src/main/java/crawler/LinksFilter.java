package crawler;

import crawler.event.LinksExtractedEvent;
import crawler.event.NewLinkAvailableEvent;

import java.net.URL;
import java.util.Set;

public class LinksFilter extends CrawlerConsumer<LinksExtractedEvent>{
    public LinksFilter(CrawlerRunner runner) {
        super(runner);
    }

    @Override
    public void accept(LinksExtractedEvent event) {
        Set<URL> urls = event.getData();
        LinksStorage storage = this.runner.getLinksStorage();

        urls.forEach(url -> {
            if(!storage.isProcessed(url) && !storage.isQueued(url)){
                storage.add(url);
                runner.getPublisher().publish(NewLinkAvailableEvent.instance(url, event.getSourceHtml()));
            }
        });
    }
}
