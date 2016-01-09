package pl.gda.eti.pg.prosite.rule;

import java.util.HashSet;
import java.util.Set;

public class NoneOfRule extends Rule {

    private final Rule nextRule;

    /**
     * Zbiór znaków zabronionych w tym miejscu schematu.
     */
    private Set<Character> excludedCharactersSet = new HashSet<>();

    public NoneOfRule(char[] excludedCharacters, Rule nextRule) {
        this.nextRule = nextRule;
        for (char c : excludedCharacters) {
            excludedCharactersSet.add(c);
        }
    }

    /**
     * Reguła sprawdza, czy znak należy do zbioru wykluczonych wartości.
     *
     * @param c pojedynczy znak, który zostanie sprawdzony przez regułę.
     * @return jeśli znak należy do zbioru wykluczonych wartości zwraca null, jeśli nie należy to zwraca kolejną regułę
     */
    @Override
    public Rule next(char c) {
        if (!excludedCharactersSet.contains(c)) {
            return nextRule;
        } else {
            return null;
        }
    }
}
