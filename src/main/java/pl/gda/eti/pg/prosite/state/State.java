package pl.gda.eti.pg.prosite.state;

public abstract class State {

    private boolean isFinal = false;

    public State() {
    }

    public State(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public abstract State next(char c);

    public boolean isFinal() {
        return isFinal;
    }

}
