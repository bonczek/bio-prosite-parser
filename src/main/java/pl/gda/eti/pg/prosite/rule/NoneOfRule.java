package pl.gda.eti.pg.prosite.rule;

import java.util.HashSet;
import java.util.Set;

public class NoneOfRule extends Rule {

    private final Rule nextRule;

    /**
     * Zbiór znaków zabronionych w tym miejscu schematu.
     */
    private Set<Character> excludedCharactersSet;

    public NoneOfRule(char[] excludedCharacters, Rule nextRule, int index) {
        super(index);
        this.nextRule = nextRule;
        this.excludedCharactersSet = new HashSet<>();
        for (char c : excludedCharacters) {
            excludedCharactersSet.add(c);
        }
    }

    /**
     * Konstruktor slużący do kopiowania reguł
     */
    public NoneOfRule(NoneOfRule rule, Rule nextRule) {
        super(rule.index);
        this.nextRule = nextRule;
        this.excludedCharactersSet = new HashSet<>(rule.excludedCharactersSet);
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
