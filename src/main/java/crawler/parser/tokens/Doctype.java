package crawler.parser.tokens;

import crawler.parser.HtmlCharacters;
import crawler.parser.HtmlMarks;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static crawler.parser.HtmlCharacters.SPACE;
import static crawler.parser.HtmlMarks.DOCTYPE_END;
import static crawler.parser.HtmlMarks.DOCTYPE_PUBLIC;
import static crawler.parser.HtmlMarks.DOCTYPE_START;

public class Doctype extends Tag {
    private boolean publyc;
    private List<String> urls;

    public Doctype(TagName tagName, boolean publyc, List<String> urls) {
        super(TokenType.DOCTYPE, tagName);
        this.publyc = publyc;
        this.urls = Collections.unmodifiableList(urls);
    }

    @Override
    public String html() {
        return DOCTYPE_START + SPACE +(isPublic() ? DOCTYPE_PUBLIC + SPACE: "") + Objects.requireNonNull(urls).stream().collect(Collectors.joining(" ")) + DOCTYPE_END;
    }

    public boolean isPublic() {
        return publyc;
    }

    public List<String> getUrls() {
        return urls;
    }

    public static DoctypeBuilder builder() {
        return new DoctypeBuilder();
    }

    public static class DoctypeBuilder {
        private TagName tagName;
        private boolean publyc;
        private List<String> urls;

        public DoctypeBuilder setTagName(TagName tagName) {
            this.tagName = tagName;
            return this;
        }

        public DoctypeBuilder setPublic(boolean publyc) {
            this.publyc = publyc;
            return this;
        }

        public DoctypeBuilder addUrl(String url) {
            this.urls.add(url);
            return this;
        }

        public Doctype build() {
            return new Doctype(tagName, publyc, urls);
        }
    }
}
