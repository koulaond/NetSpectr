package core.analysis.query.syntax;

import java.util.Set;

public class LogicalStatement<ST extends Statement> extends Statement {
    protected LogicalStatementType type;
    protected Set<ST> subordinates;

    public LogicalStatement(StatementTarget target, LogicalStatementType type, Set<ST> subordinates) {
        super(target);
        this.type = type;
        this.subordinates = subordinates;
    }
}
