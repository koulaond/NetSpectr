package core.analysis.query.syntax;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Statement<T> {

    protected StatementTarget statementTarget;
    protected Operator<T> operator;

    public Statement(StatementTarget statementTarget, Operator<T> operator) {
        this.statementTarget = statementTarget;
        this.operator = operator;
    }
}
