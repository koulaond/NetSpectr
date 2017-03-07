package crawler.parser.state;

import crawler.parser.ContentReader;
import crawler.parser.TextValueAdjuster;
import crawler.parser.tokens.Attribute;
import crawler.parser.tokens.Attributes;
import crawler.parser.tokens.StartTag;

import static java.lang.String.valueOf;

public class TagHeaderState extends State<StartTag> {

    public TagHeaderState() {

    }

    @Override
    protected void nextStep(ContentReader reader) {

    }

    @Override
    protected void makeBuild() {

    }

    private class AttributeState extends State<Attribute> {
        private String name, value;

        @Override
        protected void nextStep(ContentReader reader) {
            TextValueAdjuster adjuster = new TextValueAdjuster();
            name = adjuster.adjust(valueOf(reader.readAttributeName()), " ");
            value = adjuster.adjust(valueOf(reader.readAttributeValue()), "\"", "=");
        }

        @Override
        protected void makeBuild() {
            if (this.name != null && this.value != null) {
                this.token = Attribute.builder().setName(name).setValue(value).build();
                this.tokenBuilt = true;
            }
        }
    }
}
