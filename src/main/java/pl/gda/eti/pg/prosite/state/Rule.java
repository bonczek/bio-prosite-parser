package pl.gda.eti.pg.prosite.state;

public abstract class Rule {

    private boolean isFinal = false;

    public Rule() {
    }

    public Rule(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public abstract Rule next(char c);

    public boolean isFinal() {
        return isFinal;
    }

}
