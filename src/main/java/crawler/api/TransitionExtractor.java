package crawler.api;

/**
 * Definition for extracting classes used in Crawler mechanism.
 * @param <NODE> source node type
 * @param <TRANSITION> target outcome transitions type
 */
public interface TransitionExtractor<NODE, TRANSITION> {

    /**
     * Extracts all outcomes of type {@code TRANSITION} from a source instance with type {@code NODE}.
     * @param source
     * @return Iterable instance with extracted outcomes
     */
    Iterable<TRANSITION> extractLinks(NODE source);
}
