package core.analysis.query.execute;

import core.TestUtils;
import core.WebPage;
import core.analysis.query.syntax.Attributes;
import core.analysis.query.syntax.Elements;
import core.analysis.query.syntax.Query;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static core.analysis.query.syntax.Operator.*;
import static org.assertj.core.api.Assertions.assertThat;

class QueryExecutorTest {
    public static final String ATTR_CLASS = "class";
    public static final String ATTR_HREF = "href";
    public static final String ATTR_HREF_ASSETS_IMAGES__STARTING_WITH = "assets/images/";
    public static final String ATTR_CLASS_VALUE_JUMBOTRON__STARTING_WITH = "jumbotron";
    public static final String ATTR_CLASS_VALUE_CONTAINER = "container";
    public static final String ATTR_CLASS_VALUE_ROW = "row";
    public static final String ATTR_CLASS_VALUE_COL_MD_2 = "col-md-2";
    public static final String ATTR_CLASS_VALUE_COL_MD_8 = "col-md-8";
    public static final String ELEMENT_A = "a";
    public static final String ELEMENT_DIV = "div";
    public static final String ELEMENT_FORM = "form";
    public static final String ELEMENT_INPUT = "input";
    public static final String SPAN = "span";
    public static final String ELEMENT_SPAN = SPAN;
    public static final String ATTR_CLASS_VALUE_MB_3_BOX_SHADOW__ENDS_WITH = "mb-3 box-shadow";
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
        String attr_class_value = "jumbotron jumbotron-fluid about";
        String attr_class = "class";
        String element_div = "div";
        Query query = Query.query(
                Elements.withName(element_div)
                        .hasAttribute(Attributes.hasName(attr_class).hasValue(attr_class_value))
        );
        List<ElementQueryResult> queryResults = executor.executeQuery(WEB_PAGE, query);
        assertThat(queryResults)
                .hasSize(1)
                .first()
                .isNotNull()
                .satisfies(queryResult -> assertThat(queryResult.getElement())
                        .isNotNull()
                        .satisfies(element -> assertThat(element.tag().getName())
                                .isEqualTo(element_div)
                        )
                        .satisfies(element -> assertThat(element.attr(attr_class))
                                .isNotEmpty()
                                .isEqualTo(attr_class_value)
                        )
                );
    }

    @Test
    void test__find_all_divs() {
        QueryExecutor executor = new QueryExecutor();
        Query query = Query.query(Elements.withName("div"));
        List<ElementQueryResult> queryResults = executor.executeQuery(WEB_PAGE, query);
        assertThat(queryResults).hasSize(PARSED_ELEMENT.getElementsByTag("div").size());
    }

    @Test
    void test__find_specific_divs__containing_class_attr_starting_to_col() {
        QueryExecutor executor = new QueryExecutor();
        Query query = Query.query(
                Elements.withName("div")
                        .hasAttribute(
                                Attributes.hasName("class")
                                        .hasValue(startsWith("col-md"))
                        )
        );
        List<ElementQueryResult> queryResults = executor.executeQuery(WEB_PAGE, query);
        assertThat(queryResults).hasSize(PARSED_ELEMENT.getElementsByAttributeValueStarting("class", "col-md").size());
    }

    @Test
    void test__really_brutal_thor_hammer_and_kraken__positive() {
        QueryExecutor executor = new QueryExecutor();
        Query query = Query.query(
                Elements.withName(ELEMENT_DIV)
                        .hasAttribute(
                                Attributes.hasName(ATTR_CLASS)
                                        .hasValue(startsWith(ATTR_CLASS_VALUE_JUMBOTRON__STARTING_WITH))
                        )
                        .containsSubElement(
                                Elements.withName(ELEMENT_DIV)
                                        .hasAttribute(
                                                Attributes.hasName(ATTR_CLASS).hasValue(startsWith(ATTR_CLASS_VALUE_CONTAINER))
                                        )
                                        .containsSubElement(
                                                Elements.withName(ELEMENT_DIV)
                                                        .hasAttribute(
                                                                Attributes.hasName(ATTR_CLASS).hasValue(ATTR_CLASS_VALUE_ROW)
                                                        )
                                                        .containsSubElement(
                                                                Elements.withName(ELEMENT_DIV)
                                                                        .hasAttribute(
                                                                                Attributes.hasName(ATTR_CLASS).hasValue(ATTR_CLASS_VALUE_COL_MD_2)
                                                                        )
                                                                        .containsSubElement(
                                                                                Elements.withName(ELEMENT_DIV)
                                                                                        .hasAttribute(
                                                                                                Attributes.hasName(ATTR_CLASS).hasValue(endsWith(ATTR_CLASS_VALUE_MB_3_BOX_SHADOW__ENDS_WITH))
                                                                                        )
                                                                                        .containsSubElement(
                                                                                                Elements.withName(ELEMENT_A)
                                                                                                        .hasAttribute(
                                                                                                                Attributes.hasName(exact(ATTR_HREF)).hasValue(startsWith(ATTR_HREF_ASSETS_IMAGES__STARTING_WITH))
                                                                                                        )
                                                                                        )
                                                                        )
                                                        )
                                                        .containsSubElement(
                                                                Elements.withName(ELEMENT_DIV)
                                                                        .hasAttribute(
                                                                                Attributes.hasName(ATTR_CLASS).hasValue(ATTR_CLASS_VALUE_COL_MD_8)
                                                                        )
                                                        )

                                        )
                        )
        );
        List<ElementQueryResult> queryResults = executor.executeQuery(WEB_PAGE, query);
        assertThat(queryResults).hasSize(1);
    }

    @Test
    void test__really_brutal_thor_hammer_and_kraken__negative_href_bad_operator() {
        QueryExecutor executor = new QueryExecutor();
        Query query = Query.query(
                Elements.withName(ELEMENT_DIV)
                        .hasAttribute(
                                Attributes.hasName(ATTR_CLASS)
                                        .hasValue(startsWith(ATTR_CLASS_VALUE_JUMBOTRON__STARTING_WITH))
                        )
                        .containsSubElement(
                                Elements.withName(ELEMENT_DIV)
                                        .hasAttribute(
                                                Attributes.hasName(ATTR_CLASS).hasValue(startsWith(ATTR_CLASS_VALUE_CONTAINER))
                                        )
                                        .containsSubElement(
                                                Elements.withName(ELEMENT_DIV)
                                                        .hasAttribute(
                                                                Attributes.hasName(ATTR_CLASS).hasValue(ATTR_CLASS_VALUE_ROW)
                                                        )
                                                        .containsSubElement(
                                                                Elements.withName(ELEMENT_DIV)
                                                                        .hasAttribute(
                                                                                Attributes.hasName(ATTR_CLASS).hasValue(ATTR_CLASS_VALUE_COL_MD_2)
                                                                        )
                                                                        .containsSubElement(
                                                                                Elements.withName(ELEMENT_DIV)
                                                                                        .hasAttribute(
                                                                                                Attributes.hasName(ATTR_CLASS).hasValue(endsWith(ATTR_CLASS_VALUE_MB_3_BOX_SHADOW__ENDS_WITH))
                                                                                        )
                                                                                        .containsSubElement(
                                                                                                Elements.withName(ELEMENT_A)
                                                                                                        .hasAttribute(      // false                          endsWith returns false
                                                                                                                Attributes.hasName(exact(ATTR_HREF)).hasValue(endsWith(ATTR_HREF_ASSETS_IMAGES__STARTING_WITH))
                                                                                                        )
                                                                                        )
                                                                        )
                                                        )
                                                        .containsSubElement(
                                                                Elements.withName(ELEMENT_DIV)
                                                                        .hasAttribute(
                                                                                Attributes.hasName(ATTR_CLASS).hasValue(ATTR_CLASS_VALUE_COL_MD_8)
                                                                        )
                                                        )

                                        )
                        )
        );
        List<ElementQueryResult> queryResults = executor.executeQuery(WEB_PAGE, query);
        assertThat(queryResults).hasSize(0);
    }

    @Test
    void test__really_brutal_thor_hammer_and_kraken__negative_() {
        QueryExecutor executor = new QueryExecutor();
        Query query = Query.query(
                Elements.withName(ELEMENT_DIV)
                        .hasAttribute(
                                Attributes.hasName(ATTR_CLASS)
                                        .hasValue(startsWith(ATTR_CLASS_VALUE_JUMBOTRON__STARTING_WITH))
                        )
                        .containsSubElement(
                                Elements.withName(ELEMENT_DIV)
                                        .hasAttribute(
                                                Attributes.hasName(ATTR_CLASS).hasValue(startsWith(ATTR_CLASS_VALUE_CONTAINER))
                                        )
                                        .containsSubElement(
                                                Elements.withName(ELEMENT_DIV)
                                                        .hasAttribute(
                                                                Attributes.hasName(ATTR_CLASS).hasValue(ATTR_CLASS_VALUE_ROW)
                                                        )
                                                        .containsSubElement(
                                                                Elements.withName(ELEMENT_DIV + "corrupt")  // false
                                                                        .hasAttribute(
                                                                                Attributes.hasName(ATTR_CLASS).hasValue(ATTR_CLASS_VALUE_COL_MD_2)
                                                                        )
                                                                        .containsSubElement(
                                                                                Elements.withName(ELEMENT_DIV)
                                                                                        .hasAttribute(
                                                                                                Attributes.hasName(ATTR_CLASS).hasValue(endsWith(ATTR_CLASS_VALUE_MB_3_BOX_SHADOW__ENDS_WITH))
                                                                                        )
                                                                                        .containsSubElement(
                                                                                                Elements.withName(ELEMENT_A)
                                                                                                        .hasAttribute(
                                                                                                                Attributes.hasName(exact(ATTR_HREF)).hasValue(startsWith(ATTR_HREF_ASSETS_IMAGES__STARTING_WITH))
                                                                                                        )
                                                                                        )
                                                                        )
                                                        )
                                                        .containsSubElement(
                                                                Elements.withName(ELEMENT_DIV)
                                                                        .hasAttribute(
                                                                                Attributes.hasName(ATTR_CLASS).hasValue(ATTR_CLASS_VALUE_COL_MD_8)
                                                                        )
                                                        )

                                        )
                        )
        );
        List<ElementQueryResult> queryResults = executor.executeQuery(WEB_PAGE, query);
        assertThat(queryResults).hasSize(0);
    }
}