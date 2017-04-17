package repository;

import java.net.URL;
import java.util.Set;
import java.util.UUID;

public interface Node {
    Graph getGraph();

    UUID getId();

    URL getUrl();

    Set<Node> getIncomes();

    Set<Node> getOutcomes();
}
