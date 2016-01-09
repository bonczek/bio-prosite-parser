package pl.gda.eti.pg.prosite.state;

public class ExactlyKTimesState extends SingleCharacterState {

    private int counter;

    public ExactlyKTimesState(Character character, State nextState, int repeatNumer) {
        super(character, nextState);
        this.counter = repeatNumer;
    }

    @Override
    public State next(char c) {
        if (Character.compare(c, character) == 0) {
            counter--;
            if (counter > 0) {
                return this;
            } else {
                return nextState;
            }
        }
        return null;
    }
}
