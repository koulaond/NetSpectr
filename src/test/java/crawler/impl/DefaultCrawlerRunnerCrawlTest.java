package crawler.impl;

import crawler.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCrawlerRunnerCrawlTest {

    @InjectMocks
    private static DefaultCrawlerRunner runner;

    @Mock
    private static DefaultContentDownloader downloader;

    @Mock
    private static DefaultLinkExtractor extractor;

    private static final String PROTOCOL = "https";
    private static final String DOMAIN = "test";
    private static final String SLASH = "/";
    private static final int PAGES_COUNT = 500;
    private static final int OUTCOMES = 6;
    private static int[][] GRAPH;
    private static final List<URL> urlPool = new ArrayList<>();

    @Test
    public void testCrawling() throws Exception {
        GRAPH = new TreeGraphCreationStrategy().createGraph(PAGES_COUNT, OUTCOMES);
        for (int i = 0; i < GRAPH.length; i++) {
            List<URL> urls = new ArrayList<>();
            int[] node = GRAPH[i];
            for (int j = 0; j < node.length; j++) {
                try {
                    URL url = new URL(PROTOCOL, DOMAIN, SLASH + node[j]);
                    urls.add(url);
                    if (!urlPool.contains(url)) {
                        urlPool.add(url);
                    }
                } catch (MalformedURLException e) {
                    Assert.fail();
                }
            }
            when(extractor.extractLinks(SLASH + i)).thenReturn(urls);
            int j = 0;
            Iterator<URL> iterator = urlPool.iterator();
            while (iterator.hasNext()) {
                URL next = iterator.next();
                when(downloader.downloadContent(next)).thenReturn(SLASH + j);
                j++;
            }
        }
        final int[] count = {0};
        CountDownLatch latch = new CountDownLatch(500);
        DefaultCrawlerRunner runner = new DefaultCrawlerRunner(new URL(PROTOCOL, DOMAIN, SLASH + GRAPH[0][0]), new DefaultLinksStorage(), downloader, extractor);
        runner.subscribe(ContentToProcessEvent.class, new TestConsumer(runner, latch));
        runner.run();
        if(latch.await(6000, TimeUnit.MILLISECONDS) == false){
            Assert.fail();
        }
        assertEquals(CrawlerState.FINISHED, runner.getState());
    }

    /**
     * Strategy for creating a connected graph that represents a network.
     */
    private interface GraphCreationStrategy {
        int[][] createGraph(int nodes, int outcomes);
    }

    /**
     * Creates a connected graph using K random permutations
     */
    private static class PermutationGraphCreationStragegy implements GraphCreationStrategy {

        @Override
        public int[][] createGraph(int nodes, int outcomes) {
            int[][] output = new int[nodes][outcomes];

            for (int i = 0; i < outcomes; i++) {
                ArrayList<Integer> permutation = new ArrayList<>(nodes);
                for (int j = 0; j < nodes; j++) {
                    permutation.add(j);
                }
                Collections.shuffle(permutation);
                for (int j = 0; j < nodes; j++) {
                    output[j][i] = permutation.get(j);
                }
            }
            return output;
        }
    }

    /**
     *
     */
    private static class CyclicGraphCreationStrategy implements GraphCreationStrategy {

        @Override
        public int[][] createGraph(int nodes, int maxOutcomes) {
            int[][] output = new int[nodes][];
            int inc = 0;
            Random random = new Random();
            for (int i = 0; i < nodes; i++) {
                int[] node = new int[random.nextInt(maxOutcomes) + 1];
                for (int j = 0; j < node.length; j++) {
                    node[j] = inc;
                    inc = (++inc) % 500;
                }
                output[i] = node;
            }
            return output;
        }
    }

    /**
     *
     */
    private static class TreeGraphCreationStrategy implements GraphCreationStrategy {

        @Override
        public int[][] createGraph(int nodes, int outcomes) {
            int[][] output = new int[nodes][outcomes];
            int parentIndex = 0;
            for (int i = 1; i < nodes; i++) {
                output[parentIndex][(i - 1) % outcomes] = i;
                if (i % outcomes == 0) {
                    ++parentIndex;
                }
            }
            return output;
        }
    }

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