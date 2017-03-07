package crawler.parser.tokens;

import crawler.parser.HtmlCharacters;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class Attributes extends Token {
    private Set<Attribute> attributes;

    private Attributes( Set<Attribute> attributes) {
        super(TokenType.ATTRIBUTES);
        this.attributes = Collections.unmodifiableSet(attributes);
    }

    public static AttributesBuilder builder() {
        return new AttributesBuilder();
    }

    @Override
    public String html() {
        return attributes.stream()
                .map(attribute -> attribute.html())
                .collect(Collectors.joining(String.valueOf(HtmlCharacters.SPACE)));
    }

    public static class AttributesBuilder {
        private Set<Attribute> attributes;

        public AttributesBuilder() {
            this.attributes = new HashSet<>();
        }

        public AttributesBuilder add(Attribute attribute) {
            this.attributes.add(attribute);
            return this;
        }

        public Attributes build() {
            return new Attributes(attributes);
        }
    }
}
