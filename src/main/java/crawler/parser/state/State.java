package crawler.parser.state;

import crawler.parser.ContentReader;
import crawler.parser.tokens.Token;

public abstract class State<T extends Token> {
    protected T token;
    protected boolean tokenBuilt;
    protected boolean finished;

    T getToken() {
        if (!this.tokenBuilt) {
            throw new IllegalStateException("Token has not been built yet.");
        }
        return token;
    }

    void proceed(ContentReader reader) {
        if (this.finished) {
            throw new IllegalStateException("Cannot perform next step. State has been already finished.");
        } else {
            nextStep(reader);
        }
    }

    void buildToken() {
        if (!finished) {
            throw new IllegalStateException("Cannot build token. State process is not finished yet.");
        } else if (!tokenBuilt) {
            makeBuild();
            tokenBuilt = true;
        }
    }

    boolean finished() {
        return this.finished;
    }

    void finish() {
        this.finished = true;
    }

    protected abstract void nextStep(ContentReader reader);

    protected abstract void makeBuild();
}
