package pl.gda.eti.pg.prosite;

import java.util.HashMap;
import java.util.Map;

public class State {

    private boolean isFinal = false;

    private Map<Character, State> transitions = new HashMap<>();

    public State() {
    }

    public State(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public void addTransition(char ch, State st) {
        transitions.put(ch, st);
    }

    public State next(char ch) {
        return transitions.get(ch);
    }

    public boolean isFinal() {
        return isFinal;
    }

}
