package pl.gda.eti.pg.prosite.pattern;

import pl.gda.eti.pg.prosite.rule.BetweenKAndJRule;
import pl.gda.eti.pg.prosite.rule.Rule;

/**
 * Iterator, który zaczynając od określonego miejsca w łańcuchu znaków sprawdza zgodność kolejnych znaków ze schematem.
 */
public class PatternIterator {

    private final int index;
    private Rule rule;
    /**
     * Druga reguła w szczególnym przypadku rozgałęziania reguł.
     */
    private Rule secondRule;
    private StringBuilder matched;

    /**
     * Stworzenie nowego iteratora, ktory od okreslonego miejsca rozpocznie sprawdzanie schematu.
     *
     * @param index indeks znaku w szukanym ciagu od ktorego iterator zacznie sprawdzac reguły
     * @param rule szukane reguły, które sprawdzane są w kolejnych literach
     */
    public PatternIterator(int index, Rule rule) {
        this.index = index;
        this.rule = rule;
        this.matched = new StringBuilder();
    }

    /**
     * Konstruktor do kopiowania iteratora w szczególnym przypadku rozgałęziania reguł.
     */
    public PatternIterator(PatternIterator iterator, Rule nextRule) {
        this.index = iterator.index;
        this.matched = new StringBuilder(iterator.matched);
        this.rule = nextRule;
    }

    public Rule getSecondRule() {
        return secondRule;
    }

    /**
     * Sprawdza czy dla podanego kolejnego znaku określony schemat jest prawdziwy.
     *
     * @param character kolejny znak w przeszukiwanym tekście
     * @return rezulat dopasowania danego znaku
     */
    public boolean patternMatched(Character character) {
        secondRule = null;
        Rule nextRule = rule.next(character);
        if (nextRule != null) {
            this.rule = nextRule;
            matched.append(character);
            if (nextRule instanceof BetweenKAndJRule) {
                BetweenKAndJRule optionalRule = (BetweenKAndJRule) nextRule;
                //szczególny przypadek rozgałęzienia reguł
                if (optionalRule.getRuleAfter() != null) {
                    secondRule = optionalRule.getRuleAfter();
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean finished() {
        return rule.isFinal();
    }

    public PatternFound getSequenceFound() {
        return new PatternFound(index, index + matched.length(), matched.toString());
    }
}
