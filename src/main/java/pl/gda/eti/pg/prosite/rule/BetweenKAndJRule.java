package pl.gda.eti.pg.prosite.rule;

public class BetweenKAndJRule extends SingleCharacterRule {

    private int minRepeatNumber;

    private int maxRepeatNumber;

    private int counter = 0;

    private boolean conditionPassed = false;

    public BetweenKAndJRule(Character character, Rule nextRule, int minRepeatNumber, int maxRepeatNumber, int index) {
        super(character, nextRule, index);
        this.minRepeatNumber = minRepeatNumber;
        this.maxRepeatNumber = maxRepeatNumber;
    }

    public BetweenKAndJRule(BetweenKAndJRule rule, Rule nextRule) {
        super(rule.character, nextRule, rule.index);
        this.minRepeatNumber = rule.minRepeatNumber;
        this.maxRepeatNumber = rule.maxRepeatNumber;
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
        if (Character.compare(c, character) == 0 || Character.compare('x', character) == 0) {
            counter++;
            if (counter < minRepeatNumber) {
                return this;
            } else if (counter >= minRepeatNumber && counter < maxRepeatNumber) {
                conditionPassed = true;
                return this;
            } else if (counter == maxRepeatNumber) {
                return nextRule;
            } else {
                conditionPassed = false;
            }
        }
        return null;
    }

}
