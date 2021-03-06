package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.ElementQueryTemplate;
import core.analysis.query.syntax.Query;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SinglePageQueryExecutor {

    public List<ElementQueryResult> executeQuery(WebPage webPage, Query query) {
        String html = webPage.getHtml();
        Document page = Jsoup.parse(html);
        Element body = page.body();

        ElementQueryTemplate template = query.getTemplate();
        StatementEvaluator evaluator = EvaluatorProvider.getEvaluator(template);
        List<ElementQueryResult> queryResults = new ArrayList<>();

        Elements allElements = body.getAllElements();
        allElements.forEach(element -> {
            ElementQueryResult childResult = evaluator.evaluate(element, template, webPage);
            if (childResult.isSuccess()) {
                queryResults.add(childResult);
            }
        });
        List<ElementQueryResult> flattenResults = queryResults.stream()
                .flatMap(queryResult -> queryResult.getSubElementResults().stream())
                .collect(toList());
        return flattenResults;
    }
}