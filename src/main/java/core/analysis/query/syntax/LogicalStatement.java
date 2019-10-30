package core.analysis.query.syntax;

import java.util.Set;

public class LogicalStatement extends QueryItem<Set<QueryItem>>{
    private LogicalStatementType type;

    public LogicalStatement(Set<QueryItem> unit, LogicalStatementType type) {
        super(unit);
        this.type = type;
    }

    public LogicalStatement(LogicalStatementType type) {
        this.type = type;
    }
}
