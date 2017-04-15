package crawler;

import crawler.event.CrawlerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.bus.Event;
import reactor.bus.EventBus;
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

    void init(Map<Class<? extends CrawlerEvent>, CrawlerConsumer> eventMap){
        eventMap.keySet().forEach(eventClass -> eventBus.on(Selectors.type(eventClass), eventMap.get(eventClass)));
    }

    public void publish(Event event) {
        logger.info(String.format("Publishing event %s with data of type %s", event.getClass(), event.getData().getClass()));
        eventBus.notify(event.getClass(), event);
    }
}
