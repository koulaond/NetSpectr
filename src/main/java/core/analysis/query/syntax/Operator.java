package core.analysis.query.syntax;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

import static java.lang.String.format;

@EqualsAndHashCode
@Getter
public class Operator<T> {

    private OperatorType operatorType;
    private T value;

    private Operator(OperatorType operatorType, T value) {
        if (!operatorType.supportsType(value.getClass())) {
            throw new IllegalStateException(format("Operator type %s does not support class type %s", operatorType.name(), value.getClass().getName()));
        }
        this.operatorType = operatorType;
        this.value = value;
    }

    public static Operator<String> exact(String value) {
        return new Operator<>(OperatorType.EXACT, value);
    }

    public static Operator<String> any() {
        return new Operator<>(OperatorType.MATCHES_REGEX, ".");
    }

    public static Operator<String> startsWith(String value) {
        return new Operator<>(OperatorType.STARTS_WITH, value);
    }

    public static Operator<String> endsWith(String value) {
        return new Operator<>(OperatorType.ENDS_WITH,value);
    }

    public static Operator<String> contains(String value) {
        return new Operator<>(OperatorType.CONTAINS, value);
    }

    public static Operator<Integer> isEqualTo(int value) {
        return new Operator<>(OperatorType.EQUALS, value);
    }

    public static Operator<Integer> lessThan(int value) {
        return new Operator<>(OperatorType.LESS_THAN, value);
    }

    public static Operator<Date> before(Date value) {
        return new Operator<>(OperatorType.GREATER_THAN, value);
    }

    public static Operator<Date> after(Date value) {
        return new Operator<>(OperatorType.GREATER_THAN, value);
    }

    public static Operator<DatePair> between(Date from, Date to) {
        return new Operator<>(OperatorType.GREATER_THAN, new DatePair(from, to));
    }
}
