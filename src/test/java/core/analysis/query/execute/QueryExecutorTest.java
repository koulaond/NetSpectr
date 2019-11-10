package core.analysis.query.execute;

import core.TestUtils;
import core.WebPage;
import core.analysis.query.syntax.Attributes;
import core.analysis.query.syntax.Elements;
import core.analysis.query.syntax.Query;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class QueryExecutorTest {
    private static Element PARSED_ELEMENT;
    private static WebPage WEB_PAGE;

    @BeforeAll
    static void setup() throws IOException {
        String html = TestUtils.loadHtmlFromResource("webPage.html");
        Document parsedHtml = Jsoup.parse(html);
        PARSED_ELEMENT = parsedHtml.body();
        WEB_PAGE = WebPage.builder()
                .html(parsedHtml.html())
                .build();
    }

    @Test
    void test__find_composite_div() {
        QueryExecutor executor = new QueryExecutor();
        Query query = Query.query(
                Elements.withName("div")
                .hasAttribute(Attributes.hasName("class").hasValue("jumbotron jumbotron-fluid about"))
        );
        List<QueryResult> queryResults = executor.executeQuery(WEB_PAGE, query);
        Assertions.assertThat(queryResults).hasSize(PARSED_ELEMENT.getElementsByTag("div").size());
    }
    @Test
    void test__find_all_divs() {
        QueryExecutor executor = new QueryExecutor();
        Query query = Query.query(Elements.withName("div"));
        List<QueryResult> queryResults = executor.executeQuery(WEB_PAGE, query);
        Assertions.assertThat(queryResults).hasSize(PARSED_ELEMENT.getElementsByTag("div").size());
    }
}