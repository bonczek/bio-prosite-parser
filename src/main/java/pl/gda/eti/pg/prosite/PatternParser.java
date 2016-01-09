package pl.gda.eti.pg.prosite;

import pl.gda.eti.pg.prosite.state.AnythingRule;
import pl.gda.eti.pg.prosite.state.ExactlyKTimesRule;
import pl.gda.eti.pg.prosite.state.FinalRule;
import pl.gda.eti.pg.prosite.state.NoneOfRule;
import pl.gda.eti.pg.prosite.state.OneOfRule;
import pl.gda.eti.pg.prosite.state.Rule;
import pl.gda.eti.pg.prosite.state.SingleCharacterRule;

import java.util.Arrays;
import java.util.List;

public class PatternParser {

    public Rule parse(String pattern) {
        List<String> rules = Arrays.asList(pattern.split("-"));

        Rule nextRule = new FinalRule();
        Rule currentRule = null;
        for (int i = rules.size() - 1; i >= 0; i--) {
            String rule = rules.get(i);
            currentRule = decodeRule(rule, nextRule);
            nextRule = currentRule;
        }
        return currentRule;
    }

    private Rule decodeRule(String rule, Rule nextRule) {
        if (rule.charAt(0) == '[' && rule.charAt(rule.length() - 1) == ']') {
            return createOneOfGivenLettersRule(rule, nextRule);
        } else if (rule.charAt(0) == '{' && rule.charAt(rule.length() - 1) == '}') {
            return createNoneFromGivenLettersRule(rule, nextRule);
        } else if (rule.equals("x")) {
            return new AnythingRule(nextRule);
        } else if (rule.length() == 1) {
            return new SingleCharacterRule(rule.charAt(0), nextRule);
        } else if (rule.charAt(1) == '(' && rule.charAt(rule.length() - 1) == ')') {
            if (rule.contains(",")) {

            } else {
                return createExactlyKTimesState(rule, nextRule);
            }
        }
        //@todo reszta regul
        return null;
    }

    private Rule createOneOfGivenLettersRule(String ruleString, Rule nextRule) {
        String letters = ruleString.substring(1, ruleString.length() - 1);
        return new OneOfRule(letters.toCharArray(), nextRule);
    }

    private Rule createNoneFromGivenLettersRule(String ruleString, Rule nextRule) {
        String letters = ruleString.substring(1, ruleString.length() - 1);
        return new NoneOfRule(letters.toCharArray(), nextRule);
    }

    private Rule createExactlyKTimesState(String ruleString, Rule nextRule) {
        char character = ruleString.charAt(0);
        String stringNumber = ruleString.substring(2, ruleString.length() - 1);
        Integer repeatNumber = Integer.parseInt(stringNumber);
        return new ExactlyKTimesRule(character, nextRule, repeatNumber);
    }

}
