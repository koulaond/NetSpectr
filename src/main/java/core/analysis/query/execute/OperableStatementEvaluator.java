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
        switch (target){
            case ELEMENT_NAME:
                for (Element searchedElement : element.getAllElements()) {
                    boolean nameMatches = operator.operate(searchedElement.tag().getName());
                    if (nameMatches) {
                        ElementQueryResult result = new ElementQueryResult(webPage, statement, emptyList(), 0,0);
                        return result;
                    }
                }
        }

        return null;
    }
}
