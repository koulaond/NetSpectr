import com.ondrejkoula.crawler.CrawlerContext;
import com.ondrejkoula.crawler.messages.LoggerMessageService;
import core.JobManager;
import core.WebsiteStructureHandler;
import core.analysis.PreAnalyzer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;

public class Test {
    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        CrawlerContext crawlerContext = new CrawlerContext(() -> UUID.randomUUID(), new LoggerMessageService());
        JobManager jobManager = new JobManager(crawlerContext);
        PreAnalyzer preAnalyzer = new PreAnalyzer();
        WebsiteStructureHandler structureHandler = new WebsiteStructureHandler();
        UUID jobUuid = jobManager.createJob(new URL("https://virtii.com/de/blog"), preAnalyzer, structureHandler,
                newHashSet(event -> System.out.println(format("Web page %s added to structure %s", event.getWebPageAdded().getSourceUrl(), event.getDomain()))));
        System.out.println(jobUuid);

        jobManager.startJob(jobUuid);
        Thread.sleep(5000);
        JobManager.JobInfo newJobInfo = jobManager.getJobInfo(jobUuid);
        System.out.println();
    }
}
