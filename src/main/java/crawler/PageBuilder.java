package crawler;

import domain.ElementType;
import domain.HtmlElement;
import domain.HtmlPage;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PageBuilder {
    private String htmlContent;
    private String baseURL;

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public HtmlPage build() {
        if(htmlContent == null || baseURL == null){
            throw new IllegalArgumentException(String.format("Some of the mandatory values is not set.(htmlContent: %s, baseURL: %s)", htmlContent, baseURL));
        }
        HtmlPage page = new HtmlPage();

        return page;
    }

    static class SimpleHtmlElement  {
        private ElementType type;
        private String text;
        private Map<String, Object> attributes;
        private Set<HtmlElement> children;

        public ElementType getType() {
            return type;
        }

        void setType(ElementType type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        void setText(String text) {
            this.text = text;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        void setAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        public Set<HtmlElement> getChildren() {
            return children;
        }

        void setChildren(Set<HtmlElement> children) {
            this.children = children;
        }
    }

    static class SimpleHtmlPage {

        private URL baseURL;
        private String content;
        private SimpleHtmlElement rootElement;

        public URL getBaseURL() {
            return baseURL;
        }

        public void setBaseURL(URL baseURL) {
            this.baseURL = baseURL;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public SimpleHtmlElement getRootElement() {
            return rootElement;
        }

        public void setRootElement(SimpleHtmlElement rootElement) {
            this.rootElement = rootElement;
        }

    }
}
