package core.analysis.query.execute;

import core.WebPage;
import core.analysis.query.syntax.AttributeQueryTemplate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

public class AttributeQueryTemplateEvaluator implements StatementEvaluator<AttributeQueryTemplate, ElementQueryResult> {
    @Override
    public AttributeQueryTreeResult evaluate(Element element, AttributeQueryTemplate statement, WebPage webPage) {
        Attributes attributes = element.attributes();
        AttributeQueryTreeResolver resolver = new AttributeQueryTreeResolver();
        for (Attribute attribute : attributes) {
            boolean resultForAttribute = resolver.resolveAttribute(attribute, statement);
            if (resultForAttribute) {
               return new AttributeQueryTreeResult(webPage, statement, null, true, element, 0, 0, attribute);
            }
        }
        return new AttributeQueryTreeResult(webPage, statement, null, false, element, 0, 0, null);
    }
}
