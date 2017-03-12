package crawler.parser.tokens;

/**
 * Created by Koula on 12.3.2017.
 */
public class CloseBracket extends Text{

    protected CloseBracket() {
        super(TokenType.BRACKET_OPEN, ">");
    }
}
