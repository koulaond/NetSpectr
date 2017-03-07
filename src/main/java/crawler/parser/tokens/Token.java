package crawler.parser.tokens;

public abstract class Token {
    protected TokenType tokenType;

    protected Token(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public TokenType getType() {
        return tokenType;
    }

    public abstract String html();
}
