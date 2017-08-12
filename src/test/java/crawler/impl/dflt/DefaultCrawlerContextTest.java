package crawler.impl.dflt;

import crawler.api.*;
import crawler.api.CrawlerContext.CrawlerInfo;
import org.junit.Assert;
import org.junit.Test;
import reactor.fn.Consumer;
import sun.security.krb5.internal.CredentialsUtil;

import java.net.URL;
import java.util.Optional;

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
        DefaultLinksStorage storage = new DefaultLinksStorage();
        Optional<CrawlerInfo<URL>> crawlerInfoOpt = context.createNewCrawler(url, storage);
        assertTrue(crawlerInfoOpt.isPresent());
        CrawlerInfo<URL> crawlerInfo = crawlerInfoOpt.get();
        assertEquals(url, crawlerInfo.getStartPoint());
        assertEquals(CrawlerState.NEW, crawlerInfo.getState());
        assertEquals(storage, crawlerInfo.getLinksStorage());
    }

    @Test
    public void createNewCrawler_startPoint_storage_subscribers() throws Exception {
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url = new URL(PROTOCOL, DOMAIN, SLASH);
        DefaultLinksStorage storage = new DefaultLinksStorage();
        SubscriberContainer container = SubscriberContainer.builder()
                .add(NewLinksAvailableEvent.class, new TestConsumer(NewLinksAvailableEvent.class))
                .add(ContentToProcessEvent.class, new TestConsumer(ContentToProcessEvent.class))
                .add(CrawlerStateChangedEvent.class, new TestConsumer(CrawlerStateChangedEvent.class))
                .build();
        Optional<CrawlerInfo<URL>> crawlerInfoOpt = context.createNewCrawler(url, storage, container);
        assertTrue(crawlerInfoOpt.isPresent());
        CrawlerInfo<URL> crawlerInfo = crawlerInfoOpt.get();
        assertEquals(url, crawlerInfo.getStartPoint());
        assertEquals(CrawlerState.NEW, crawlerInfo.getState());
        assertEquals(storage, crawlerInfo.getLinksStorage());
    }

    @Test
    public void subscribeTo() throws Exception {

    }

    @Test
    public void subscribeTo1() throws Exception {

    }

    @Test
    public void deleteSubscribersFrom() throws Exception {

    }

    @Test
    public void getSubscribersForCrawler() throws Exception {

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

    }

    @Test
    public void getCrawlerByStartPoint() throws Exception {

    }

    @Test
    public void getAllCrawlers() throws Exception {

    }

    @Test
    public void getCrawlersByState() throws Exception {

    }

    @Test
    public void isCrawled() throws Exception {

    }

    private class TestConsumer<T extends CrawlerEvent> implements Consumer<T>{
        private Class<CrawlerEvent> eventClass;

        TestConsumer(Class<CrawlerEvent> eventClass){
            this.eventClass = eventClass;
        }

        public Class<CrawlerEvent> getEventClass() {
            return eventClass;
        }

        @Override
        public void accept(T o) {

        }
    }

}