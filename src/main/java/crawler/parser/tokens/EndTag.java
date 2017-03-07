package crawler.parser.tokens;

import static crawler.parser.HtmlCharacters.*;
import static crawler.parser.HtmlCharacters.RIGHT_BRACKET;
import static java.util.Objects.requireNonNull;

public class EndTag extends Tag {

    protected EndTag(TagName tagName) {
        super(TokenType.END_TAG, tagName);
    }

    @Override
    public String html() {
        return LEFT_BRACKET + SLASH + requireNonNull(tagName).html() + RIGHT_BRACKET;
    }

    public static EndTagBuilder builder(){
        return new EndTagBuilder();
    }

    private static class EndTagBuilder {
        private TagName tagName;

        public EndTagBuilder setTagName(TagName tagName) {
            this.tagName = tagName;
            return this;
        }

        public EndTag build() {
            return new EndTag(tagName);
        }
    }
}
