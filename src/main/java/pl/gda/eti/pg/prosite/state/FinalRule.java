package pl.gda.eti.pg.prosite.state;

public class FinalRule extends Rule {

    public FinalRule() {
        super(true);
    }

    @Override
    public Rule next(char c) {
        return null;
    }
}
