package crawler.parser.state;


import crawler.parser.ContentReader;
import crawler.parser.tokens.OpenBracket;

public class OpenBracketState extends State<OpenBracket>{
    public OpenBracketState(ContentReader reader) {
        super(false, reader);
    }

    @Override
    protected void nextStep(ContentReader reader) {

    }

    @Override
    protected void makeBuild() {

    }
}
