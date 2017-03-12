package crawler.parser.state;

import crawler.parser.ContentReader;
import crawler.parser.tokens.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class State {
    protected Token token;
    protected boolean tokenBuilt;
    protected boolean finished;
    protected boolean isFinal;
    protected Map<Predicate<ContentReader>, State> nextStates;
    protected ContentReader reader;

    public State(boolean isFinal, ContentReader reader) {
        this.nextStates = new HashMap<>();
        this.isFinal = isFinal;
        this.reader = reader;
    }

    Token getToken() {
        if (!this.tokenBuilt) {
            throw new IllegalStateException("Token has not been built yet.");
        }
        return token;
    }

    final void proceed() {
        if (this.finished) {
            throw new IllegalStateException("Cannot perform next step. State has been already finished.");
        } else {
            nextStep();
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

    State addNextState(Predicate<ContentReader> predicate, State nextState){
        if(isFinal){
           throw new IllegalStateException("State cannot contain any transition to another state because this state final.");
        }
        this.nextStates.put(predicate, nextState);
        return this;
    }

    State traverse() {
        if (isFinal) {
            throw new IllegalStateException("Cannot traverse to another state because this state is final.");
        }
        return this.nextStates.keySet()
                .stream()
                .filter(predicate -> predicate.test(reader))
                .findFirst()
                .map(predicate -> this.nextStates.get(predicate))
                .get();
    }

    public boolean isFinal() {
        return isFinal;
    }

    boolean finished() {
        return this.finished;
    }

    void finish() {
        this.finished = true;
    }

    protected abstract void nextStep();

    protected abstract void makeBuild();
}
