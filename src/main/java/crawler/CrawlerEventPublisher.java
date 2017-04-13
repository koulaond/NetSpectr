package crawler;

import crawler.event.CrawlerEvent;
import crawler.event.NewLinkAvailableEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.ObjectSelector;

public class CrawlerEventPublisher {

    private EventBus eventBus;
    private CrawlerRunner runner;

    public CrawlerEventPublisher(EventBus eventBus, CrawlerRunner runner) {
        this.eventBus = eventBus;
        this.runner = runner;
        eventBus.on(ObjectSelector.objectSelector(NewLinkAvailableEvent.instance()), new ContentDownloader(runner, this));
    }

    public void publish(Event event) {
        eventBus.notify(event.getData().getClass(), event);
    }

    private class EventSelector extends ObjectSelector<Class<CrawlerEvent>, Class<CrawlerEvent>> {

        public EventSelector(Class<CrawlerEvent> clazz) {
            super(clazz);
        }

        @Override
        public boolean matches(Class<CrawlerEvent> key) {
            return !(getObject() == null && key != null) && (getObject() != null && getObject().isAssignableFrom(key));
        }
    }
}
