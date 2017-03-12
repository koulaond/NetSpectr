package crawler.parser.state;

import crawler.parser.ContentReader;
import crawler.parser.TextValueAdjuster;
import crawler.parser.tokens.Attribute;

import static java.lang.String.valueOf;

public class AttributeState extends State<Attribute> {
    private String name, value;

    public AttributeState(ContentReader contentReader) {
        super(false, contentReader);
        this.addNextState(reader -> reader.actual() == ' ', new SpaceState(reader))
                .addNextState(reader -> reader.actual() == '>', new SpaceState(reader));
    }

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