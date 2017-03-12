package crawler.parser.tokens;

/**
 * Created by Koula on 12.3.2017.
 */
public class Space extends Text {
    protected Space() {
        super(TokenType.SPACE_TEXT, " ");
    }
}
