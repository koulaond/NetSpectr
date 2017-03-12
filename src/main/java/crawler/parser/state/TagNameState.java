package crawler.parser.state;

import crawler.parser.ContentReader;
import crawler.parser.tokens.TagName;

public class TagNameState extends State<TagName> {
    private String name;

    public TagNameState() {
        super(false);

    }

    @Override
    protected void nextStep(ContentReader reader) {
        this.name = String.valueOf(reader.readTagName());
        finish();
    }

    @Override
    protected void makeBuild() {

    }
}
