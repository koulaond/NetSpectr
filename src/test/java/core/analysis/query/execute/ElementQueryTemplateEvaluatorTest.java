package core.analysis.query.execute;

import core.TestUtils;
import core.WebPage;
import core.analysis.query.syntax.ElementQueryTemplate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static core.analysis.query.syntax.Attributes.hasName;
import static core.analysis.query.syntax.Attributes.or;
import static core.analysis.query.syntax.Elements.withName;
import static core.analysis.query.syntax.Operator.endsWith;
import static core.analysis.query.syntax.Operator.startsWith;

class ElementQueryTemplateEvaluatorTest {
    private static Element PARSED_ELEMENT;
    private static WebPage WEB_PAGE;

    @BeforeAll
    static void setup() throws IOException {
        String html = TestUtils.loadHtmlFromResource("ElementQueryTemplateEvaluatorTestHtml.html");
        Document parsedHtml = Jsoup.parse(html);
        PARSED_ELEMENT = parsedHtml.body().getElementsByTag("div").first();
        WEB_PAGE = WebPage.builder()
                .html(parsedHtml.html())
                .build();
    }

    @Test
    void test_element_found() {
        ElementQueryTemplateEvaluator evaluator = new ElementQueryTemplateEvaluator();
        ElementQueryTemplate template = withName("div")
                .hasAttribute(
                        or(
                                hasName("class"),
                                hasName("vlass")
                        )
                )
                .containsSubElement(
                        withName("form")
                                .hasAttribute(hasName("action").hasValue(startsWith("https://earthworm")))
                                .containsSubElement(withName("div").hasAttribute(hasName("class").hasValue(startsWith("input-gr"))))
                );
        ElementQueryResult result = evaluator.evaluate(PARSED_ELEMENT, template, WEB_PAGE);
        boolean success = result.isSuccess();
        Assertions.assertTrue(success);
    }

    @Test
    void test_element_not_found__subelement_form_has_bad_name() {
        ElementQueryTemplateEvaluator evaluator = new ElementQueryTemplateEvaluator();
        ElementQueryTemplate template = withName("div")
                .hasAttribute(
                        or(
                                hasName("class"),
                                hasName("vlass")
                        )
                )
                .containsSubElement(
                        withName("formm")
                                .hasAttribute(hasName("action").hasValue(startsWith("https://earthworm")))
                                .containsSubElement(withName("div").hasAttribute(hasName("class").hasValue(startsWith("input-gr"))))
                );
        ElementQueryResult result = evaluator.evaluate(PARSED_ELEMENT, template, WEB_PAGE);
        boolean success = result.isSuccess();
        Assertions.assertFalse(success);
    }

    @Test
    void test_element_not_found__subelement_form_has_bad_attribute() {
        ElementQueryTemplateEvaluator evaluator = new ElementQueryTemplateEvaluator();
        ElementQueryTemplate template = withName("div")
                .hasAttribute(
                        or(
                                hasName("class"),
                                hasName("vlass")
                        )
                )
                .containsSubElement(
                        withName("form")
                                .hasAttribute(hasName("actionz").hasValue(startsWith("https://earthworm")))
                                .containsSubElement(withName("div").hasAttribute(hasName("class").hasValue(startsWith("input-gr"))))
                );
        ElementQueryResult result = evaluator.evaluate(PARSED_ELEMENT, template, WEB_PAGE);
        boolean success = result.isSuccess();
        Assertions.assertFalse(success);
    }

    @Test
    void test_element_not_found__subsubelement_div_has_bad_attribute_value() {
        ElementQueryTemplateEvaluator evaluator = new ElementQueryTemplateEvaluator();
        ElementQueryTemplate template = withName("div")
                .hasAttribute(
                        or(
                                hasName("class"),
                                hasName("vlass")
                        )
                )
                .containsSubElement(
                        withName("form")
                                .hasAttribute(hasName("actionz").hasValue(startsWith("https://earthworm")))
                                .containsSubElement(withName("div").hasAttribute(hasName("class").hasValue(endsWith("input-gr"))))
                );
        ElementQueryResult result = evaluator.evaluate(PARSED_ELEMENT, template, WEB_PAGE);
        boolean success = result.isSuccess();
        Assertions.assertFalse(success);
    }
}