package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.Query;
import core.analysis.query.syntax.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Set;

public class QueryExecutor {

    public QueryResult executeQuery(WebPage webPage, Query query) {
        String html = webPage.getHtml();
        Document page = Jsoup.parse(html);

        Elements body = page.getElementsByTag("body");
        if (body.isEmpty()) {
            throw new IllegalStateException(String.format("Web page with id: %s and URL: %s does not contain <body>.", webPage.getId(), webPage.getSourceUrl()));
        }
        return doExecute(webPage, query.getStatements(), body.get(0));
    }

    private QueryResult doExecute(WebPage webPage, Set<Statement> statements, Element body) {
        OperableStatementEvaluator evaluator = new OperableStatementEvaluator();
        for (Element nestedElement : body.getAllElements()) {
            for (Statement statement : statements) {

            }
        }
        return null;
    }
}