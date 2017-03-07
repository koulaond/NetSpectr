package crawler.event;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.bus.Event;
import reactor.fn.Consumer;

public abstract class CrawlerConsumer<T> implements Consumer<Event<T>>{

    @Autowired
    protected CrawlerEventPublisher publisher;


}
