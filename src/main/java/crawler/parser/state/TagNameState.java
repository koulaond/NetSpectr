package crawler.parser.state;

import crawler.parser.ContentReader;

public class TagNameState extends State<TagName> {
    private String name;

    public TagNameState(ContentReader reader) {
        super(false, reader);

    }

    @Override
    protected void nextStep() {
        this.name = String.valueOf(reader.readTagName());
        finish();
    }

    @Override
    protected void makeBuild() {

    }
}
