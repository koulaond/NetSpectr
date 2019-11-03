package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.OperableStatement;
import core.analysis.query.syntax.Operator;
import core.analysis.query.syntax.StatementTarget;
import org.jsoup.nodes.Element;

import static java.util.Collections.emptyList;

public class OperableStatementEvaluator implements StatementEvaluator<OperableStatement, ElementQueryResult> {

    @Override
    public ElementQueryResult evaluate(Element element, OperableStatement statement, WebPage webPage) {
        Operator operator = statement.getOperator();
        StatementTarget target = statement.getTarget();
        ElementQueryResult result;
        switch (target) {
            case ELEMENT_NAME:
                boolean nameMatches = operator.operate(element.tag().getName());
                result = new ElementQueryResult(webPage, statement, emptyList(), nameMatches, element, 0, 0);
                break;
            case ELEMENT_TEXT:
                boolean textMatches = operator.operate(element.text());
                result = new ElementQueryResult(webPage, statement, emptyList(), textMatches, element, 0, 0);
                break;
            default:
                result = new ElementQueryResult(webPage, statement, emptyList(), false, element, 0, 0);
        }
        return result;
    }
}
