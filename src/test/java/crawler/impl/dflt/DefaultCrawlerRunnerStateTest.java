package crawler.impl.dflt;

import crawler.api.CrawlerState;
import crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.*;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.*;
import static org.junit.Assert.fail;

public class DefaultCrawlerRunnerStateTest {

    @Test
    public void testStop() {
        int[][] graph = new TreeGraphCreationStrategy().createGraph(2500, 10);
        DefaultContentDownloader downloader = DefaultCrawlerRunnerTestUtils.mockDownloader();
        DefaultLinkExtractor extractor = DefaultCrawlerRunnerTestUtils.mockExtractor();
        mockCrawlerComponents(graph, downloader, extractor, 200);
        String path = SLASH + graph[0][0];
        URL startUrl = null;
        try {
            startUrl = new URL(PROTOCOL, DOMAIN, path);
        } catch (MalformedURLException e) {
            fail("Cannot create start URL for (protocol, domain, path) = (" + PROTOCOL + ", " + DOMAIN + ", " + path + ")");
            e.printStackTrace();
        }
        DefaultCrawlerRunner runner = new DefaultCrawlerRunner(startUrl);
        new Thread(runner).start();
        try {
            Thread.sleep(5000);
            Assert.assertEquals(CrawlerState.RUNNING, runner.getState());
            runner.setState(CrawlerState.STOPPED);
            Thread.sleep(1000);
            Assert.assertEquals(CrawlerState.STOPPED, runner.getState());
        } catch (InterruptedException e) {
            fail("Thread problem: " + e.getMessage());
        }
    }
}