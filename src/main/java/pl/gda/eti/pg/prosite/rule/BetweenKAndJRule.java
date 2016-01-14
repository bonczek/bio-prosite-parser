package pl.gda.eti.pg.prosite.rule;

public class BetweenKAndJRule extends SingleCharacterRule {

    private int minRepeatNumber;

    private int maxRepeatNumber;

    private int counter;

    private boolean conditionPassed = false;

    public BetweenKAndJRule(Character character, Rule nextRule, int minRepeatNumber, int maxRepeatNumber) {
        super(character, nextRule);
        this.minRepeatNumber = minRepeatNumber;
        this.maxRepeatNumber = maxRepeatNumber;
    }

    public Rule getRuleAfter() {
        if (conditionPassed) {
            return nextRule;
        } else {
            return null;
        }
    }

    @Override
    public Rule next(char c) {
        if (Character.compare(c, character) == 0) {
            minRepeatNumber--;
            maxRepeatNumber--;
            if (minRepeatNumber > 0) {
                return this;
            } else if (minRepeatNumber <= 0 && maxRepeatNumber > 0) {
                conditionPassed = true;
                return this;
            } else if (maxRepeatNumber == 0) {
                return nextRule;
            } else {
                conditionPassed = false;
            }
        }
        return null;
    }

}
