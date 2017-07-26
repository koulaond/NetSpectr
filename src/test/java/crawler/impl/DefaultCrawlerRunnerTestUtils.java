package crawler.impl;

import crawler.impl.dflt.DefaultContentDownloader;
import crawler.impl.dflt.DefaultLinkExtractor;
import org.junit.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultCrawlerRunnerTestUtils {

    public static DefaultContentDownloader mockDownloader(){
        return mock(DefaultContentDownloader.class);
    }

    public static DefaultLinkExtractor mockExtractor(){
        return mock(DefaultLinkExtractor.class);
    }

    public static void mockCrawlerComponents(int[][] graph, DefaultContentDownloader downloader, DefaultLinkExtractor extractor)
            throws InstantiationException, IllegalAccessException {
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
            when(extractor.extractLinks("/" + i)).thenReturn(urls);
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
}
