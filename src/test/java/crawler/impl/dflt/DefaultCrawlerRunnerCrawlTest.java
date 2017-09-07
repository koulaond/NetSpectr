package crawler.impl.dflt;

import crawler.api.ContentToProcessEvent;
import crawler.api.CrawlerState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import static crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCrawlerRunnerCrawlTest {

    private static final int PAGES_COUNT = 500;
    private static final int OUTCOMES_COUNT_FROM_EACH_PAGE = 6;
    private static final int CYCLES = 10;

    @Test
    public void testCrawling() {
        Set<Class<? extends GraphCreationStrategy>> classes = getStrategies();
        for (int i = 0; i < CYCLES; i++) {
            classes.forEach(clazz -> {
                int[][] graph = null;
                try {
                    graph = clazz.newInstance().createGraph(PAGES_COUNT, OUTCOMES_COUNT_FROM_EACH_PAGE);
                } catch (InstantiationException | IllegalAccessException e) {
                    fail("Cannot mock crawler components because there was some instantiation problem: " + e.getMessage());
                    e.printStackTrace();
                }

                DefaultContentNodeDownloader downloader = mockDownloader(graph);
                DefaultCrawlerRunner runner = null;
                String path = SLASH + graph[0][0];
                try {
                    URL startUrl = new URL(PROTOCOL, DOMAIN, path);
                    runner = new DefaultCrawlerRunner(startUrl, new DefaultStorage(), downloader, null);
                } catch (MalformedURLException e) {
                    fail("Cannot create start URL for (protocol, domain, path) = (" + PROTOCOL + ", " + DOMAIN + ", " + path + ")");
                    e.printStackTrace();
                }
                CountDown countDown = new CountDown(PAGES_COUNT);
                runner.subscribe(ContentToProcessEvent.class, new TestConsumer(countDown));
                runner.run();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                assertTrue("Bad count for strategy: " + clazz.getSimpleName() + ", count: " + countDown.getCount(), countDown.finished());
                assertEquals(CrawlerState.FINISHED, runner.getState());
            });
        }
    }
}