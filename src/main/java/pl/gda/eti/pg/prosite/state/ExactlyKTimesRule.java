package pl.gda.eti.pg.prosite.state;

public class ExactlyKTimesRule extends SingleCharacterRule {

    private int counter;

    public ExactlyKTimesRule(Character character, Rule nextRule, int repeatNumer) {
        super(character, nextRule);
        this.counter = repeatNumer;
    }

    @Override
    public Rule next(char c) {
        if (Character.compare(c, character) == 0) {
            counter--;
            if (counter > 0) {
                return this;
            } else {
                return nextRule;
            }
        }
        return null;
    }
}
