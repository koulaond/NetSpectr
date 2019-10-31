package core.analysis.query.syntax;

public class OperableStatement<T> extends Statement {
    protected Operator<T> operator;

    public OperableStatement(StatementTarget target, Operator<T> operator) {
        super(target);
        this.operator = operator;
    }
}
