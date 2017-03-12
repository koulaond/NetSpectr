package crawler.parser.state;

import crawler.parser.ContentReader;
import crawler.parser.tokens.Space;


public class SpaceState extends State<Space> {

    protected SpaceState(ContentReader reader) {
        super(false, reader);
    }

    @Override
    protected void nextStep(ContentReader reader) {
    }

    @Override
    protected void makeBuild() {

    }
}
