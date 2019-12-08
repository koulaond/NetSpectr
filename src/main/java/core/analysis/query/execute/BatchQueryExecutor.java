package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BatchQueryExecutor {

    public BatchQueryResult executeQueries(Set<WebPage> webPages, Query query) {
        SinglePageQueryExecutor singlePageExecutor = new SinglePageQueryExecutor();
        Map<WebPage, List<ElementQueryResult>> results = new HashMap<>();
        webPages.forEach(webPage -> {
            List<ElementQueryResult> resultForPage = singlePageExecutor.executeQuery(webPage, query);
            if (!resultForPage.isEmpty()) {
                results.put(webPage, resultForPage);
            }
        });
        return new BatchQueryResult(webPages.size(), results.size(), results);
    }
}
