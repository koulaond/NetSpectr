package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.Statement;
import lombok.Getter;
import org.jsoup.nodes.Element;

import java.util.List;

@Getter
public class ElementQueryResult extends QueryResult {
    private final Element element;

    public ElementQueryResult(WebPage sourceWebPage, Statement matchingStatement, List<QueryResult> subElementResults, boolean success, Element element) {
        super(sourceWebPage, matchingStatement, subElementResults, success);
        this.element = element;
    }
}
