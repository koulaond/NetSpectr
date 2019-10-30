package core.analysis.query.syntax;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Statement<T> {

    protected Operator<T> operator;

    public Statement(Operator<T> operator) {
        this.operator = operator;
    }
}
