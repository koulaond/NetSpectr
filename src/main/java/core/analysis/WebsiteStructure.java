package core.analysis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Map;
import java.util.Set;

@Getter
@Setter(AccessLevel.PACKAGE)
public class WebsiteStructure {
    private String domain;
    private WebPageNode initialNode;
    private Map<URL, WebPageNode> webPageNodes;
}
