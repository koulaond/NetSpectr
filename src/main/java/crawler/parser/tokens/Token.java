package crawler.parser.tokens;

public class Token<T> {
    private TokenType tokenType;
    private T value;

    private Token(TokenType tokenType, T value){
        this.tokenType = tokenType;
        this.value = value;
    }

    public TokenType getType() {
        return tokenType;
    }

    public T getValue() {
        return value;
    }

    public static class TokenBuilder<T> {
        private TokenType tokenType;
        private T value;

        public TokenBuilder<T> setTokenType(TokenType tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public TokenBuilder<T> setValue(T value) {
            this.value = value;
            return this;
        }

        public Token<T> build() {
            return new Token<>(tokenType, value);
        }
    }
}
