package crawler.parser.state;

import crawler.parser.ContentReader;
import crawler.parser.TextValueAdjuster;
import crawler.parser.tokens.Attribute;
import crawler.parser.tokens.StartTag;

import static java.lang.String.valueOf;

public class TagHeaderState extends State<StartTag> {

    public TagHeaderState(ContentReader reader) {
        super(false, reader);
    }

    @Override
    protected void nextStep(ContentReader reader) {

    }

    @Override
    protected void makeBuild() {

    }
}
