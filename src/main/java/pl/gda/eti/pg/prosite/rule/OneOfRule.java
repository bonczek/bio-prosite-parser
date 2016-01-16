package pl.gda.eti.pg.prosite.rule;

import java.util.HashSet;
import java.util.Set;

public class OneOfRule extends Rule {

    private final Rule nextRule;
    /**
     * Zbiór znaków dozwolonych w tym miejscu schematu.
     */
    private Set<Character> permittedCharacters;

    public OneOfRule(char[] chars, Rule nextRule, int index) {
        super(index);
        this.nextRule = nextRule;
        this.permittedCharacters = new HashSet<>();
        for (char c : chars) {
            permittedCharacters.add(c);
        }
    }

    /**
     * Konstruktor slużący do kopiowania reguł
     */
    public OneOfRule(OneOfRule rule, Rule nextRule) {
        super(rule.index);
        this.permittedCharacters = new HashSet<>(rule.permittedCharacters);
        this.nextRule = nextRule;
    }

    /**
     * Reguła sprawdza, czy znak zawiera się w dozwolonym zbiorze znaków.
     *
     * @param ch pojedynczy znak, który zostanie sprawdzony przez regułę.
     * @return jeśli znak należy do zbioru to zwraca kolejną regułę, jeśli nie to null
     */
    @Override
    public Rule next(char ch) {
        if (permittedCharacters.contains(ch)) {
            return nextRule;
        } else {
            return null;
        }
    }

}
