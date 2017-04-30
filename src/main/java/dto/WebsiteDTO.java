package dto;

import java.util.Set;

public class WebsiteDTO {

    private Long id;
    private NetworkDTO network;
    private String url;
    private String htmlContent;
    private Set<WebsiteDTO> outcomes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NetworkDTO getNetwork() {
        return network;
    }

    public void setNetwork(NetworkDTO network) {
        this.network = network;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Set<WebsiteDTO> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(Set<WebsiteDTO> outcomes) {
        this.outcomes = outcomes;
    }
}
