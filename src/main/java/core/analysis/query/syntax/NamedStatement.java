package core.analysis.query.syntax;

public class NamedStatement<T> extends Statement<T> {
    private String targetName;

    public NamedStatement(StatementTarget statementTarget, Operator<T> operator, String targetName) {
        super(statementTarget, operator);
        this.targetName = targetName;
    }
}
