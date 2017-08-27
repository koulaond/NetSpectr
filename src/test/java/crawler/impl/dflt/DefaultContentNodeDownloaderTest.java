package crawler.impl.dflt;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class DefaultContentNodeDownloaderTest {
    private static URL URL_GOOGLE_CORRECT_HTTP;
    private static URL URL_GOOGLE_CORRECT_HTTPS;
    private static URL URL_GOOGLE_BAD_ADDRESS;
    private static URL URL_GOOGLE_UNSUPPORTED_PROTOCOL;
    private static URL URL_GOOGLE_PROTOCOL_ONLY;

    @BeforeClass
    public static void setupTest() {
        try {
            URL_GOOGLE_CORRECT_HTTP = new URL("http://www.google.cz");
            URL_GOOGLE_CORRECT_HTTPS = new URL("https://www.google.cz");
            URL_GOOGLE_BAD_ADDRESS = new URL("https://www.googlebykoulik.cz");
            URL_GOOGLE_UNSUPPORTED_PROTOCOL = new URL("ftp://www.google.cz");
            URL_GOOGLE_PROTOCOL_ONLY = new URL("https://");
        } catch (MalformedURLException e) {
            Assert.fail("Cannot set testing URLs due to: " + e.getMessage());
        }
    }

    @Test
    public void downloadContent_correctUrl_HTTP() throws Exception {
        DefaultContentNodeDownloader downloader = new DefaultContentNodeDownloader();
        String content = downloader.downloadContent(URL_GOOGLE_CORRECT_HTTP);
        assertNotNull(content);
        assertFalse(content.isEmpty());
    }

    @Test
    public void downloadContent_correctUrl_HTTPS() throws Exception {
        DefaultContentNodeDownloader downloader = new DefaultContentNodeDownloader();
        String content = downloader.downloadContent(URL_GOOGLE_CORRECT_HTTPS);
        assertNotNull(content);
        assertFalse(content.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void downloadContent_missingProtocol() {
        DefaultContentNodeDownloader downloader = new DefaultContentNodeDownloader();
        downloader.downloadContent(URL_GOOGLE_UNSUPPORTED_PROTOCOL);
    }

    @Test(expected = IllegalStateException.class)
    public void downloadContent_protocolOnly() {
        DefaultContentNodeDownloader downloader = new DefaultContentNodeDownloader();
        downloader.downloadContent(URL_GOOGLE_PROTOCOL_ONLY);
    }

    @Test(expected = IllegalStateException.class)
    public void downloadContent_badAddress() {
        DefaultContentNodeDownloader downloader = new DefaultContentNodeDownloader();
        downloader.downloadContent(URL_GOOGLE_BAD_ADDRESS);
    }
}