package core.analysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

public class PreAnalyzer {


    public WebPage preAnalyzePage(URL location, String pageTitle, String html, Set<URL> outcomeUrlsOnDomain, Set<URL> outcomeUrlsOutOfDomain) {

        Document parsedDocument = Jsoup.parse(html);
        Elements scriptElements = parsedDocument.getElementsByTag("script");
        Set<String> scripts = scriptElements.stream().map(Element::html).collect(toSet());

        Elements stylesheetElements = parsedDocument.getElementsByTag("style");
        Set<String> embeddedStylesheets = stylesheetElements.stream().map(Element::html).collect(toSet());

        Elements stylesheetPathElements = parsedDocument.select("link[rel=stylesheet]");
        Set<String> stylesheetPaths = stylesheetPathElements.stream().map(element -> element.attr("href")).collect(toSet());

        Elements tableElements = parsedDocument.getElementsByTag("table");
        Set<String> tables = tableElements.stream().map(Element::html).collect(toSet());

        Elements imageElements = parsedDocument.getElementsByTag("img");
        Set<String> images = imageElements.stream().map(element -> element.attr("src")).collect(toSet());

        return WebPage.builder()
                .id(UUID.randomUUID())
                .sourceUrl(location)
                .pageTitle(pageTitle)
                .html(html)
                .outcomeUrlsOnDomain(outcomeUrlsOnDomain)
                .outcomeUrlsOutOfDomain(outcomeUrlsOutOfDomain)
                .scripts(scripts)
                .embeddedStylesheets(embeddedStylesheets)
                .stylesheetPaths(stylesheetPaths)
                .tables(tables)
                .imageSources(images)
                .build();
    }
}
