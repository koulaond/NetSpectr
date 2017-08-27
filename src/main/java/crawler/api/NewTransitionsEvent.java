package crawler.api;

public final class NewTransitionsEvent<NODE, TRANSITION> extends CrawlerEvent<Iterable<TRANSITION>> {

    private NODE source;

    public NewTransitionsEvent(Iterable<TRANSITION> data, NODE source) {
        super(data);
        this.source = source;
    }

    public NODE getSource() {
        return source;
    }

}
