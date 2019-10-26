package core.event;

import core.WebPage;
import core.WebPageNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URL;
import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class StructureUpdatedEvent {
    UUID  jobUuid;
    private WebPage webPageAdded;
    private Map<URL, WebPageNode> webPageNodes;
    private String domain;
}
