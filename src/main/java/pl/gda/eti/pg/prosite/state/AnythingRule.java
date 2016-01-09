package pl.gda.eti.pg.prosite.state;

public class AnythingRule extends Rule {

    private final Rule nextRule;

    public AnythingRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

    @Override
    public Rule next(char c) {
        return nextRule;
    }
}
