package crawler.parser.state;

import crawler.parser.ContentReader;
import crawler.parser.tokens.TagName;

public class TagNameState extends State<TagName> {

    private String name;

    @Override
    void proceed(ContentReader reader) {
        this.name = String.valueOf(reader.readTagName());
    }

    @Override
    protected void nextStep(ContentReader reader) {

    }

    @Override
    protected void makeBuild() {

    }
}
