package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.LogicalStatement;
import core.analysis.query.syntax.Statement;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static core.analysis.query.syntax.LogicalStatementType.AND;
import static core.analysis.query.syntax.LogicalStatementType.OR;
import static java.util.Collections.emptyList;

public class LogicalStatementEvaluator implements StatementEvaluator<LogicalStatement<Statement>, ElementQueryResult> {

    @Override
    public ElementQueryResult evaluate(Element element, LogicalStatement<Statement> statement, WebPage webPage) {
        Set<Statement> subordinates = statement.getSubordinates();
        List<ElementQueryResult> subElementResults = new ArrayList<>();
        for (Statement subStatement : subordinates) {
            StatementEvaluator subEvaluator = EvaluatorProvider.getEvaluator(subStatement);
            ElementQueryResult subQueryResult = subEvaluator.evaluate(element, subStatement, webPage);
            // If type is AND then break after first unsuccessful evaluation
            if (AND.equals(statement.getType())) {
                if (subQueryResult.isSuccess()) {
                    subElementResults.add(subQueryResult);
                } else {
                    return new ElementQueryResult(webPage, statement, emptyList(), false, element);
                }
            } else {
                subElementResults.add(subQueryResult);
            }
        }
        if (OR.equals(statement.getType())) {
            for (ElementQueryResult subResult : subElementResults) {
                if (subResult.isSuccess()) {
                    return new ElementQueryResult(webPage, statement, subElementResults, true, element);
                }
            }
            return new ElementQueryResult(webPage, statement, subElementResults, false, element);
        } else {
            // Is AND and all results are successful
            return new ElementQueryResult(webPage, statement, subElementResults, true, element);
        }
    }
}
