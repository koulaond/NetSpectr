package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.Query;
import core.analysis.query.syntax.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Set;

public class QueryExecutor {

    public QueryResult executeQuery(WebPage webPage, Query query) {
        String html = webPage.getHtml();
        Document page = Jsoup.parse(html);
        Element body = page.body();
        return doExecute(webPage, query.getStatements(), body);
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