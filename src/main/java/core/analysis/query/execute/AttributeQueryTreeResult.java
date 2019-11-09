package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.Statement;
import lombok.Getter;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import java.util.List;

@Getter
public class AttributeQueryTreeResult extends ElementQueryResult {

    private final Attribute foundAttribute;

    public AttributeQueryTreeResult(WebPage sourceWebPage,
                                    Statement matchingStatement,
                                    List<QueryResult> subElementResults,
                                    boolean success,
                                    Element element,
                                    int positionStart,
                                    int positionEnd,
                                    Attribute foundAttribute) {
        super(sourceWebPage, matchingStatement, subElementResults, success, element, positionStart, positionEnd);
        this.foundAttribute = foundAttribute;
    }
}
