package crawler.impl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class DefaultLinkExtractorTest {
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

    private static URL BASE_URL;

    @BeforeClass
    public static void setup(){
        try {
            BASE_URL = new URL("https://www.test.domain.com");
        } catch (MalformedURLException e) {
            Assert.fail("Cannot set testing URLs due to: " + e.getMessage());
        }
    }
    @Test
    public void testExtractLinks() throws Exception {
        DefaultLinkExtractor extractor = new DefaultLinkExtractor(BASE_URL);
        Iterable<URL> urls = extractor.extractLinks(HTML);
        // TODO
    }

}