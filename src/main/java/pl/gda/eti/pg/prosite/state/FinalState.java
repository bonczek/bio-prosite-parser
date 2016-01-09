package pl.gda.eti.pg.prosite.state;

/**
 * Created by adam on 09.01.16.
 */
public class FinalState extends State {

    public FinalState() {
        super(true);
    }

    @Override
    public State next(char c) {
        return null;
    }
}
