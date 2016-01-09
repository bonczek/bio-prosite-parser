package pl.gda.eti.pg.prosite.rule;

public class FinalRule extends Rule {

    public FinalRule() {
        super(true);
    }

    @Override
    public Rule next(char c) {
        return null;
    }
}
