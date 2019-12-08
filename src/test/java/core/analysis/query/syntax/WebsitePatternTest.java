package core.analysis.query.syntax;

import core.analysis.query.syntax.website.WebsitePattern;
import core.analysis.query.syntax.website.WebsitePatternNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WebsitePatternTest {

    @Test
    void testDuplicateConnectionsNotAdded() {
        WebsitePatternNode n0 = new WebsitePatternNode(Query.query(Elements.hasAttribute("div")));
        WebsitePatternNode n1 = new WebsitePatternNode(Query.query(Elements.hasAttribute("class")));
        WebsitePatternNode n2 = new WebsitePatternNode(Query.query(Elements.hasAttribute("table")));
        WebsitePatternNode n3 = new WebsitePatternNode(Query.query(Elements.hasAttribute("span")));
        WebsitePatternNode n4 = new WebsitePatternNode(Query.query(Elements.hasAttribute("canvas")));

        WebsitePattern pattern = new WebsitePattern()
                .addConnection(n0, n1)
                .addConnection(n1, n2, n3)
                .addConnection(n2, n0)
                .addConnection(n3, n4)
                .addConnection(n3, n4)
                .addConnection(n3, n4)
                .addConnection(n3, n4)
                .addConnection(n3, n4)
                .addConnection(n3, n4);
        assertEquals(pattern.getNodes().size(), 5);
    }

    @Test
    void testIsomorphism() {
        WebsitePatternNode n0 = new WebsitePatternNode(Query.query(Elements.hasAttribute("div")));
        WebsitePatternNode n1 = new WebsitePatternNode(Query.query(Elements.hasAttribute("class")));
        WebsitePatternNode n2 = new WebsitePatternNode(Query.query(Elements.hasAttribute("table")));
        WebsitePatternNode n3 = new WebsitePatternNode(Query.query(Elements.hasAttribute("span")));
        WebsitePatternNode n4 = new WebsitePatternNode(Query.query(Elements.hasAttribute("canvas")));

        WebsitePattern first = new WebsitePattern()
                .addConnection(n0, n1)
                .addConnection(n1, n2, n3)
                .addConnection(n2, n0)
                .addConnection(n3, n4);

        WebsitePatternNode n00 = new WebsitePatternNode(Query.query(Elements.hasAttribute("div")));
        WebsitePatternNode n01 = new WebsitePatternNode(Query.query(Elements.hasAttribute("class")));
        WebsitePatternNode n02 = new WebsitePatternNode(Query.query(Elements.hasAttribute("table")));
        WebsitePatternNode n03 = new WebsitePatternNode(Query.query(Elements.hasAttribute("span")));
        WebsitePatternNode n04 = new WebsitePatternNode(Query.query(Elements.hasAttribute("canvas")));
        WebsitePattern second = new WebsitePattern()
                .addConnection(n01, n02)
                .addConnection(n01, n03)
                .addConnection(n00, n01)
                .addConnection(n03, n04)
                .addConnection(n03, n04)
                .addConnection(n03, n04)
                .addConnection(n02, n00);
        assertEquals(first, second);
    }

    @Test
    void testDifference1() {
        WebsitePatternNode n0 = new WebsitePatternNode(Query.query(Elements.hasAttribute("div")));
        WebsitePatternNode n1 = new WebsitePatternNode(Query.query(Elements.hasAttribute("class")));
        WebsitePatternNode n2 = new WebsitePatternNode(Query.query(Elements.hasAttribute("table")));
        WebsitePatternNode n3 = new WebsitePatternNode(Query.query(Elements.hasAttribute("span")));
        WebsitePatternNode n4 = new WebsitePatternNode(Query.query(Elements.hasAttribute("canvas")));

        WebsitePattern first = new WebsitePattern()
                .addConnection(n0, n1)
                .addConnection(n1, n2, n3)
                .addConnection(n2, n0)
                .addConnection(n3, n4);

        WebsitePatternNode n00 = new WebsitePatternNode(Query.query(Elements.hasAttribute("div1")));    // difference
        WebsitePatternNode n01 = new WebsitePatternNode(Query.query(Elements.hasAttribute("class")));
        WebsitePatternNode n02 = new WebsitePatternNode(Query.query(Elements.hasAttribute("table")));
        WebsitePatternNode n03 = new WebsitePatternNode(Query.query(Elements.hasAttribute("span")));
        WebsitePatternNode n04 = new WebsitePatternNode(Query.query(Elements.hasAttribute("canvas")));
        WebsitePattern second = new WebsitePattern()
                .addConnection(n01, n02)
                .addConnection(n01, n03)
                .addConnection(n00, n01)
                .addConnection(n03, n04)
                .addConnection(n03, n04)
                .addConnection(n03, n04)
                .addConnection(n02, n00);
        assertNotEquals(first, second);
    }
}