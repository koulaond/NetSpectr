package core.analysis.query.execute;

import core.TestUtils;
import core.WebPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    void test() {

        ElementQueryTemplateEvaluator evaluator = new ElementQueryTemplateEvaluator();
//        ElementQueryTemplate template = Elements.hasAttribute()
//        evaluator.evaluate()
    }
}