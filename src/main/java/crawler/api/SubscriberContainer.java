package crawler.api;

import reactor.fn.Consumer;

import java.util.*;

import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

public final class SubscriberContainer {

    private Map<Class<? extends CrawlerEvent>, Subscribers> subscriberContainer;

    private SubscriberContainer(Map<Class<? extends CrawlerEvent>, Subscribers> subscriberContainer) {
        this.subscriberContainer = subscriberContainer;
    }

    public Set<Consumer<? extends CrawlerEvent>> getSubscribersFor(Class<? extends CrawlerEvent> event) {
        Subscribers subscribers = subscriberContainer.get(event);
        if (subscribers == null) {
            return null;
        }
        return subscribers.getSubscribers();
    }

    public Set<Consumer<? extends CrawlerEvent>> getSubscribersFor(CrawlerEvent event) {
        return getSubscribersFor(event.getClass());
    }

    public Set<Class<? extends CrawlerEvent>> getEvents() {
        return subscriberContainer.keySet();
    }

    public boolean isEmpty() {
        return this.subscriberContainer.isEmpty();
    }

    public static SubscriberContainerBuilder builder() {
        return new SubscriberContainerBuilder();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (!(obj instanceof SubscriberContainer)) {
            return false;
        }
        SubscriberContainer that = (SubscriberContainer) obj;
        // getEvents() never returns null so it can be compared directly
        if (!that.getEvents().equals(getEvents())) {
            return false;
        }
        for (Class<? extends CrawlerEvent> event : getEvents()) {
            Set thisSubscribersForEvent = getSubscribersFor(event);
            Set thatSubscribersForEvent = that.getSubscribersFor(event);
            boolean same = thisSubscribersForEvent != null
                    ? thisSubscribersForEvent.equals(thatSubscribersForEvent)
                    : thatSubscribersForEvent != null;
            if(!same){
                return false;
            }
        }
        return true;
    }

    public static final class SubscriberContainerBuilder {
        private Map<Class<? extends CrawlerEvent>, Subscribers> subscriberContainer;

        private SubscriberContainerBuilder() {
            this.subscriberContainer = new HashMap<>();
        }

        public SubscriberContainerBuilder add(Class<? extends CrawlerEvent> eventClass, Consumer<? extends CrawlerEvent> consumer) {
            requireNonNull(eventClass);
            requireNonNull(consumer);
            Subscribers subscribers = subscriberContainer.get(eventClass);
            if (subscribers == null) {
                subscribers = new Subscribers();
                subscriberContainer.put(eventClass, subscribers);
            }
            subscribers.addSubscriber(consumer);
            return this;
        }

        public SubscriberContainer build() {
            return new SubscriberContainer(this.subscriberContainer);
        }
    }

    private static class Subscribers {
        private Set<Consumer<? extends CrawlerEvent>> subscribers;

        Subscribers() {
            this.subscribers = new HashSet<>();
        }

        void addSubscriber(Consumer<? extends CrawlerEvent> subscriber) {
            this.subscribers.add(subscriber);
        }

        boolean contains(Consumer<? extends CrawlerEvent> subscriber) {
            return this.subscribers.contains(subscriber);
        }

        Set<Consumer<? extends CrawlerEvent>> getSubscribers() {
            return unmodifiableSet(subscribers);
        }
    }
}
