package core.analysis.query.syntax;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode
@AllArgsConstructor(access = PRIVATE)
public class Query {
    private ElementQueryTemplate template;

    public Set<Statement> getStatements() {
        return template.getSubordinates();
    }

    public static Query query(ElementQueryTemplate template) {
        return new Query(template);
    }
}
