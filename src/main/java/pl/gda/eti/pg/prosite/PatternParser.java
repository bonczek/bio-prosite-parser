package pl.gda.eti.pg.prosite;

import pl.gda.eti.pg.prosite.rule.AnythingRule;
import pl.gda.eti.pg.prosite.rule.ExactlyKTimesRule;
import pl.gda.eti.pg.prosite.rule.FinalRule;
import pl.gda.eti.pg.prosite.rule.NoneOfRule;
import pl.gda.eti.pg.prosite.rule.OneOfRule;
import pl.gda.eti.pg.prosite.rule.Rule;
import pl.gda.eti.pg.prosite.rule.SingleCharacterRule;

import java.util.Arrays;
import java.util.List;

public class PatternParser {

    /**
     * Wyszukuje reguł wzorca Prosite w podanym ciągu znaków.
     * Kolejne reguły powinny być rozdzielone znakiem '-'
     *
     * @param pattern wzorzec z zapisanymi regułami
     * @return zwraca łańcuch reguł, który jest wynikiem przetworzenia podanego wzorca.
     */
    public Rule parse(String pattern) throws IllegalArgumentException {
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

    private Rule decodeRule(String rule, Rule nextRule) throws IllegalArgumentException {
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
                //@todo wystąpenia litery między i<A<j
            } else {
                try {
                    return createExactlyKTimesState(rule, nextRule);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Failed to create rule, because cannot parse number of repeat time.", e);
                }

            }
        }
        throw new IllegalArgumentException(String.format(
                "Error during parsing rule. Cannot apply none of implemented rules to: %s", rule));
    }

    private Rule createOneOfGivenLettersRule(String ruleString, Rule nextRule) {
        String letters = ruleString.substring(1, ruleString.length() - 1);
        return new OneOfRule(letters.toCharArray(), nextRule);
    }

    private Rule createNoneFromGivenLettersRule(String ruleString, Rule nextRule) {
        String letters = ruleString.substring(1, ruleString.length() - 1);
        return new NoneOfRule(letters.toCharArray(), nextRule);
    }

    private Rule createExactlyKTimesState(String ruleString, Rule nextRule) throws NumberFormatException {
        char character = ruleString.charAt(0);
        String stringNumber = ruleString.substring(2, ruleString.length() - 1);
        Integer repeatNumber = Integer.parseInt(stringNumber);
        return new ExactlyKTimesRule(character, nextRule, repeatNumber);
    }

}
