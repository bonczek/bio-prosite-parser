package pl.gda.eti.pg.prosite.state;

import java.util.HashSet;
import java.util.Set;

public class NoneOfState extends State {

    private final State nextState;
    private Set<Character> excludedCharactersSet = new HashSet<>();

    public NoneOfState(char[] excludedCharacters, State nextState) {
        this.nextState = nextState;
        for (char c : excludedCharacters) {
            excludedCharactersSet.add(c);
        }
    }

    @Override
    public State next(char c) {
        if (!excludedCharactersSet.contains(c)) {
            return nextState;
        } else {
            return null;
        }
    }
}
