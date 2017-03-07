package crawler.parser.tokens;

public class Comment extends Text {

    protected Comment(TokenType tokenType, String data) {
        super(tokenType, data);
    }

    public static CommentBuilder builder(){
        return new CommentBuilder();
    }

    private static class CommentBuilder extends TextBuilder{

        public Comment build() {
            return new Comment(TokenType.COMMENT, data);
        }
    }
}
