package pl.gda.eti.pg.prosite.rule;

import java.util.HashMap;
import java.util.Map;

public class OneOfRule extends Rule {

    private Map<Character, Rule> transitions = new HashMap<>();

    public OneOfRule(char[] chars, Rule st) {
        for (char c : chars) {
            transitions.put(c, st);
        }
    }

    @Override
    public Rule next(char ch) {
        return transitions.get(ch);
    }

}
