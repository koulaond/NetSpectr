package core.analysis.query.syntax;

import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;

import java.util.HashSet;

@EqualsAndHashCode(callSuper = true)
public class AttributeQueryTemplate extends LogicalStatement<Statement> {

    AttributeQueryTemplate() {
        super(StatementTarget.ATTRIBUTE, LogicalStatementType.AND, new HashSet());
    }

    public AttributeQueryTemplate hasName(String name) {
        subordinates.add(new OperableStatement<>(StatementTarget.ATTRIBUTE_NAME, Operator.exact(name)));
        return this;
    }

    public AttributeQueryTemplate hasName(Operator<String> operator) {
        subordinates.add(new OperableStatement<>(StatementTarget.ATTRIBUTE_NAME, operator));
        return this;
    }

    public AttributeQueryTemplate hasValue(String value) {
        subordinates.add(new OperableStatement<>(StatementTarget.ATTRIBUTE_VALUE, Operator.exact(value)));
        return this;
    }

    public AttributeQueryTemplate hasValue(Operator<String> operator) {
        subordinates.add(new OperableStatement<>(StatementTarget.ATTRIBUTE_VALUE, operator));
        return this;
    }

    public AttributeQueryTemplate or(AttributeQueryTemplate... definitions) {
        subordinates.add(new LogicalStatement(StatementTarget.ATTRIBUTE, LogicalStatementType.OR, Sets.newHashSet(definitions)));
        return this;
    }

    public AttributeQueryTemplate and(AttributeQueryTemplate... definitions) {
        subordinates.add(new LogicalStatement(StatementTarget.ATTRIBUTE, LogicalStatementType.AND, Sets.newHashSet(definitions)));
        return this;
    }
}
