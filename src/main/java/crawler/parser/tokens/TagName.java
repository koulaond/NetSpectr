package crawler.parser.tokens;

public class TagName extends Token{
    protected String value;

    protected TagName(TokenType tokenType) {
        super(tokenType);
    }

    @Override
    public String html() {
        return value;
    }
}
