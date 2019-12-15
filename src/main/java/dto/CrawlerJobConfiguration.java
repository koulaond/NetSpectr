package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
public class CrawlerJobConfiguration {
    private String name;
    private String description;
    private String initialUrl;
    private int crawlDelay;
}
