package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchQueryExecutor {

    public BatchQueryResult executeQueries(Map<WebPage, Query> queries) {
        SinglePageQueryExecutor singlePageExecutor = new SinglePageQueryExecutor();
        Map<WebPage, List<ElementQueryResult>> results = new HashMap<>();
        queries.forEach((webPage, query) -> {
            List<ElementQueryResult> resultForPage = singlePageExecutor.executeQuery(webPage, query);
            if (!resultForPage.isEmpty()) {
                results.put(webPage, resultForPage);
            }
        });
        return new BatchQueryResult(queries.size(), results.size(), results);
    }
}
