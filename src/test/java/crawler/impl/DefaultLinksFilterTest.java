package crawler.impl;

import crawler.LinksStorage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class DefaultLinksFilterTest {

    private static final String[] URLS_TO_FILTER = {
            "https://www.winteriscoming.com",
            "https://www.winterishere.com",
            "https://www.fucktheking.com",
            "https://www.idfuckher.com",
            "https://www.kingofthenorth.com",
            "https://www.reek.com",
            "https://www.khaldrogo.com",
            "https://www.littleman.com"
    };

    private static final String[] IS_PROCESSED = {
            "https://www.winteriscoming.com",
            "https://www.winterishere.com"
    };
    private static final String[] IS_QUEUED = {
            "https://www.khaldrogo.com",
            "https://www.littleman.com"
    };

    private static final String[] FILTERED_URLS = {
            "https://www.fucktheking.com",
            "https://www.idfuckher.com",
            "https://www.kingofthenorth.com",
            "https://www.reek.com"
    };

    private static List<URL> URLS_TO_FILTER_COLLECTION;
    private static List<URL> FILTERED_URLS_COLLECTION;

    @BeforeClass
    public static void setup() {
        URLS_TO_FILTER_COLLECTION = new ArrayList<>();
        FILTERED_URLS_COLLECTION = new ArrayList<>();
        Stream.of(URLS_TO_FILTER).forEach(s -> {
            try {
                URLS_TO_FILTER_COLLECTION.add(new URL(s));
            } catch (MalformedURLException e) {
                Assert.fail("Cannot set testing URLs due to: " + e.getMessage());
            }
        });
        Stream.of(FILTERED_URLS).forEach(s -> {
            try {
                FILTERED_URLS_COLLECTION.add(new URL(s));
            } catch (MalformedURLException e) {
                Assert.fail("Cannot set testing URLs due to: " + e.getMessage());
            }
        });
    }

    @Test
    public void testFilterLinks() throws Exception {
        DefaultLinksFilter filter = new DefaultLinksFilter();
        Iterable<URL> filtered = filter.filterLinks(new ArrayList<>(URLS_TO_FILTER_COLLECTION), storage());
        filtered.forEach(url -> Assert.assertTrue(FILTERED_URLS_COLLECTION.contains(url)));
    }

    private LinksStorage<URL> storage() {
        return new LinksStorage<URL>() {
            @Override
            public void add(URL item) {
            }

            @Override
            public void add(Iterable<URL> items) {
            }

            @Override
            public URL poll() {
                return null;
            }

            @Override
            public void setProcessed(URL item) {
            }

            @Override
            public boolean isProcessed(URL item) {
                return isContainedIn(item, IS_PROCESSED);
            }

            @Override
            public boolean isQueued(URL item) {
                return isContainedIn(item, IS_QUEUED);
            }

            private boolean isContainedIn(URL item, String[] array) {
                for (int i = 0; i < array.length; i++) {
                    if (array[i].equals(item.toExternalForm())) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Iterator<URL> iterator() {
                return null;
            }
        };
    }
}