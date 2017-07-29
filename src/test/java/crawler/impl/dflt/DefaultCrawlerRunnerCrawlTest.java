package crawler.impl.dflt;

import crawler.api.ContentToProcessEvent;
import crawler.api.CrawlerConsumer;
import crawler.api.CrawlerRunner;
import crawler.api.CrawlerState;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCrawlerRunnerCrawlTest {

    private static final int PAGES_COUNT = 500;
    private static final int OUTCOMES = 6;
    private static final int CYCLES = 10;

    @Test
    public void testCrawling() {
        Set<Class<? extends GraphCreationStrategy>> classes = getStrategies();
        for (int i = 0; i < CYCLES; i++) {
            classes.forEach(clazz -> {
                DefaultContentDownloader downloader = mockDownloader();
                DefaultLinkExtractor extractor = mockExtractor();
                int[][] graph = null;
                try {
                    graph = clazz.newInstance().createGraph(PAGES_COUNT, OUTCOMES);
                } catch (InstantiationException | IllegalAccessException e) {
                    fail("Cannot mock crawler components because there was some instantiation problem: " + e.getMessage());
                    e.printStackTrace();
                }
                mockCrawlerComponents(graph, downloader, extractor, 0);

                CountDownLatch latch = new CountDownLatch(PAGES_COUNT);
                DefaultCrawlerRunner runner = null;
                String path = SLASH + graph[0][0];
                try {
                    URL startUrl = new URL(PROTOCOL, DOMAIN, path);
                    runner = new DefaultCrawlerRunner(startUrl, new DefaultLinksStorage(), downloader, extractor);
                } catch (MalformedURLException e) {
                    fail("Cannot create start URL for (protocol, domain, path) = (" + PROTOCOL + ", " + DOMAIN + ", " + path + ")");
                    e.printStackTrace();
                }
                runner.subscribe(ContentToProcessEvent.class, new TestConsumer(runner, latch));
                runner.run();
                assertEquals("Bad count for strategy: " + clazz.getSimpleName(), 0, latch.getCount());
                assertEquals(CrawlerState.FINISHED, runner.getState());
            });
        }
    }

    /**
     * Implementation of {@code CrawlerConsumer} for testing purposes.
     */
    private static class TestConsumer extends CrawlerConsumer<ContentToProcessEvent> {

        CountDownLatch latch;

        public TestConsumer(CrawlerRunner runner, CountDownLatch latch) {
            super(runner);
            this.latch = latch;
        }

        @Override
        public void accept(ContentToProcessEvent crawlerEvent) {
            latch.countDown();
        }

    }
}