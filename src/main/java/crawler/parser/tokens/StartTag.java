package crawler.parser.tokens;

import static crawler.parser.HtmlCharacters.*;
import static java.util.Objects.requireNonNull;

public class StartTag extends Tag {
    private Attributes attributes;
    private boolean closed;
    private boolean hasAssociatedClosedTag;

    private StartTag(TagName tagName, Attributes attributes, boolean closed, boolean hasAssociatedClosedTag) {
        super(TokenType.START_TAG, tagName);
        this.attributes = attributes;
        this.closed = closed;
        this.hasAssociatedClosedTag = hasAssociatedClosedTag;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public boolean hasAssociatedClosedTag() {
        return hasAssociatedClosedTag;
    }

    @Override
    public String html() {
        return LEFT_BRACKET + requireNonNull(tagName).html() + SPACE + requireNonNull(attributes).html() + SPACE + (closed ? (SLASH + RIGHT_BRACKET) : (RIGHT_BRACKET));
    }

    public static StartTagBuilder builder() {
        return new StartTagBuilder();
    }

    public static class StartTagBuilder {
        private TagName tagName;
        private Attributes attributes;
        private boolean closed;
        private boolean hasAssociatedClosedTag;

        private StartTagBuilder() {
        }

        public StartTagBuilder setTagName(TagName tagName) {
            this.tagName = tagName;
            return this;
        }

        public StartTagBuilder setAttributes(Attributes attributes) {
            this.attributes = attributes;
            return this;
        }

        public StartTagBuilder setClosed(boolean closed) {
            this.closed = closed;
            return this;
        }

        public StartTagBuilder setHasAssociatedClosedTag(boolean hasAssociatedClosedTag) {
            this.hasAssociatedClosedTag = hasAssociatedClosedTag;
            return this;
        }

        public StartTag build() {
            return new StartTag(tagName, attributes, closed, hasAssociatedClosedTag);
        }
    }
}
