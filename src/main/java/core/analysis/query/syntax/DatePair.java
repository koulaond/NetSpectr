package core.analysis.query.syntax;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class DatePair {
    private Date before, after;
}
