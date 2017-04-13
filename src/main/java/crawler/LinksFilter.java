package crawler;

import crawler.event.LinksExtractedEvent;
import crawler.event.NewLinkAvailableEvent;

import java.net.URL;
import java.util.Set;

public class LinksFilter extends CrawlerConsumer<LinksExtractedEvent>{
    public LinksFilter(CrawlerRunner crawlerContext, CrawlerEventPublisher publisher) {
        super(crawlerContext, publisher);
    }

    @Override
    public void accept(LinksExtractedEvent event) {
        Set<URL> urls = event.getData();
        LinksStorage storage = this.crawlerContext.getLinksStorage();

        urls.forEach(url -> {
            if(!storage.isProcessed(url) && !storage.isQueued(url)){
                storage.add(url);
                publisher.publish(new NewLinkAvailableEvent(url, crawlerContext, event.getSourceHtml()));
            }
        });
    }
}
