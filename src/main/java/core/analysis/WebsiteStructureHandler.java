package core.analysis;

import java.net.URL;
import java.util.Map;

public class WebsiteStructureHandler {
    private WebsiteStructure structure;

    WebsiteStructureHandler() {
        this.structure = new WebsiteStructure();
    }

    public void updateStructure(WebPage webPage) {
        Map<URL, WebPageNode> webPageNodes = structure.getWebPageNodes();
        if (structure.getInitialNode() == null) {
            // First node
            WebPageNode node = new WebPageNode();
            node.setWebPage(webPage);
            structure.setInitialNode(node);
            webPageNodes.put(webPage.getSourceUrl(), node);
            adjustOutcomeNodes(webPage, webPageNodes, node);
        } else {
            WebPageNode node = webPageNodes.get(webPage.getSourceUrl());
            if (node == null) {
                node = new WebPageNode();
                node.setWebPage(webPage);
                webPageNodes.put(webPage.getSourceUrl(), node);
            }
            adjustOutcomeNodes(webPage, webPageNodes, node);
        }
    }

    public boolean isInStructure(URL location) {
        return structure.getWebPageNodes().get(location) != null;
    }

    private void adjustOutcomeNodes(WebPage webPage, Map<URL, WebPageNode> webPageNodes, WebPageNode sourceNode) {
        for (URL outcomeUrl : webPage.getOutcomeUrlsOnDomain()) {
            WebPageNode outcomeNode = webPageNodes.get(outcomeUrl);
            if (outcomeNode == null) {
                outcomeNode = new WebPageNode();
                outcomeNode.setSourceUrl(outcomeUrl);
                webPageNodes.put(outcomeUrl, outcomeNode);
            }
            outcomeNode.getIncomeWebPageNodes().add(sourceNode);
            sourceNode.getOutcomeWebPageNodes().add(outcomeNode);
        }
    }
}
