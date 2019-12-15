package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ondrejkoula.crawler.CrawlerInfo;
import lombok.Builder;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
public class CrawlerJobDTO {
    private String url;
    private String jobId;
    private CrawlerInfo crawlerInfo;
    private String message;
}
