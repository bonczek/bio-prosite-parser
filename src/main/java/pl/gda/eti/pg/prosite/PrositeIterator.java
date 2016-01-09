package pl.gda.eti.pg.prosite;

import pl.gda.eti.pg.prosite.state.State;

/**
 * Iterator, który zaczynając od określonego miejsca w łańcuchu znaków sprawdza zgodność kolejnych znaków ze schematem.
 */
public class PrositeIterator {

    private final int index;
    private State state;
    private StringBuilder matched;

    /**
     * Stworzenie nowego iteratora, ktory od okreslonego miejsca rozpocznie sprawdzanie schematu.
     *
     * @param index indeks znaku w szukanym ciagu od ktorego iterator zacznie sprawdzac reguły
     * @param state szukane reguły, które sprawdzane są w kolejnych literach
     */
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

    /**
     * Sprawdza czy dla podanego kolejnego znaku określony schemat jest prawdziwy.
     *
     * @param character kolejny znak w przeszukiwanym tekście
     * @return rezulat dopasowania danego znaku
     */
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
