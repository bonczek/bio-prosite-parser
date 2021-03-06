package pl.gda.eti.pg.prosite.rule;

public class SingleCharacterRule extends Rule {

    /**
     * Pojedynczy znak wymagany przez tą regułę określony we wzorcu.
     */
    protected final Character character;

    protected final Rule nextRule;

    public SingleCharacterRule(Character character, Rule nextRule, int index) {
        super(index);
        this.character = character;
        this.nextRule = nextRule;
    }

    /**
     * Konstruktor slużący do kopiowania reguł
     */
    public SingleCharacterRule(SingleCharacterRule rule, Rule nextRule) {
        super(rule.index);
        this.character = rule.character;
        this.nextRule = nextRule;
    }

    /**
     * Reguła sprawdza, czy znak w tym miejscu schematu jest identyczny z podanym na wejściu.
     *
     * @param c pojedynczy znak, który zostanie sprawdzony przez regułę.
     * @return
     */
    @Override
    public Rule next(char c) {
        if (Character.compare(c, character) == 0) {
            return nextRule;
        } else {
            return null;
        }
    }
}
