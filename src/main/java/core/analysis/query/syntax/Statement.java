package core.analysis.query.syntax;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Statement {

    protected StatementTarget target;

    public Statement(StatementTarget target) {
        this.target = target;
    }
}
