package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.Statement;
import lombok.Getter;

import java.util.List;

@Getter
public class ElementQueryResult extends QueryResult {
    private int positionStart;
    private int positionEnd;

    public ElementQueryResult(WebPage sourceWebPage, Statement matchingStatement, List<QueryResult> subElementResults, int positionStart, int positionEnd) {
        super(sourceWebPage, matchingStatement, subElementResults);
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
    }
}
