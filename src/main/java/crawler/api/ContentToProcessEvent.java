package crawler.api;

/**
 * Event that carries new data node downloaded by a crawler.
 * @param <NODE> new downloaded node
 * @param <TRANSITION> transition that directs to the new available node
 */
public final class ContentToProcessEvent<NODE, TRANSITION> extends CrawlerEvent<NODE> {

    private TRANSITION incomeTransition;

    public ContentToProcessEvent(NODE NODE, TRANSITION source) {
        super(NODE);
        this.incomeTransition = source;
    }

    public TRANSITION getIncomeTransition() {
        return incomeTransition;
    }
}
