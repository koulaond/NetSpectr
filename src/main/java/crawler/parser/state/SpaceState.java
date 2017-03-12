package crawler.parser.state;

import crawler.parser.ContentReader;


public class SpaceState extends State {

    protected SpaceState(ContentReader reader) {
        super(false, reader);
    }

    @Override
    protected void nextStep() {

    }

    @Override
    protected void makeBuild() {

    }
}
