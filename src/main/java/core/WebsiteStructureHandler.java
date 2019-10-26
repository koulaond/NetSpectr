package core;

import lombok.AccessLevel;
import lombok.Getter;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Getter(AccessLevel.PACKAGE)
public class WebsiteStructureHandler {
    private WebsiteStructure structure;
    private ReentrantLock reentrantLock = new ReentrantLock();


    public WebsiteStructureHandler() {
        this.structure = new WebsiteStructure();
    }

    void updateStructure(WebPage webPage) {
        reentrantLock.lock();
        try {
            Map<URL, WebPageNode> webPageNodes = structure.getWebPageNodes();
            if (structure.getInitialNode() == null) {
                // First node
                WebPageNode node = new WebPageNode();
                node.setWebPage(webPage);
                node.setSourceUrl(webPage.getSourceUrl());
                structure.setInitialNode(node);
                structure.setDomain(webPage.getSourceUrl().getHost());
                webPageNodes.put(webPage.getSourceUrl(), node);
                adjustOutcomeNodes(webPage, webPageNodes, node);
            } else {
                WebPageNode node = webPageNodes.get(webPage.getSourceUrl());
                if (node == null) {
                    node = new WebPageNode();
                    webPageNodes.put(webPage.getSourceUrl(), node);
                }
                node.setWebPage(webPage);
                node.setSourceUrl(webPage.getSourceUrl());
                adjustOutcomeNodes(webPage, webPageNodes, node);
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    private void adjustOutcomeNodes(WebPage webPage, Map<URL, WebPageNode> webPageNodes, WebPageNode sourceNode) {
        for (URL outcomeUrl : webPage.getOutcomeUrlsOnDomain()) {
            WebPageNode outcomeNode = webPageNodes.get(outcomeUrl);
            if (outcomeNode == null) {
                outcomeNode = new WebPageNode();
                outcomeNode.setSourceUrl(outcomeUrl);
                webPageNodes.put(outcomeUrl, outcomeNode);
            }
            if (!outcomeNode.getSourceUrl().equals(sourceNode.getSourceUrl())) {
                outcomeNode.getIncomeWebPageNodes().add(sourceNode);
                sourceNode.getOutcomeWebPageNodes().add(outcomeNode);
            }
        }
    }
}
