package crawler.parser.tokens;

import java.util.Objects;

public class Text extends Token {
    protected String data;

    protected Text(TokenType tokenType, String data) {
        super(tokenType);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String html() {
        return Objects.requireNonNull(data);
    }

    public static TextBuilder builder(){
        return new TextBuilder();
    }

    protected static class TextBuilder {
        protected String data;

        public TextBuilder setData(String data) {
            this.data = data;
            return this;
        }

        public Text build() {
            return new Text(TokenType.TEXT, data);
        }
    }
}
