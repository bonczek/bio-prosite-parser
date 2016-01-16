package pl.gda.eti.pg.prosite.rule;

import java.util.List;

public class RuleBuilder {

    private final List<Rule> rulesChain;

    public RuleBuilder(List<Rule> rulesChain) {
        this.rulesChain = rulesChain;
    }

    public Rule getCopiedRulesChain(int index) {
        Rule nextRule = null;
        Rule currentRule = null;
        for (int i = rulesChain.size() - 1; i >= index; i--) {
            currentRule = copy(rulesChain.get(i), nextRule);
            nextRule = currentRule;
        }
        return currentRule;
    }

    private Rule copy(Rule rule, Rule nextRule) {
        if (rule instanceof FinalRule) {
            return new FinalRule((FinalRule) rule);
        } else if (rule instanceof AnythingRule) {
            return new AnythingRule((AnythingRule) rule, nextRule);
        } else if (rule instanceof ExactlyKTimesRule) {
            return new ExactlyKTimesRule((ExactlyKTimesRule) rule, nextRule);
        } else if (rule instanceof BetweenKAndJRule) {
            return new BetweenKAndJRule((BetweenKAndJRule) rule, nextRule);
        } else if (rule instanceof SingleCharacterRule) {
            return new SingleCharacterRule((SingleCharacterRule) rule, nextRule);
        } else if (rule instanceof OneOfRule) {
            return new OneOfRule((OneOfRule) rule, nextRule);
        } else if (rule instanceof NoneOfRule) {
            return new NoneOfRule((NoneOfRule) rule, nextRule);
        } else {
            throw new UnsupportedOperationException("Cannot instatiate rule, because it is unknown.");
        }
    }
}
