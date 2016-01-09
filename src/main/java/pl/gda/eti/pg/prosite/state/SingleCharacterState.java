package pl.gda.eti.pg.prosite.state;

public class SingleCharacterState extends State {

    protected final Character character;

    protected final State nextState;

    public SingleCharacterState(Character character, State nextState) {
        this.character = character;
        this.nextState = nextState;
    }

    @Override
    public State next(char c) {
        if (Character.compare(c, character) == 0) {
            return nextState;
        } else {
            return null;
        }
    }
}
