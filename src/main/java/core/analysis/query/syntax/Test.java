package core.analysis.query.syntax;

public class Test {
    public static void main(String[] args) {
        ElementQueryTemplate fistTemplate = Elements.hasAttribute(
                Attributes.or(
                        Attributes.hasName("class"),
                        Attributes.hasName("p:class"),
                        Attributes.or(
                                Attributes.hasValue(Operator.exact("koula")),
                                Attributes.hasValue(Operator.exact("koala"))
                        )
                )
                        .hasValue(
                                Operator.endsWith(".ondrejkoula")
                        )
        );
        ElementQueryTemplate secondTemplate = Elements.hasAttribute(
                Attributes
                        .hasValue(
                                Operator.endsWith(".ondrejkoula")
                        )
                        .or(
                                Attributes.or(
                                        Attributes.hasValue("koala"),
                                        Attributes.hasValue("koula")
                                ),
                                Attributes.hasName(Operator.exact("p:class")),
                                Attributes.hasName(Operator.exact("class"))
                        )
        );

        System.out.println(fistTemplate.equals(secondTemplate));
        System.out.println(fistTemplate.hashCode() == secondTemplate.hashCode());
    }
}
