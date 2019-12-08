package core.analysis.query.execute;

import core.WebPage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class BatchQueryResult {
    private int numberOfInputPages;
    private int numberOfFoundPages;
    private Map<WebPage, List<ElementQueryResult>> results;
}
