package core.analysis.query.syntax;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class DatePair {
    private Date before, after;
}
