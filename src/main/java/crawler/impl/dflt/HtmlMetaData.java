package crawler.impl.dflt;

import java.net.URL;

public class HtmlMetaData {
    private URL url;
    private String sourceHtml;
    private String title;
    private Iterable<URL> outcomes;

    public HtmlMetaData(URL url, String sourceHtml, String title, Iterable<URL> outcomes) {
        this.url = url;
        this.sourceHtml = sourceHtml;
        this.title = title;
        this.outcomes = outcomes;
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

    public URL getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HtmlMetaData metaData = (HtmlMetaData) o;

        if (!getUrl().equals(metaData.getUrl())) return false;
        if (!getSourceHtml().equals(metaData.getSourceHtml())) return false;
        if (getTitle() != null ? !getTitle().equals(metaData.getTitle()) : metaData.getTitle() != null) return false;
        return getOutcomes() != null ? getOutcomes().equals(metaData.getOutcomes()) : metaData.getOutcomes() == null;
    }
}
