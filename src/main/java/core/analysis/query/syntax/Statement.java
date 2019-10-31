package core.analysis.query.syntax;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Statement {

    protected StatementTarget target;

    public Statement(StatementTarget target) {
        this.target = target;
    }
}
