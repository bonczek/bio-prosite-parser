package pl.gda.eti.pg.prosite.rule;

public class AnythingRule extends Rule {

    private final Rule nextRule;

    public AnythingRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

    /**
     * Przepuszcza każdy znak i zwraca kolejną regułę.
     *
     * @param c pojedynczy znak, który zostanie sprawdzony przez regułę.
     * @return kolejna reguła
     */
    @Override
    public Rule next(char c) {
        return nextRule;
    }

    //@todo czy ograniczać podane znaki do jakiegoś konkretnego zbioru?
}
