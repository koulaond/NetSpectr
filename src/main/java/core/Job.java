package core;

import com.ondrejkoula.crawler.CrawlerContext;
import lombok.AccessLevel;
import lombok.Getter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter(AccessLevel.PACKAGE)
public class Job {

    private final UUID jobUuid;
    private final UUID crawlerUuid;
    private final String host;
    private final CrawlerEventProcessor eventProcessor;
    private final CrawlerContext crawlerContext;

    public Job(UUID crawlerUuid, String host, CrawlerEventProcessor eventProcessor, CrawlerContext crawlerContext) {
        this.jobUuid = UUID.randomUUID();
        this.crawlerUuid = crawlerUuid;
        this.host = host;
        this.eventProcessor = eventProcessor;
        this.crawlerContext = crawlerContext;
    }

    public JobInfo getInfo() {
        return new JobInfo(jobUuid, crawlerUuid, host, new HashMap<>(eventProcessor.getStructureHandler().getStructure().getWebPageNodes()));
    }

    @Getter
    public static class JobInfo {
        private final UUID jobUuid;
        private final UUID crawlerUuid;
        private final String host;
        private final Map<URL, WebPageNode> collectedWebPageNodes;

        public JobInfo(UUID jobUuid, UUID crawlerUuid, String host, Map<URL, WebPageNode> collectedWebPageNodes) {
            this.jobUuid = jobUuid;
            this.crawlerUuid = crawlerUuid;
            this.host = host;
            this.collectedWebPageNodes = collectedWebPageNodes;
        }
    }
}
