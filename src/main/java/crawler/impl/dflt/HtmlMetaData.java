package crawler.impl.dflt;

import java.net.URL;

public class HtmlMetaData {
    private String sourceHtml;
    private Iterable<URL> outcomes;
    private String title;
    private String baseUri;

    public HtmlMetaData(String sourceHtml, Iterable<URL> outcomes, String title, String baseUri) {
        this.sourceHtml = sourceHtml;
        this.outcomes = outcomes;
        this.title = title;
        this.baseUri = baseUri;
    }

    public String getSourceHtml() {
        return sourceHtml;
    }

    public Iterable<URL> getOutcomes() {
        return outcomes;
    }

    public String getTitle() {
        return title;
    }

    public String getBaseUri() {
        return baseUri;
    }
}
