package crawler.parser.state;

import crawler.parser.ContentReader;
import crawler.parser.TextValueAdjuster;
import crawler.parser.tokens.Token;
import crawler.parser.tokens.TokenType;

import static java.lang.String.valueOf;

public class AttributeState extends State {
    private String name, value;

    public AttributeState(ContentReader contentReader) {
        super(false, contentReader);

    }

    @Override
    protected void nextStep() {
        TextValueAdjuster adjuster = new TextValueAdjuster();
        value = adjuster.adjust(valueOf(reader.readAttributeValue()), "\"", "=");
    }

    @Override
    protected void makeBuild() {
        if (this.value != null) {
            this.token = new Token.TokenBuilder<>().setTokenType(TokenType.ATTIBUTE).setValue(value).build();
            this.tokenBuilt = true;
        }
    }
}