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

    /**
     * Konstruktor slużący do kopiowania reguł
     */
    public BetweenKAndJRule(BetweenKAndJRule rule, Rule nextRule) {
        super(rule.character, nextRule, rule.index);
        this.minRepeatNumber = rule.minRepeatNumber;
        this.maxRepeatNumber = rule.maxRepeatNumber;
    }

    /**
     * Jeżeli warunek jest już spełniony np. e(2,4), a e już wystąpiło 2 razy, to zwraca następną regułę.
     *
     * @return następna w kolejności reguła.
     */
    public Rule getRuleAfter() {
        if (conditionPassed) {
            return nextRule;
        } else {
            return null;
        }
    }

    /**
     * Reguła sprawdza, czy podany znak występuje conajmniej k, ale nie więcej niż j razy pod rząd.
     * Jeśli warunek jeszcze nie jest spełniony to reguła zwraca samą siebie ze zmniejszonym licznikiem wystąpień.
     * Po wystąpieniu minimalną liczbę razy zwraca dalej samą siebie, ale umożliwia pobranie kolejnej reguły poprzez metode getRuleAfter()
     * Po dotarciu do limitu maksimum zwraca kolejną regułę w kolejności.
     * <p>
     * Jeśli szukany znak oznaczony jest jako 'x' każdy znak jest przepuszczany, a reguła po prostu służy do określenia liczby wystąpień różnych znaków.
     *
     * @param c pojedynczy znak, który zostanie sprawdzony przez regułę.
     * @return kolejną regułę, samą siebie lub null
     */
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
