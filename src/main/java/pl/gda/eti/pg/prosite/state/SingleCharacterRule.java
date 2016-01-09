package pl.gda.eti.pg.prosite.state;

public class SingleCharacterRule extends Rule {

    protected final Character character;

    protected final Rule nextRule;

    public SingleCharacterRule(Character character, Rule nextRule) {
        this.character = character;
        this.nextRule = nextRule;
    }

    @Override
    public Rule next(char c) {
        if (Character.compare(c, character) == 0) {
            return nextRule;
        } else {
            return null;
        }
    }
}
