package pl.gda.eti.pg.prosite.state;

import java.util.HashMap;
import java.util.Map;

public class OneOfState extends State {

    private Map<Character, State> transitions = new HashMap<>();

    public OneOfState(char[] chars, State st) {
        for (char c : chars) {
            transitions.put(c, st);
        }
    }

    @Override
    public State next(char ch) {
        return transitions.get(ch);
    }

}
