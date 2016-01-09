package pl.gda.eti.pg.prosite.state;

import java.util.HashSet;
import java.util.Set;

public class NoneOfRule extends Rule {

    private final Rule nextRule;
    private Set<Character> excludedCharactersSet = new HashSet<>();

    public NoneOfRule(char[] excludedCharacters, Rule nextRule) {
        this.nextRule = nextRule;
        for (char c : excludedCharacters) {
            excludedCharactersSet.add(c);
        }
    }

    @Override
    public Rule next(char c) {
        if (!excludedCharactersSet.contains(c)) {
            return nextRule;
        } else {
            return null;
        }
    }
}
