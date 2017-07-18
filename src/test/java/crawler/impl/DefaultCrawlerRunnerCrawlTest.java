package crawler.impl;

import crawler.ContentToProcessEvent;
import crawler.CrawlerConsumer;
import crawler.CrawlerEvent;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

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
    private static final HashSet<URL> urlPool = new HashSet<>();

    @Test
    public void testCrawling() throws Exception {
        GRAPH = new PermutationGraphCreationStragegy().createGraph(PAGES_COUNT, OUTCOMES);
        for (int i = 0; i < GRAPH.length; i++) {
            Set<URL> urls = new HashSet<>();
            int[] node = GRAPH[i];
            for (int j = 0; j < node.length; j++) {
                try {
                    URL url = new URL(PROTOCOL, DOMAIN, SLASH + node[j]);
                    urls.add(url);
                    urlPool.add(url);
                } catch (MalformedURLException e) {
                    Assert.fail();
                }
            }
            when(extractor.extractLinks(SLASH+i)).thenReturn(urls);
            int j = 0;
            Iterator<URL> iterator = urlPool.iterator();
            while (iterator.hasNext()) {
                URL next = iterator.next();
                when(downloader.downloadContent(next)).thenReturn(SLASH+j);
                j++;
            }
        }

        DefaultCrawlerRunner runner = new DefaultCrawlerRunner(new URL(PROTOCOL, DOMAIN, ""));
        runner.subscribe(ContentToProcessEvent.class, new CrawlerConsumer(runner) {
            private int contentsCount;

            @Override
            public void accept(CrawlerEvent crawlerEvent) {
                contentsCount++;
            }

        });
        runner.run();
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
        private Map<Integer, Boolean> visited;

        @Override
        public int[][] createGraph(int nodes, int outcomes) {
            this.visited = new HashMap<>(nodes);
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
}