package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.Statement;
import lombok.Getter;

import java.util.List;

@Getter
public class QueryResult {
    protected final WebPage sourceWebPage;
    protected final Statement matchingStatement;
    protected final List<QueryResult> subElementResults;
    protected final boolean success;

    public QueryResult(WebPage sourceWebPage, Statement matchingStatement, List<QueryResult> subElementResults, boolean success) {
        this.sourceWebPage = sourceWebPage;
        this.matchingStatement = matchingStatement;
        this.subElementResults = subElementResults;
        this.success = success;
    }
}
