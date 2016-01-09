package pl.gda.eti.pg.prosite;

import pl.gda.eti.pg.prosite.rule.Rule;

/**
 * Iterator, który zaczynając od określonego miejsca w łańcuchu znaków sprawdza zgodność kolejnych znaków ze schematem.
 */
public class PrositeIterator {

    private final int index;
    private Rule rule;
    private StringBuilder matched;

    /**
     * Stworzenie nowego iteratora, ktory od okreslonego miejsca rozpocznie sprawdzanie schematu.
     *
     * @param index indeks znaku w szukanym ciagu od ktorego iterator zacznie sprawdzac reguły
     * @param rule szukane reguły, które sprawdzane są w kolejnych literach
     */
    public PrositeIterator(int index, Rule rule) {
        this.index = index;
        this.rule = rule;
        this.matched = new StringBuilder();
    }

    public int getIndex() {
        return index;
    }

    public Rule getRule() {
        return rule;
    }

    /**
     * Sprawdza czy dla podanego kolejnego znaku określony schemat jest prawdziwy.
     *
     * @param character kolejny znak w przeszukiwanym tekście
     * @return rezulat dopasowania danego znaku
     */
    public boolean patternMatched(Character character) {
        Rule tmp = rule.next(character);
        if (tmp != null) {
            this.rule = tmp;
            matched.append(character);
            return true;
        } else {
            return false;
        }
    }

    public String getMatched() {
        return matched.toString();
    }
}
