package core.analysis.query.syntax;

import java.util.Queue;

public class ElementQueryTemplate extends QueryItem<Queue<Statement>> {

    public ElementQueryTemplate withName(Operator<String> operator) {
        addToQueue(new Statement(operator));
        return this;
    }


    public ElementQueryTemplate hasText() {

    }

    public ElementQueryTemplate hasText(Operator<String> operator) {

    }

    public ElementQueryTemplate hasAttribute(AttributeQueryTemplate attributeQueryTemplate) {

    }

    public ElementQueryTemplate hasAttribute(String attrName) {

    }

    public ElementQueryTemplate and(ElementQueryTemplate... templates) {

    }

    public ElementQueryTemplate or(ElementQueryTemplate... templates) {

    }

    public ElementQueryTemplate containsSubElement(ElementQueryTemplate elementQueryTemplate) {

    }

    protected void addToQueue(Statement<QueryItem> statement) {
        if (!unit.contains(statement)) {
            unit.add(statement);
        }
    }
}
