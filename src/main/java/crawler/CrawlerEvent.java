package crawler;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
public class CrawlerEvent {

    private Crawler crawler;

    private CrawlerState oldState;

    private CrawlerState newState;
}
