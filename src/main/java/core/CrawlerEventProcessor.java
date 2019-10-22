package core;

import com.ondrejkoula.crawler.PageDataAcquiredCrawlerEvent;
import core.analysis.PreAnalyzer;
import lombok.AccessLevel;
import lombok.Getter;
import utils.WebPageUtils;

import java.net.URL;
import java.util.Set;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toSet;

@Getter(AccessLevel.PACKAGE)
public class CrawlerEventProcessor implements Consumer<PageDataAcquiredCrawlerEvent> {
    private final PreAnalyzer preAnalyzer;
    private final WebsiteStructureHandler structureHandler;

    public CrawlerEventProcessor(PreAnalyzer preAnalyzer, WebsiteStructureHandler structureHandler) {
        this.preAnalyzer = preAnalyzer;
        this.structureHandler = structureHandler;
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

    }
}
