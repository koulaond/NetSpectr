package crawler;

import crawler.event.CrawlerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selector;
import reactor.bus.selector.Selectors;

import java.util.Map;

public class CrawlerEventPublisher {

    private EventBus eventBus;
    private CrawlerRunner runner;
    protected Logger logger;

    public CrawlerEventPublisher(EventBus eventBus, CrawlerRunner runner) {
        this.eventBus = eventBus;
        this.runner = runner;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public CrawlerEventPublisher(EventBus eventBus, CrawlerRunner runner, Map<Selector, CrawlerConsumer> subscribers){
        this(eventBus, runner);
        subscribers.forEach((selector, crawlerConsumer) -> subscribe(selector, crawlerConsumer));
    }

    public void subscribe(Selector selector, CrawlerConsumer consumer){
        this.eventBus.on(selector, consumer);
    }

    public void publish(Event event) {
        logger.info(String.format("Publishing event %s with data of type %s, runner ID: ", event.getClass(), event.getData().getClass(), runner.getId()));
        eventBus.notify(event.getClass(), event);
    }
}
