package core.analysis.query;

import core.analysis.query.syntax.Operator;
import core.analysis.query.syntax.Statement;
import core.analysis.query.syntax.StatementTarget;

import java.util.LinkedList;
import java.util.Queue;

public class QueryTemplate {
    private Queue<Statement> statementQueue;

    QueryTemplate() {
        statementQueue = new LinkedList<>();
    }

    public QueryTemplate hasName(Operator<String> operator) {
        addToQueue(new Statement(StatementTarget.ELEMENT, operator));
        return this;
    }

    public QueryTemplate hasAttr(String name, Operator operator) {

        return this;
    }

    public QueryTemplate containsAttr(String name) {

        return this;
    }

    public QueryTemplate hasElement(QueryTemplate elementQueryTemplate) {

        return this;
    }

    public QueryTemplate and(QueryTemplate... andTemplates) {

        return this;
    }

    public QueryTemplate or(QueryTemplate... orTemplates) {

        return this;
    }

    private void addToQueue(Statement statement) {;
        if (!statementQueue.contains(statement)) {
            statementQueue.add(statement);
        }
    }

}
