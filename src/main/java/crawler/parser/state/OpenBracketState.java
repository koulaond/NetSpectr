package crawler.parser.state;

import crawler.parser.ContentReader;

public class OpenBracketState extends State{
    public OpenBracketState(ContentReader reader) {
        super(false, reader);
    }

    @Override
    protected void nextStep() {
        finish();
    }

    @Override
    protected void makeBuild() {

    }
}
