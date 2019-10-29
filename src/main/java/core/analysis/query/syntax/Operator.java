package core.analysis.query.syntax;

import lombok.EqualsAndHashCode;

import static java.lang.String.format;

@EqualsAndHashCode
public class Operator<T> {

    private OperatorType operatorType;
    private T value;

    private Operator(T value,  OperatorType operatorType) {
        if (!operatorType.supportsType(value.getClass())) {
            throw new IllegalStateException(format("Operator type %s does not support class type %s", operatorType.name(), value.getClass().getName()));
        }
        this.operatorType = operatorType;
        this.value = value;
    }

    public static Operator<String> isEqualTo(String value) {
        return new Operator<>(value, OperatorType.EQUALS);
    }
}
