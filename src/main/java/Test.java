import com.ondrejkoula.crawler.CrawlerContext;
import com.ondrejkoula.crawler.messages.LoggerMessageService;
import core.Job;
import core.JobManager;
import core.WebsiteStructureHandler;
import core.analysis.PreAnalyzer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static java.lang.String.format;

public class Test {
    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        CrawlerContext crawlerContext = new CrawlerContext(() -> UUID.randomUUID(), new LoggerMessageService());
        JobManager jobManager = new JobManager(crawlerContext);
        PreAnalyzer preAnalyzer = new PreAnalyzer();
        WebsiteStructureHandler structureHandler = new WebsiteStructureHandler();
        Job.JobInfo jobInfo = jobManager.createJob(new URL("https://virtii.com/de/blog"), preAnalyzer, structureHandler,
                event -> System.out.println(format("Job (Crawler ID: %s) changed state: %s -> %s", event.getCrawlerUuid(), event.getOldState(), event.getNewState())));
        System.out.println(jobInfo);

        jobManager.startJob(jobInfo.getJobUuid());
        Thread.sleep(5000);
        Job.JobInfo newJobInfo = jobManager.getJobInfo(jobInfo.getJobUuid());
        System.out.println();
    }
}
