package crawler.parser.state;

import crawler.parser.ContentReader;


public class CloseBracketState extends State{
    public CloseBracketState(ContentReader reader) {
        super(false, reader);
    }

    @Override
    protected void nextStep() {

    }

    @Override
    protected void makeBuild() {

    }
}
