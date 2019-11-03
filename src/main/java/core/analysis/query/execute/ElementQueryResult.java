package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.Statement;
import lombok.Getter;
import org.jsoup.nodes.Element;

import java.util.List;

@Getter
public class ElementQueryResult extends QueryResult {
    private final Element element;
    private final int positionStart;
    private final int positionEnd;

    public ElementQueryResult(WebPage sourceWebPage, Statement matchingStatement, List<QueryResult> subElementResults, boolean success, Element element, int positionStart, int positionEnd) {
        super(sourceWebPage, matchingStatement, subElementResults, success);
        this.element = element;
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
    }
}
