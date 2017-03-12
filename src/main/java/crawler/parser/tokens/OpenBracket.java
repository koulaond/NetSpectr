package crawler.parser.tokens;

public class OpenBracket extends Text{
    protected OpenBracket() {
        super(TokenType.BRACKET_OPEN, "<");
    }
}
