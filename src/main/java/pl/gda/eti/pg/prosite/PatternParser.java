package pl.gda.eti.pg.prosite;

import java.util.Arrays;
import java.util.List;

public class PatternParser {

    public State parse(String pattern) {
        List<String> rules = Arrays.asList(pattern.split("-"));

        State nextState = new State(true);
        State currentState = null;
        for (int i = rules.size() - 1; i >= 0; i--) {
            String rule = rules.get(i);
            currentState = decodeRule(rule, nextState);
            if (i > 0) {
                nextState = currentState;
            }
        }
        return currentState;
    }

    private State decodeRule(String rule, State nextState) {
        if (rule.charAt(0) == '[' && rule.charAt(rule.length() - 1) == ']') {
            String letters = rule.substring(1, rule.length() - 1);
            State oneOf = new State();
            for (char c : letters.toCharArray()) {
                oneOf.addTransition(c, nextState);
            }
            return oneOf;
        }
        //@todo reszta regul
        return null;
    }
}
