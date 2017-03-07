package crawler.parser.tokens;

public abstract class Tag extends Token{
    protected TagName tagName;

    protected Tag(TokenType tokenType, TagName tagName) {
        super(tokenType);
        this.tagName = tagName;
    }

    public TagName getTagName() {
        return tagName;
    }
}
