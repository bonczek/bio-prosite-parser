package pl.gda.eti.pg.prosite.state;

public class AnythingState extends State {

    private final State nextState;

    public AnythingState(State nextState) {
        this.nextState = nextState;
    }

    @Override
    public State next(char c) {
        return nextState;
    }
}
