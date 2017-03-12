package crawler.parser.state;

import crawler.parser.ContentReader;
import crawler.parser.tokens.CloseBracket;

/**
 * Created by Koula on 12.3.2017.
 */
public class CloseBracketState extends State<CloseBracket>{
    public CloseBracketState(ContentReader reader) {
        super(false, reader);
    }

    @Override
    protected void nextStep(ContentReader reader) {

    }

    @Override
    protected void makeBuild() {

    }
}
