package crawler.event;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

public final class CrawlerEventHandler {

    private final Map<UUID, Set<Consumer<DataAcquiredCrawlerEvent>>> dataAcquiredEvents;
    private final Map<UUID, Set<Consumer<LinksExtractedCrawlerEvent>>> linksExtractedEvents;
    private final Map<UUID, Set<Consumer<StateChangedCrawlerEvent>>> stateChangedEvents;

    private final ReentrantLock lock;

    public CrawlerEventHandler() {
        this.dataAcquiredEvents = new ConcurrentHashMap<>();
        this.linksExtractedEvents = new ConcurrentHashMap<>();
        this.stateChangedEvents = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }

    public <E extends CrawlerEvent> void notify(E event) {
        UUID crawlerUuid = event.getCrawlerUuid();
        lock.lock();
        try {
            if (event instanceof DataAcquiredCrawlerEvent) {
                ofNullable(dataAcquiredEvents.get(crawlerUuid))
                        .ifPresent(consumers -> consumers.forEach(consumer -> consumer.accept((DataAcquiredCrawlerEvent) event)));
            } else if (event instanceof LinksExtractedCrawlerEvent) {
                ofNullable(linksExtractedEvents.get(crawlerUuid))
                        .ifPresent(consumers -> consumers.forEach(consumer -> consumer.accept((LinksExtractedCrawlerEvent) event)));
            } else if (event instanceof StateChangedCrawlerEvent) {
                ofNullable(stateChangedEvents.get(crawlerUuid))
                        .ifPresent(consumers -> consumers.forEach(consumer -> consumer.accept((StateChangedCrawlerEvent) event)));
            } else
                throw new IllegalArgumentException(String.format("Unsupported event type: %s", event.getClass().getName()));
        } finally {
            lock.unlock();
        }

    }

    public static CrawlerEventHandlerBuilder builder() {
        return new CrawlerEventHandlerBuilder();
    }

    private void subscribeDataAcquired(UUID crawlerUuid, Consumer<DataAcquiredCrawlerEvent> consumer) {
        dataAcquiredEvents.computeIfAbsent(crawlerUuid, o -> new HashSet<>()).add(consumer);
    }

    private void subscribeLinksExtracted(UUID crawlerUuid, Consumer<LinksExtractedCrawlerEvent> consumer) {
        linksExtractedEvents.computeIfAbsent(crawlerUuid, o -> new HashSet<>()).add(consumer);
    }

    private void subscribeStateChanged(UUID crawlerUuid, Consumer<StateChangedCrawlerEvent> consumer) {
        stateChangedEvents.computeIfAbsent(crawlerUuid, o -> new HashSet<>()).add(consumer);
    }

    public static final class CrawlerEventHandlerBuilder {
        private CrawlerEventHandler handler;

        private CrawlerEventHandlerBuilder() {
            this.handler = new CrawlerEventHandler();
        }

        public CrawlerEventHandlerBuilder subscribeDataAcquired(UUID crawlerUuid, Consumer<DataAcquiredCrawlerEvent>... consumers) {
            for (Consumer<DataAcquiredCrawlerEvent> consumer : consumers)
                this.handler.subscribeDataAcquired(crawlerUuid, consumer);
            return this;
        }

        public CrawlerEventHandlerBuilder subscribeLinksExtracted(UUID crawlerUuid, Consumer<LinksExtractedCrawlerEvent>... consumers) {
            for (Consumer<LinksExtractedCrawlerEvent> consumer : consumers)
                this.handler.subscribeLinksExtracted(crawlerUuid, consumer);
            return this;
        }

        public CrawlerEventHandlerBuilder subscribeStateChanged(UUID crawlerUuid, Consumer<StateChangedCrawlerEvent>... consumers) {
            for (Consumer<StateChangedCrawlerEvent> consumer : consumers)
                this.handler.subscribeStateChanged(crawlerUuid, consumer);
            return this;
        }

        public CrawlerEventHandler build() {
            return handler;
        }
    }
}
