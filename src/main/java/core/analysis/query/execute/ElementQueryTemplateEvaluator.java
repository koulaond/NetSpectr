package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.ElementQueryTemplate;
import core.analysis.query.syntax.Statement;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ElementQueryTemplateEvaluator implements StatementEvaluator<ElementQueryTemplate, ElementQueryResult> {
    @Override
    public ElementQueryResult evaluate(Element element, ElementQueryTemplate statement, WebPage webPage) {
        Set<Statement> subordinates = statement.getSubordinates();
        List<QueryResult> subElementResults = new ArrayList<>();
        Elements subElements = element.getAllElements();
        ElementQueryResult resultForAnyElement = null;
        Statement lastSubStatement = null;
        LOOP_ELEMENT:
        for (Element subElement : subElements) {
            for (Statement subStatement : subordinates) {
                lastSubStatement = subStatement;
                StatementEvaluator subEvaluator = EvaluatorProvider.getEvaluator(subStatement);
                QueryResult subElementResult = subEvaluator.evaluate(subElement, subStatement, webPage);
                if (!subElementResult.isSuccess()) {
                    subElementResults.clear();
                    continue LOOP_ELEMENT;
                } else {
                    subElementResults.add(subElementResult);
                }
            }
            // All sub-element results are not empty (were not cleared), sub-element found
            if (!subElementResults.isEmpty()) {
                resultForAnyElement = new ElementQueryResult(webPage, lastSubStatement, subElementResults, true, subElement, 0, 0);
                break;
            }
        }
        if (resultForAnyElement == null) {
            resultForAnyElement = new ElementQueryResult(webPage, lastSubStatement, subElementResults, false, null, 0, 0);
        }
        return resultForAnyElement;
    }
}
