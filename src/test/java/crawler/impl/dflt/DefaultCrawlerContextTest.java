package crawler.impl.dflt;

import crawler.api.CrawlerContext;
import crawler.api.CrawlerContext.CrawlerInfo;
import org.junit.Assert;
import org.junit.Test;
import sun.security.krb5.internal.CredentialsUtil;

import java.net.URL;
import java.util.Optional;

import static crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.DOMAIN;
import static crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.PROTOCOL;
import static crawler.impl.dflt.DefaultCrawlerRunnerTestUtils.SLASH;
import static org.junit.Assert.*;

public class DefaultCrawlerContextTest {
    @Test
    public void createNewCrawler_startPointOnly() throws Exception {
        DefaultCrawlerContext context = new DefaultCrawlerContext();
        URL url = new URL(PROTOCOL, DOMAIN, SLASH);
        Optional<CrawlerInfo<URL>> crawlerInfo = context.createNewCrawler(url);
        Assert.assertTrue(crawlerInfo.isPresent());
        crawlerInfo.ifPresent(urlCrawlerInfo -> {
            Assert.assertEquals(url, urlCrawlerInfo.getStartPoint());
        });
    }

    @Test
    public void createNewCrawler_startPoint_storage() throws Exception {

    }

    @Test
    public void createNewCrawler_startPoint_storage_subscribers() throws Exception {

    }

    @Test
    public void subscribeTo() throws Exception {

    }

    @Test
    public void subscribeTo1() throws Exception {

    }

    @Test
    public void deleteSubscribersFrom() throws Exception {

    }

    @Test
    public void getSubscribersForCrawler() throws Exception {

    }

    @Test
    public void startCrawler() throws Exception {

    }

    @Test
    public void stopCrawler() throws Exception {

    }

    @Test
    public void pauseCrawler() throws Exception {

    }

    @Test
    public void resumeCrawler() throws Exception {

    }

    @Test
    public void getCrawlerByID() throws Exception {

    }

    @Test
    public void getCrawlerByStartPoint() throws Exception {

    }

    @Test
    public void getAllCrawlers() throws Exception {

    }

    @Test
    public void getCrawlersByState() throws Exception {

    }

    @Test
    public void isCrawled() throws Exception {

    }

}