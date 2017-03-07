package crawler;

import domain.HtmlElement;
import domain.ElementType;
import domain.HtmlPage;

import java.util.List;

public class PageContentProvider {
    List<HtmlElement> getElementsByType(HtmlPage page, ElementType type) {
        return null;
    }

    List<HtmlElement> getElementsWithAttribute(HtmlPage page, String attrName) {
        return null;
    }

    List<HtmlElement> getElementsWithChildren(HtmlPage page) {
        return null;
    }

    List<HtmlElement> getElementsWithText(HtmlPage page) {
        return null;
    }

    HtmlElement getBody() {
        return null;
    }

    HtmlElement getHead() {
        return null;
    }
}
