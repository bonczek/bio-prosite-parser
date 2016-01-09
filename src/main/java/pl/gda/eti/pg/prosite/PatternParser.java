package pl.gda.eti.pg.prosite;

import pl.gda.eti.pg.prosite.state.AnythingState;
import pl.gda.eti.pg.prosite.state.ExactlyKTimesState;
import pl.gda.eti.pg.prosite.state.FinalState;
import pl.gda.eti.pg.prosite.state.NoneOfState;
import pl.gda.eti.pg.prosite.state.OneOfState;
import pl.gda.eti.pg.prosite.state.SingleCharacterState;
import pl.gda.eti.pg.prosite.state.State;

import java.util.Arrays;
import java.util.List;

public class PatternParser {

    public State parse(String pattern) {
        List<String> rules = Arrays.asList(pattern.split("-"));

        State nextState = new FinalState();
        State currentState = null;
        for (int i = rules.size() - 1; i >= 0; i--) {
            String rule = rules.get(i);
            currentState = decodeRule(rule, nextState);
            nextState = currentState;
        }
        return currentState;
    }

    private State decodeRule(String rule, State nextState) {
        if (rule.charAt(0) == '[' && rule.charAt(rule.length() - 1) == ']') {
            return createOneOfGivenLettersRule(rule, nextState);
        } else if (rule.charAt(0) == '{' && rule.charAt(rule.length() - 1) == '}') {
            return createNoneFromGivenLettersRule(rule, nextState);
        } else if (rule.equals("x")) {
            return new AnythingState(nextState);
        } else if (rule.length() == 1) {
            return new SingleCharacterState(rule.charAt(0), nextState);
        } else if (rule.charAt(1) == '(' && rule.charAt(rule.length() - 1) == ')') {
            if (rule.contains(",")) {

            } else {
                return createExactlyKTimesState(rule, nextState);
            }
        }
        //@todo reszta regul
        return null;
    }

    private State createOneOfGivenLettersRule(String ruleString, State nextState) {
        String letters = ruleString.substring(1, ruleString.length() - 1);
        return new OneOfState(letters.toCharArray(), nextState);
    }

    private State createNoneFromGivenLettersRule(String ruleString, State nextState) {
        String letters = ruleString.substring(1, ruleString.length() - 1);
        return new NoneOfState(letters.toCharArray(), nextState);
    }

    private State createExactlyKTimesState(String ruleString, State nextState) {
        char character = ruleString.charAt(0);
        String stringNumber = ruleString.substring(2, ruleString.length() - 1);
        Integer repeatNumber = Integer.parseInt(stringNumber);
        return new ExactlyKTimesState(character, nextState, repeatNumber);
    }

}
