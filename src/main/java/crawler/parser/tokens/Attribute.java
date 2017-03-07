package crawler.parser.tokens;

import static crawler.parser.HtmlCharacters.ASSIGN;
import static crawler.parser.HtmlCharacters.QUOTE;
import static java.util.Objects.requireNonNull;

public class Attribute extends Token {
    private String name;
    private String value;

    private Attribute(String name, String value) {
        super(TokenType.ATTIBUTE);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String html() {
        return requireNonNull(name) + ASSIGN + QUOTE + requireNonNull(value) + QUOTE;
    }

    public static AttributeBuilder builder(){
        return new AttributeBuilder();
    }

    public static class AttributeBuilder {
        private String attributeName;
        private String attributeValue;

        public AttributeBuilder setName(String attributeName) {
            this.attributeName = attributeName;
            return this;
        }

        public AttributeBuilder setValue(String attributeValue) {
            this.attributeValue = attributeValue;
            return this;
        }

        public Attribute build() {
            return new Attribute(attributeName, attributeValue);
        }
    }
}
