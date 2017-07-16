package crawler.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;


public class DefaultCrawlerRunnerCrawlTest {

    @InjectMocks
    private DefaultCrawlerRunner runner;

    @Mock
    private DefaultContentDownloader downloader;

    @Mock
    private DefaultLinkExtractor extractor;

    @Mock
    private DefaultLinksFilter filter;

    private static final int PAGES_COUNT = 50;

    @Before
    public void setup(){

    }

    @Test
    public void testCrawling() throws Exception {

    }

}