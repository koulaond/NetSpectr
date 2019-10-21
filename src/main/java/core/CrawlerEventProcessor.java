package core;

import com.ondrejkoula.crawler.PageDataAcquiredCrawlerEvent;
import core.analysis.PreAnalyzer;
import core.analysis.WebPage;
import core.analysis.WebsiteStructureHandler;

import java.util.function.Consumer;

public class CrawlerEventProcessor implements Consumer<PageDataAcquiredCrawlerEvent> {
    private final PreAnalyzer preAnalyzer;
    private final WebsiteStructureHandler structureHandler;

    public CrawlerEventProcessor(PreAnalyzer preAnalyzer, WebsiteStructureHandler structureHandler) {
        this.preAnalyzer = preAnalyzer;
        this.structureHandler = structureHandler;
    }

    @Override
    public void accept(PageDataAcquiredCrawlerEvent event) {
        // Do not preanalyze if web page already analyzed in past
        if (!structureHandler.isInStructure(event.getLocation())) {
            WebPage page = preAnalyzer.preAnalyzePage(
                    event.getLocation(),
                    event.getDocumentTitle(),
                    event.getDocumentHtml(),
                    event.getOutcomeUrlsOnDomain(),
                    event.getOutcomeUrlsOutOfDomain());
          structureHandler.updateStructure(page);
        }
    }
}
