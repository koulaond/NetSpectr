package domain;

import java.net.URL;

public class HtmlPage {
private URL baseURL;
    private String content;
    private HtmlElement rootElement;

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

    public HtmlElement getRootElement() {
        return rootElement;
    }

    public void setRootElement(HtmlElement rootElement) {
        this.rootElement = rootElement;
    }
}
