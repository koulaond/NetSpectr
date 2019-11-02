package core.analysis.query.syntax;

import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;

import java.util.HashSet;

@EqualsAndHashCode(callSuper = true)
public class ElementQueryTemplate extends LogicalStatement<Statement> {

    ElementQueryTemplate() {
        super(StatementTarget.ELEMENT_STRUCTURE, LogicalStatementType.AND, new HashSet<>());
    }

    public ElementQueryTemplate withName(String name) {
        return withName(Operator.exact(name));
    }

    public ElementQueryTemplate withName(Operator<String> operator) {
        subordinates.add(new OperableStatement<>(StatementTarget.ELEMENT_NAME, operator));
        return this;
    }

    public ElementQueryTemplate hasText() {
        subordinates.add(new OperableStatement<>(StatementTarget.ELEMENT_NAME, Operator.any()));
        return this;
    }

    public ElementQueryTemplate hasText(Operator<String> operator) {
        subordinates.add(new OperableStatement<>(StatementTarget.ELEMENT_NAME, operator));
        return this;
    }

    public ElementQueryTemplate hasAttribute(String attrName) {
        return hasAttribute(Attributes.hasName(attrName));
    }

    public ElementQueryTemplate hasAttribute(AttributeQueryTemplate attributeQueryTemplate) {
        subordinates.add(attributeQueryTemplate);
        return this;
    }

    public ElementQueryTemplate and(ElementQueryTemplate... templates) {
        subordinates.add(new LogicalStatement(StatementTarget.ATTRIBUTE, LogicalStatementType.AND, Sets.newHashSet(templates)));
        return this;
    }

    public ElementQueryTemplate or(ElementQueryTemplate... templates) {
        subordinates.add(new LogicalStatement(StatementTarget.ATTRIBUTE, LogicalStatementType.OR, Sets.newHashSet(templates)));
        return this;
    }

    public ElementQueryTemplate containsSubElement(ElementQueryTemplate elementQueryTemplate) {
        subordinates.add(elementQueryTemplate);
        return this;
    }
}
