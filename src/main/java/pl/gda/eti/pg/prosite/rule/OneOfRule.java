package pl.gda.eti.pg.prosite.rule;

import java.util.HashSet;
import java.util.Set;

public class OneOfRule extends Rule {

    private final Rule nextRule;
    /**
     * Zbiór znaków dozwolonych w tym miejscu schematu.
     */
    private Set<Character> permittedCharacters = new HashSet<>();

    public OneOfRule(char[] chars, Rule nextRule) {
        this.nextRule = nextRule;
        for (char c : chars) {
            permittedCharacters.add(c);
        }
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
