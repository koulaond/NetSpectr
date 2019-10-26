package core;

import com.ondrejkoula.crawler.PageDataAcquiredCrawlerEvent;
import core.analysis.PreAnalyzer;
import core.event.StructureUpdatedEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import utils.WebPageUtils;

import java.net.URL;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toSet;

@Getter(AccessLevel.PACKAGE)
public class CrawlerEventProcessor implements Consumer<PageDataAcquiredCrawlerEvent> {
    private final UUID jobUuid;
    private final PreAnalyzer preAnalyzer;
    private final WebsiteStructureHandler structureHandler;
    private final Set<Consumer<StructureUpdatedEvent>> consumers;

    @NonNull
    public CrawlerEventProcessor(UUID jobUuid, PreAnalyzer preAnalyzer, WebsiteStructureHandler structureHandler, @NonNull Set<Consumer<StructureUpdatedEvent>> consumers) {
        this.jobUuid = jobUuid;
        this.preAnalyzer = preAnalyzer;
        this.structureHandler = structureHandler;
        this.consumers = consumers;
    }

    @Override
    public void accept(PageDataAcquiredCrawlerEvent event) {
        URL fixedlocation = WebPageUtils.cleanUrl(event.getLocation());

        Set<URL> outcomeUrlsOnDomain = event.getOutcomeUrlsOnDomain()
                .stream()
                .map(WebPageUtils::cleanUrl)
                .collect(toSet());

        Set<URL> outcomeUrlsOutOfDomain = event.getOutcomeUrlsOutOfDomain()
                .stream()
                .map(WebPageUtils::cleanUrl)
                .collect(toSet());

        WebPage page = preAnalyzer.preAnalyzePage(
                fixedlocation,
                event.getDocumentTitle(),
                event.getDocumentHtml(),
                outcomeUrlsOnDomain,
                outcomeUrlsOutOfDomain);
        structureHandler.updateStructure(page);
        WebsiteStructure structure = structureHandler.getStructure();
        StructureUpdatedEvent structureUpdatedEvent = new StructureUpdatedEvent(jobUuid, page, Collections.unmodifiableMap(structure.getWebPageNodes()), structure.getDomain());
        this.consumers.forEach(consumer -> {
            consumer.accept(structureUpdatedEvent);
        });
    }
}
