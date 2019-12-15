package core;

import com.ondrejkoula.crawler.CrawlerContext;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Job {

    private final UUID uuid;
    private final UUID crawlerUuid;
    private final String host;
    private final CrawlerContext crawlerContext;
    private final WebsiteStructureHandler structureHandler;

    public Job(UUID crawlerUuid, String host, CrawlerContext crawlerContext, WebsiteStructureHandler structureHandler) {
        this.uuid = UUID.randomUUID();
        this.crawlerUuid = crawlerUuid;
        this.host = host;
        this.crawlerContext = crawlerContext;
        this.structureHandler = structureHandler;
    }
}
