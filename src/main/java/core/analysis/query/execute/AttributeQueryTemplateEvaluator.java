package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.AttributeQueryTemplate;
import core.analysis.query.syntax.Statement;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

import java.util.Set;

public class AttributeQueryTemplateEvaluator implements StatementEvaluator<AttributeQueryTemplate, ElementQueryResult> {
    @Override
    public ElementQueryResult evaluate(Element element, AttributeQueryTemplate statement, WebPage webPage) {
        Attributes attributes = element.attributes();
        Set<Statement> subStatements = statement.getSubordinates();
        AttributeQueryTreeResolver resolver = new AttributeQueryTreeResolver();
        for (Attribute attribute : attributes) {
            boolean resultForAttribute = resolver.resolveAttribute(attribute, statement);
        }
        return null;
    }
}
