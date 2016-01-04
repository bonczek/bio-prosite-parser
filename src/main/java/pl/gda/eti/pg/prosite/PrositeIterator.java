package pl.gda.eti.pg.prosite;

public class PrositeIterator {

    private final int index;
    private State state;
    private StringBuilder matched;

    public PrositeIterator(int index, State state) {
        this.index = index;
        this.state = state;
        this.matched = new StringBuilder();
    }

    public int getIndex() {
        return index;
    }

    public State getState() {
        return state;
    }

    public boolean patternMatched(Character character) {
        State tmp = state.next(character);
        if (tmp != null) {
            this.state = tmp;
            matched.append(character);
            return true;
        } else {
            return false;
        }
    }

    public String getMatched() {
        return matched.toString();
    }
}
