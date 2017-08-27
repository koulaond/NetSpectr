package crawler.impl.dflt;

import crawler.api.*;
import crawler.api.CrawlerContext.CrawlerInfo;
import org.junit.Test;
import reactor.fn.Consumer;

import java.net.URL;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.DOMAIN;
import static crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.PROTOCOL;
import static crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.SLASH;
import static org.junit.Assert.*;

public class DefaultCrawlerContextTest {

    @Test
    public void createNewCrawler_startPointOnly() throws Exception {
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url = new URL(PROTOCOL, DOMAIN, SLASH);
        Optional<CrawlerInfo<URL>> crawlerInfoOpt = context.createNewCrawler(url);
        assertTrue(crawlerInfoOpt.isPresent());
        CrawlerInfo<URL> crawlerInfo = crawlerInfoOpt.get();
        assertEquals(url, crawlerInfo.getStartPoint());
        assertEquals(CrawlerState.NEW, crawlerInfo.getState());
    }

    @Test
    public void createNewCrawler_startPoint_storage() throws Exception {
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url = new URL(PROTOCOL, DOMAIN, SLASH);
        DefaultStorage storage = new DefaultStorage();
        Optional<CrawlerInfo<URL>> crawlerInfoOpt = context.createNewCrawler(url, storage);
        assertTrue(crawlerInfoOpt.isPresent());
        CrawlerInfo<URL> crawlerInfo = crawlerInfoOpt.get();
        assertEquals(url, crawlerInfo.getStartPoint());
        assertEquals(CrawlerState.NEW, crawlerInfo.getState());
        assertEquals(storage, crawlerInfo.getStorage());
    }

    @Test
    public void createNewCrawler_startPoint_storage_subscribers() throws Exception {
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url = new URL(PROTOCOL, DOMAIN, SLASH);
        DefaultStorage storage = new DefaultStorage();
        SubscriberContainer subscribers = SubscriberContainer.builder()
                .add(NewTransitionsEvent.class, new TestConsumer(NewTransitionsEvent.class))
                .add(ContentToProcessEvent.class, new TestConsumer(ContentToProcessEvent.class))
                .add(CrawlerStateChangedEvent.class, new TestConsumer(CrawlerStateChangedEvent.class))
                .build();
        Optional<CrawlerInfo<URL>> crawlerInfoOpt = context.createNewCrawler(url, storage, subscribers);
        assertTrue(crawlerInfoOpt.isPresent());
        CrawlerInfo<URL> crawlerInfo = crawlerInfoOpt.get();
        assertEquals(url, crawlerInfo.getStartPoint());
        assertEquals(CrawlerState.NEW, crawlerInfo.getState());
        assertEquals(storage, crawlerInfo.getStorage());
        assertEquals(subscribers, crawlerInfo.getSubscribers());
    }

    @Test
    public void subscribeTo_singleSubscriber() throws Exception {
        TestConsumer consumer = new TestConsumer(NewTransitionsEvent.class);
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url = new URL(PROTOCOL, DOMAIN, SLASH);
        context.createNewCrawler(url);
        context.subscribeTo(url, NewTransitionsEvent.class, consumer);
        Optional<CrawlerInfo<URL>> info = context.getCrawlerByStartPoint(url);
        assertTrue(info.isPresent());
        SubscriberContainer subscribers = info.get().getSubscribers();
        Set<Consumer<? extends CrawlerEvent>> subscribersFor = subscribers.getSubscribersFor(NewTransitionsEvent.class);
        assertEquals(1, subscribersFor.size());
        assertEquals(consumer, subscribersFor.iterator().next());
    }

    @Test
    public void subscribeTo_subscriberContainer() throws Exception {
        TestConsumer newLinksConsumer = new TestConsumer(NewTransitionsEvent.class);
        TestConsumer stateChangedConsumer = new TestConsumer(CrawlerStateChangedEvent.class);
        TestConsumer contentConsumer = new TestConsumer(ContentToProcessEvent.class);
        SubscriberContainer subscribers = SubscriberContainer.builder()
                .add(NewTransitionsEvent.class, newLinksConsumer)
                .add(CrawlerStateChangedEvent.class, stateChangedConsumer)
                .add(ContentToProcessEvent.class, contentConsumer)
                .build();
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url = new URL(PROTOCOL, DOMAIN, SLASH);
        context.createNewCrawler(url);
        context.subscribeTo(url, subscribers);
        Optional<CrawlerInfo<URL>> info = context.getCrawlerByStartPoint(url);
        assertTrue(info.isPresent());
        SubscriberContainer registeredSubscribers = info.get().getSubscribers();
        assertEquals(subscribers, registeredSubscribers);
    }

    @Test
    public void deleteSubscribersFrom() throws Exception {
        TestConsumer consumer = new TestConsumer(NewTransitionsEvent.class);
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url = new URL(PROTOCOL, DOMAIN, SLASH);
        context.createNewCrawler(url);
        context.subscribeTo(url, NewTransitionsEvent.class, consumer);
        Optional<CrawlerInfo<URL>> info = context.getCrawlerByStartPoint(url);
        assertTrue(info.isPresent());
        SubscriberContainer subscribers = info.get().getSubscribers();
        Set<Consumer<? extends CrawlerEvent>> subscribersFor = subscribers.getSubscribersFor(NewTransitionsEvent.class);
        assertEquals(1, subscribersFor.size());
        assertEquals(consumer, subscribersFor.iterator().next());
        context.deleteSubscribersFrom(url);
        info = context.getCrawlerByStartPoint(url);
        assertTrue(info.isPresent());
        subscribers = info.get().getSubscribers();
        assertTrue(subscribers.isEmpty());
    }

    @Test
    public void getSubscribersForCrawler() throws Exception {
        TestConsumer newLinksConsumer = new TestConsumer(NewTransitionsEvent.class);
        TestConsumer stateChangedConsumer = new TestConsumer(CrawlerStateChangedEvent.class);
        TestConsumer contentConsumer = new TestConsumer(ContentToProcessEvent.class);
        SubscriberContainer subscribers = SubscriberContainer.builder()
                .add(NewTransitionsEvent.class, newLinksConsumer)
                .add(CrawlerStateChangedEvent.class, stateChangedConsumer)
                .add(ContentToProcessEvent.class, contentConsumer)
                .build();
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url = new URL(PROTOCOL, DOMAIN, SLASH);
        context.createNewCrawler(url);
        context.subscribeTo(url, subscribers);
        Optional<SubscriberContainer> subscribersForCrawler = context.getSubscribersForCrawler(url);
        assertTrue(subscribersForCrawler.isPresent());
        assertEquals(subscribers, subscribersForCrawler.get());
    }

    @Test
    public void changeState() throws Exception {
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url = new URL(PROTOCOL, DOMAIN, SLASH);
        context.createNewCrawler(url);
        context.changeState(url, CrawlerState.RUNNING);
        Optional<CrawlerInfo<URL>> crawler = context.getCrawlerByStartPoint(url);
        assertTrue(crawler.isPresent());
        assertEquals(CrawlerState.RUNNING, crawler.get().getState());

        context.changeState(url, CrawlerState.PENDING);
        crawler = context.getCrawlerByStartPoint(url);
        assertTrue(crawler.isPresent());
        assertEquals(CrawlerState.PENDING, crawler.get().getState());
    }

    @Test
    public void startCrawler() throws Exception {

    }

    @Test
    public void stopCrawler() throws Exception {

    }

    @Test
    public void pauseCrawler() throws Exception {

    }

    @Test
    public void resumeCrawler() throws Exception {

    }

    @Test
    public void getCrawlerByID() throws Exception {
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url = new URL(PROTOCOL, DOMAIN, SLASH);
        Optional<CrawlerInfo<URL>> newCrawler = context.createNewCrawler(url);
        assertTrue(newCrawler.isPresent());
        Optional<CrawlerInfo<URL>> crawlerByID = context.getCrawlerByID(newCrawler.get().getUuid());
        assertTrue(crawlerByID.isPresent());
        assertEquals(newCrawler.get().getUuid(), crawlerByID.get().getUuid());
        assertEquals(newCrawler.get().getState(), crawlerByID.get().getState());
        assertEquals(newCrawler.get().getSubscribers(), crawlerByID.get().getSubscribers());
    }

    @Test
    public void getCrawlerByStartPoint() throws Exception {

    }
    @Test
    public void getAllCrawlers() throws Exception {
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url1 = new URL(PROTOCOL, DOMAIN, SLASH + 1);
        URL url2 = new URL(PROTOCOL, DOMAIN, SLASH + 2);
        URL url3 = new URL(PROTOCOL, DOMAIN, SLASH + 3);
        context.createNewCrawler(url1);
        context.createNewCrawler(url2);
        context.createNewCrawler(url3);
        Set<CrawlerInfo<URL>> allCrawlers = context.getAllCrawlers();
        assertEquals(3, allCrawlers.size());
    }

    @Test
    public void getCrawlersByState() throws Exception {

    }

    @Test
    public void isCrawled() throws Exception {

    }

    private class TestConsumer<T extends CrawlerEvent> implements Consumer<T> {
        private Class<CrawlerEvent> eventClass;

        TestConsumer(Class<CrawlerEvent> eventClass) {
            this.eventClass = eventClass;
        }

        public Class<CrawlerEvent> getEventClass() {
            return eventClass;
        }

        @Override
        public void accept(T o) {

        }
    }

    private class TestRunner implements CrawlerRunner<URL> {

        private UUID uuid = UUID.randomUUID();
        private URL startPoint;
        private CrawlerState state;
        private SubscriberContainer subscribers;
        private Storage<URL> storage;

        @Override
        public UUID getId() {
            return uuid;
        }

        @Override
        public URL getStartPoint() {
            return startPoint;
        }

        @Override
        public CrawlerState getState() {
            return state;
        }

        @Override
        public SubscriberContainer getSubscribers() {
            return subscribers;
        }

        public Storage<URL> getStorage() {
            return storage;
        }

        @Override
        public void subscribe(Class<? extends CrawlerEvent> clazz, Consumer<? extends CrawlerEvent> consumer) {

        }

        @Override
        public void resetSubscribers() {

        }

        @Override
        public void setState(CrawlerState state) {
            this.state = state;

        }

        @Override
        public void run() {
            this.state = CrawlerState.RUNNING;
        }
    }

}