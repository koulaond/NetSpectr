package crawler.event;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.bus.Event;
import reactor.bus.EventBus;

public class CrawlerEventPublisher {

    @Autowired
    private EventBus eventBus;

    public void publish(Event event) {
        eventBus.notify("event published", event);
    }
}
