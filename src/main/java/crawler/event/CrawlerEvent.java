package crawler.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
abstract class CrawlerEvent {

    private final UUID crawlerUuid;
}
