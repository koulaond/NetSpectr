package crawler.impl;

import crawler.impl.dflt.DefaultLinkExtractor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class DefaultLinkExtractorTest {
    private static final String BASE_URL_DEF = "https://www.test.domain.com";
    private static URL BASE_URL;

    private static final String HTML = "<html>\n" +
            "<head></head>\n" +
            "<body>\n" +
            "<div id=\"main\">\n" +
            "    <a href=\"/business\" title=\"Business\"/>\n" +
            "    <a href=\"/organisation\" title=\"Organisation\"/>\n" +
            "    <div id=\"products\">\n" +
            "        <a href=\"/products/network\" title=\"Network products\"/>\n" +
            "        <div id=\"in-progress\">\n" +
            "            <a href=\"/products/network/inprogress\" title=\"In progress\"/>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    private static final String[] EXPECTED_URLS = {
            "/business",
            "/organisation",
            "/products/network",
            "/products/network/inprogress"
    };
    private static List<URL> EXPECTED_URLS_COLLECTION;

    @BeforeClass
    public static void setup() {
        EXPECTED_URLS_COLLECTION = new ArrayList<>();
        try {
            BASE_URL = new URL(BASE_URL_DEF);
        } catch (MalformedURLException e) {
            fail("Cannot set Base URL due to: " + e.getMessage());
        }

        Stream.of(EXPECTED_URLS).forEach(url -> {
            try {
                EXPECTED_URLS_COLLECTION.add(new URL(BASE_URL_DEF + url));
            } catch (MalformedURLException e) {
                fail("Cannot set testing URLs due to: " + e.getMessage());
            }
        });
    }

    @Test
    public void testExtractLinks() throws Exception {
        DefaultLinkExtractor extractor = new DefaultLinkExtractor(BASE_URL);
        extractor.extractLinks(HTML).forEach(url -> assertTrue(EXPECTED_URLS_COLLECTION.contains(url)));
    }
}