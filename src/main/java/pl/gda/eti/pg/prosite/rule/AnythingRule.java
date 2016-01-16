package pl.gda.eti.pg.prosite.rule;

public class AnythingRule extends Rule {

    private final Rule nextRule;

    public AnythingRule(Rule nextRule, int index) {
        super(index);
        this.nextRule = nextRule;
    }

    /**
     * Konstruktor slużący do kopiowania reguł
     */
    public AnythingRule(AnythingRule rule, Rule nextRule) {
        super(rule.index);
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

}
