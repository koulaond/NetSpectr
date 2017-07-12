package crawler.impl;

import crawler.ContentDownloader;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class DefaultContentDownloaderTest {
    private static URL URL_GOOGLE_CORRECT;
    private static URL URL_GOOGLE_BAD_SYNTAX;
    private static URL URL_GOOGLE_BAD_ADDRESS;
    private static URL URL_GOOGLE_MISSING_PROTOCOL;
    private static URL URL_GOOGLE_PROTOCOL_ONLY;

    @BeforeClass
    public static void setupTest() {
        try {
            URL_GOOGLE_CORRECT = new URL("https://www.google.cz");
            URL_GOOGLE_BAD_SYNTAX = new URL("https://www.google.cz");
            URL_GOOGLE_BAD_ADDRESS = new URL("https://www.google.cz");
            URL_GOOGLE_MISSING_PROTOCOL = new URL("https://www.google.cz");
            URL_GOOGLE_PROTOCOL_ONLY = new URL("https://www.google.cz");
        } catch (MalformedURLException e) {
            Assert.fail("Cannot set testing URLs due to: " + e.getMessage());
        }
    }


    @Test
    public void downloadContent_correctUrl() throws Exception {
        DefaultContentDownloader downloader = new DefaultContentDownloader();
        String content = downloader.downloadContent(URL_GOOGLE_CORRECT);
    }

}