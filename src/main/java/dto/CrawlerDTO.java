package dto;

public class CrawlerDTO {
    private Long id;
    private String baseUrl;
    private String status;

    public Long getId() {
        return id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
