package crawler.impl.dflt;

import crawler.api.TransitionExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class DefaultTransitionExtractor implements TransitionExtractor<HtmlMetaData, URL> {

    @Override
    public Iterable<URL> extractLinks(HtmlMetaData metaData) {
        return metaData.getOutcomes();
    }
}
