package crawler.impl.dflt;

import org.junit.Assert;
import org.reflections.Reflections;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultCrawlerRunnerTestUtils {

    public static final String PROTOCOL = "https";
    public static final String DOMAIN = "test";
    public static final String SLASH = "/";

    public static DefaultContentDownloader mockDownloader(){
        return mock(DefaultContentDownloader.class);
    }

    public static DefaultLinkExtractor mockExtractor(){
        return mock(DefaultLinkExtractor.class);
    }

    public static void mockCrawlerComponents(int[][] graph, DefaultContentDownloader downloader, DefaultLinkExtractor extractor, long delayMillis) {
        List<URL> urlPool = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            List<URL> urls = new ArrayList<>();
            int[] node = graph[i];
            for (int j = 0; j < node.length; j++) {
                try {
                    URL url = new URL("https", "test", "/" + node[j]);
                    urls.add(url);
                    if (!urlPool.contains(url)) {
                        urlPool.add(url);
                    }
                } catch (MalformedURLException e) {
                    Assert.fail();
                }
            }
            when(extractor.extractLinks("/" + i)).thenAnswer(invocationOnMock -> {
                if(delayMillis > 0) {
                    Thread.sleep(delayMillis);
                }
                return urls;
            });
        }
        int j = 0;
        Collections.sort(urlPool, urlComparator());
        Iterator<URL> iterator = urlPool.iterator();
        while (iterator.hasNext()) {
            URL next = iterator.next();
            when(downloader.downloadContent(next)).thenReturn("/" + j);
            j++;
        }
    }

    private static Comparator<URL> urlComparator() {
        return (url1, url2) -> {
            Integer left = Integer.parseInt(url1.getPath().substring(1));
            Integer right = Integer.parseInt(url2.getPath().substring(1));
            if (left > right) {
                return 1;
            } else if (left < right) {
                return -1;
            } else return 0;
        };
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
}
