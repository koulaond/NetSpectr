package crawler.api;

import java.util.*;

import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

public final class SubscriberContainer {

    private Map<Class<? extends CrawlerEvent>, Subscribers> subscriberContainer;

    private SubscriberContainer(Map<Class<? extends CrawlerEvent>, Subscribers> subscriberContainer) {
        this.subscriberContainer = subscriberContainer;
    }

    public Set<CrawlerConsumer> getSubscribersFor(Class<? extends CrawlerEvent> event){
        Subscribers subscribers = subscriberContainer.get(event);
        if(subscribers == null){
            return null;
        }
        return subscribers.getSubscribers();
    }

    public Set<CrawlerConsumer> getSubscribersFor(CrawlerEvent event){
        return getSubscribersFor(event.getClass());
    }

    public Set<Class<? extends CrawlerEvent>> getEvents(){
        return subscriberContainer.keySet();
    }

    public boolean isEmpty(){
        return this.subscriberContainer.isEmpty();
    }

    public static SubscriberContainerBuilder builder(){
        return new SubscriberContainerBuilder();
    }

    public static final class SubscriberContainerBuilder {
        private Map<Class<? extends CrawlerEvent>, Subscribers> subscriberContainer;

        private SubscriberContainerBuilder(){
            this.subscriberContainer = new HashMap<>();
        }

        public SubscriberContainerBuilder add(Class<? extends CrawlerEvent> eventClass, CrawlerConsumer consumer){
            requireNonNull(eventClass);
            requireNonNull(consumer);
            Subscribers subscribers = subscriberContainer.get(eventClass);
            if(subscribers == null){
                subscribers = new Subscribers();
                subscriberContainer.put(eventClass, subscribers);
            }
            subscribers.addSubscriber(consumer);
            return this;
        }

        public SubscriberContainer build(){
            return new SubscriberContainer(this.subscriberContainer);
        }
    }

    private static class Subscribers{
        private Set<CrawlerConsumer> subscribers;

        Subscribers() {
            this.subscribers = new HashSet<>();
        }

        void addSubscriber(CrawlerConsumer subscriber) {
            this.subscribers.add(subscriber);
        }

        boolean contains(CrawlerConsumer consumer){
            return subscribers.contains(consumer);
        }

        Set<CrawlerConsumer> getSubscribers(){
            return unmodifiableSet(subscribers);
        }
    }
}
