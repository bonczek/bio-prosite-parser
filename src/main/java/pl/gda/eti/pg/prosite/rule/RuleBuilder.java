package pl.gda.eti.pg.prosite.rule;

import java.util.List;

/**
 * Klasa do budowania nowych łańcuchów reguł według zapamiętanego schematu.
 * Potrzeba taka wynika z tego, że stan reguł może się zmieniać, przez co każdy PatternIterator potrzebuje nowe obiekty reguł.
 */
public class RuleBuilder {

    private final List<Rule> rulesChain;

    public RuleBuilder(List<Rule> rulesChain) {
        this.rulesChain = rulesChain;
    }

    /**
     * Tworzy kopię reguł zapamiętanych po sparsowaniu od zadanego indeksu reguły.
     *
     * @param ruleIndex indeks reguły od której należy odtworzyć dalszy łańcuch reguł
     * @return nowy łańcuch reguł
     */
    public Rule getCopiedRulesChain(int ruleIndex) {
        Rule nextRule = null;
        Rule currentRule = null;
        for (int i = rulesChain.size() - 1; i >= ruleIndex; i--) {
            currentRule = copy(rulesChain.get(i), nextRule);
            nextRule = currentRule;
        }
        return currentRule;
    }

    /**
     * Tworzy nowe instancje obiektów opierając się na już stworzonych.
     *
     * @param rule     reguła do skopiowania
     * @param nextRule nowa reguła następna w kolejności
     * @return nowy obiekt skopiowanej reguły.
     */
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
