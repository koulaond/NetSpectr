package crawler.impl.dflt;

import crawler.api.ContentToProcessEvent;
import org.junit.Assert;
import org.reflections.Reflections;
import reactor.fn.Consumer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultCrawlerRunnerTestUtils {

    public static final String PROTOCOL = "https";
    public static final String DOMAIN = "test";
    public static final String SLASH = "/";

    public static DefaultContentNodeDownloader mockDownloader(int[][] graph){
        DefaultContentNodeDownloader downloader = mock(DefaultContentNodeDownloader.class);
        Map<URL, HtmlMetaData> nodes = new HashMap<>();
        for (int i = 0; i < graph.length; i++) {
            final int inc = i;  // effectively final
            try {
                URL url = new URL(PROTOCOL, DOMAIN, SLASH + i);
                nodes.computeIfAbsent(url, url1 -> {
                    List<URL> outcomes = new ArrayList<>();
                    for (int j = 0; j < graph[inc].length; j++) {
                        try {
                            outcomes.add(new URL(PROTOCOL, DOMAIN, SLASH + j));
                        } catch (MalformedURLException e) {
                            Assert.fail();
                        }
                    }
                    return new HtmlMetaData(url1, "html for " + inc, "title for "+inc, outcomes);
                });

            } catch (MalformedURLException e) {
                Assert.fail();
            }
        }
        nodes.forEach((url, metaData) -> when(downloader.downloadContent(url)).thenReturn(metaData));
        return downloader;
    }

    public static Set<Class<? extends GraphCreationStrategy>> getStrategies(){
        Reflections reflections = new Reflections(DefaultCrawlerRunnerTestUtils.class.getPackage().getName());
        Set<Class<? extends GraphCreationStrategy>> classes = reflections.getSubTypesOf(GraphCreationStrategy.class);
        return classes;
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
     * Implementation of {@code Consumer} for testing purposes.
     */
    static class TestConsumer implements Consumer<ContentToProcessEvent> {

        private CountDown countDown;

        public TestConsumer( CountDown countDown) {
            this.countDown = countDown;
        }

        @Override
        public void accept(ContentToProcessEvent crawlerEvent) {
            countDown.countDown();
        }

    }
}
