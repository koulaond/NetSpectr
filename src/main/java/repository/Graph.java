package repository;

import java.net.URL;
import java.util.*;

public class Graph {
    private URL host;
    private Node rootNode;
    private HashSet<Node> nodeIndex;

    // TODO

    private class BaseNode implements Node {
        private Graph graph;
        private UUID id;
        private URL url;
        private Set<Node> incomes, outcomes;

        public BaseNode() {
            this.id = UUID.randomUUID();
            this.incomes = new HashSet<>();
            this.outcomes = new HashSet<>();
        }

        @Override
        public Graph getGraph() {
            return graph;
        }

        public void setGraph(Graph graph) {
            this.graph = graph;
        }

        @Override
        public UUID getId() {
            return id;
        }

        public BaseNode setId(UUID id) {
            this.id = id;
            return this;
        }

        @Override
        public URL getUrl() {
            return url;
        }

        public BaseNode setUrl(URL url) {
            this.url = url;
            return this;
        }

        @Override
        public Set<Node> getIncomes() {
            return incomes;
        }

        public BaseNode setIncomes(Set<Node> incomes) {
            this.incomes = incomes;
            return this;
        }

        @Override
        public Set<Node> getOutcomes() {
            return outcomes;
        }

        public BaseNode setOutcomes(Set<Node> outcomes) {
            this.outcomes = outcomes;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BaseNode other = (BaseNode) o;
            return this.getId().equals(other.getId());
        }

        @Override
        public int hashCode() {
            return 31 * id.hashCode() + url.hashCode();
        }
    }
}
