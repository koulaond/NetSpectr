package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.Statement;
import org.jsoup.nodes.Element;

public interface StatementEvaluator<S extends Statement, R extends QueryResult> {

    R evaluate(Element element, S statement, WebPage webPage);
}
