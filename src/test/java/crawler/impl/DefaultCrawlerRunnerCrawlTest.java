package crawler.impl;

import crawler.api.ContentToProcessEvent;
import crawler.api.CrawlerConsumer;
import crawler.api.CrawlerRunner;
import crawler.api.CrawlerState;
import crawler.impl.dflt.DefaultContentDownloader;
import crawler.impl.dflt.DefaultCrawlerRunner;
import crawler.impl.dflt.DefaultLinkExtractor;
import crawler.impl.dflt.DefaultLinksStorage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.reflections.Reflections;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static crawler.impl.DefaultCrawlerRunnerTestUtils.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCrawlerRunnerCrawlTest {

    private static final String PROTOCOL = "https";
    private static final String DOMAIN = "test";
    private static final String SLASH = "/";
    private static final int PAGES_COUNT = 500;
    private static final int OUTCOMES = 6;
    private static final int CYCLES = 40;

    @Test
    public void testCrawling() {
        Reflections reflections = new Reflections(this.getClass().getPackage().getName());
        Set<Class<? extends GraphCreationStrategy>> classes = reflections.getSubTypesOf(GraphCreationStrategy.class);

        for (int i = 0; i < CYCLES; i++) {
            classes.forEach(clazz -> {
                DefaultContentDownloader downloader = mockDownloader();
                DefaultLinkExtractor extractor = mockExtractor();
                int[][] graph = null;
                try {
                    graph = createGraphByStrategy(clazz, PAGES_COUNT, OUTCOMES);
                    mockCrawlerComponents(graph, downloader, extractor);
                } catch (InstantiationException | IllegalAccessException e) {
                    Assert.fail("Cannot mock crawler components because there was some instantiation problem: " + e.getMessage());
                    e.printStackTrace();
                }

                CountDownLatch latch = new CountDownLatch(PAGES_COUNT);
                DefaultCrawlerRunner runner = null;
                String path = SLASH + graph[0][0];
                try {
                    URL startUrl = new URL(PROTOCOL, DOMAIN, path);
                    runner = new DefaultCrawlerRunner(startUrl, new DefaultLinksStorage(), downloader, extractor);
                } catch (MalformedURLException e) {
                    Assert.fail("Cannot create start URL for (protocol, domain, path) = (" + PROTOCOL + ", " + DOMAIN + ", " + path + ")");
                    e.printStackTrace();
                }
                runner.subscribe(ContentToProcessEvent.class, new TestConsumer(runner, latch));
                runner.run();
                try {
                    if (latch.await(8000, TimeUnit.MILLISECONDS) == false) {
                        Assert.fail("Bad count: " + latch.getCount() + " for class " + clazz.getSimpleName());
                    }
                } catch (InterruptedException e) {
                    Assert.fail("An error was occurred during countdown: " + e.getMessage());
                    e.printStackTrace();
                }
                assertEquals(CrawlerState.FINISHED, runner.getState());
            });
        }
    }

    private int[][] createGraphByStrategy(Class<? extends GraphCreationStrategy> clazz, int nodes, int outcomes)
            throws IllegalAccessException, InstantiationException {
        GraphCreationStrategy strategy = clazz.newInstance();
        return strategy.createGraph(nodes, outcomes);
    }


    /**
     * Strategy for creating a connected graph that represents a network.
     */
    interface GraphCreationStrategy {
        int[][] createGraph(int nodes, int outcomes);
    }

    /**
     * Strategy class that creates a connected graph using K random permutations
     */
    static class PermutationGraphCreationStragegy implements GraphCreationStrategy {

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
     * Strategy class that generates a connected graph using cycles.
     */
    static class CyclicGraphCreationStrategy implements GraphCreationStrategy {

        @Override
        public int[][] createGraph(int nodes, int maxOutcomes) {
            int[][] output = new int[nodes][];
            int inc = 0;
            for (int i = 0; i < nodes; i++) {
                int[] node = new int[maxOutcomes];
                for (int j = 0; j < node.length; j++) {
                    node[j] = inc;
                    inc = (++inc) % nodes;
                }
                output[i] = node;
            }
            return output;
        }
    }

    /**
     * Strategy class that generates a connected graph as n-nary tree.
     */
    static class TreeGraphCreationStrategy implements GraphCreationStrategy {

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