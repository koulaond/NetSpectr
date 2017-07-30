package crawler.impl.dflt;

import crawler.api.ContentToProcessEvent;
import crawler.api.CrawlerState;
import crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.*;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DefaultCrawlerRunnerStateTest {

    private static final int PAGES_COUNT = 500;
    private static final int OUTCOMES = 6;
    private static final long DELAY = 20;

    @Test
    public void testNew(){
        DefaultCrawlerRunner runner = null;
        try {
            runner = new DefaultCrawlerRunner(new URL(PROTOCOL, DOMAIN, SLASH));
        } catch (MalformedURLException e) {
            Assert.fail();
        }
        assertEquals(CrawlerState.NEW, runner.getState());
    }
    @Test
    public void testRunning(){
        DefaultCrawlerRunner runner = defaultCrawlerRunner();
        CountDown countDown = new CountDown(PAGES_COUNT);
        runner.subscribe(ContentToProcessEvent.class, new TestConsumer(runner, countDown));
        new Thread(runner).start();
        try {
            Thread.sleep(1000);
            assertEquals(CrawlerState.RUNNING, runner.getState());
        } catch (InterruptedException e) {
            fail("Thread problem: " + e.getMessage());
        }
    }

    @Test
    public void testFinished(){
        DefaultCrawlerRunner runner = defaultCrawlerRunner();
        CountDown countDown = new CountDown(PAGES_COUNT);
        runner.subscribe(ContentToProcessEvent.class, new TestConsumer(runner, countDown));
        new Thread(runner).start();
        try {
            Thread.sleep(1000);
            assertEquals(CrawlerState.RUNNING, runner.getState());
            // The longest time to finish the crawl process
            Thread.sleep(PAGES_COUNT * DELAY * 2);
            assertEquals(CrawlerState.FINISHED, runner.getState());
        } catch (InterruptedException e) {
            fail("Thread problem: " + e.getMessage());
        }
    }

    @Test
    public void testStop() {
        DefaultCrawlerRunner runner = defaultCrawlerRunner();
        CountDown countDown = new CountDown(PAGES_COUNT);
        runner.subscribe(ContentToProcessEvent.class, new TestConsumer(runner, countDown));
        new Thread(runner).start();
        try {
            Thread.sleep(1000);
            assertEquals(CrawlerState.RUNNING, runner.getState());
            int countDownBeforeStop = countDown.getCount();
            runner.setState(CrawlerState.STOPPED);
            Thread.sleep(1000);
            int countDownAfterStop = countDown.getCount();
            assertEquals(CrawlerState.STOPPED, runner.getState());
            assertEquals(countDownBeforeStop, countDownAfterStop);
        } catch (InterruptedException e) {
            fail("Thread problem: " + e.getMessage());
        }
    }

    @Test
    public void testPending(){
        DefaultCrawlerRunner runner = defaultCrawlerRunner();
        CountDown countDown = new CountDown(PAGES_COUNT);
        runner.subscribe(ContentToProcessEvent.class, new TestConsumer(runner, countDown));
        new Thread(runner).start();
        try {
            Thread.sleep(1000);
            assertEquals(CrawlerState.RUNNING, runner.getState());
            int countDownBeforePause = countDown.getCount();
            runner.setState(CrawlerState.PENDING);
            Thread.sleep(1000);
            int countDownAfterPause = countDown.getCount();
            assertEquals(CrawlerState.PENDING, runner.getState());
            assertEquals(countDownBeforePause, countDownAfterPause);
            runner.setState(CrawlerState.RUNNING);
            Thread.sleep(1000);
            int countDownAfterResume = countDown.getCount();
            assertEquals(CrawlerState.RUNNING, runner.getState());
            assertTrue(countDownAfterResume < countDownAfterPause);

        } catch (InterruptedException e) {
            fail("Thread problem: " + e.getMessage());
        }
    }

    private DefaultCrawlerRunner defaultCrawlerRunner() {
        int[][] graph = new TreeGraphCreationStrategy().createGraph(PAGES_COUNT, OUTCOMES);
        DefaultContentDownloader downloader = mockDownloader();
        DefaultLinkExtractor extractor = mockExtractor();
        mockCrawlerComponents(graph, downloader, extractor, DELAY);
        String path = SLASH + graph[0][0];
        URL startUrl = null;
        try {
            startUrl = new URL(PROTOCOL, DOMAIN, path);
        } catch (MalformedURLException e) {
            fail("Cannot create start URL for (protocol, domain, path) = (" + PROTOCOL + ", " + DOMAIN + ", " + path + ")");
            e.printStackTrace();
        }
        return new DefaultCrawlerRunner(startUrl, new DefaultLinksStorage(), downloader, extractor);
    }
}