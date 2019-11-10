package core.analysis.query;

import core.WebPage;
import core.analysis.query.execute.QueryExecutor;
import core.analysis.query.syntax.Query;
import core.analysis.query.syntax.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        ElementQueryTemplate fistTemplate = Elements.withName("div");

        ElementQueryTemplate secondTemplate = Elements.hasAttribute(
                Attributes
                        .hasValue(
                                Operator.endsWith(".ondrejkoula")
                        )
                        .or(
                                Attributes.or(
                                        Attributes.hasValue("koala"),
                                        Attributes.hasValue("koula")
                                ),
                                Attributes.hasName(Operator.exact("p:class")),
                                Attributes.hasName(Operator.exact("class"))
                        )
        );
        Query firstQuery = Query.query(fistTemplate);
        Query secondQuery = Query.query(secondTemplate);

        System.out.println(firstQuery.equals(secondQuery));
        System.out.println(firstQuery.hashCode() == secondQuery.hashCode());

        Document document = Jsoup.connect("https://virtii.com/de/blog").get();
        WebPage page = WebPage.builder()
                .html(document.outerHtml())
                .build();
        QueryExecutor executor = new QueryExecutor();
        //QueryResult queryResult = executor.executeQuery(page, firstQuery);
        System.out.println();
    }
}
