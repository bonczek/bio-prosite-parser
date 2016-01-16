package pl.gda.eti.pg.prosite.rule;

public class ExactlyKTimesRule extends SingleCharacterRule {

    /**
     * Liczba powtórzeń podanego znaku.
     */
    private int repeatNumber;

    private int counter = 0;

    public ExactlyKTimesRule(Character character, Rule nextRule, int repeatNumer, int index) {
        super(character, nextRule, index);
        this.repeatNumber = repeatNumer;
    }

    public ExactlyKTimesRule(ExactlyKTimesRule rule, Rule nextRule) {
        super(rule.character, nextRule, rule.index);
        this.repeatNumber = rule.repeatNumber;
    }

    /**
     * Reguła sprawdza, czy podany znak występuje określoną liczbę razy.
     * Jeśli warunek jeszcze nie jest spełniony to reguła zwraca samą siebie ze zmniejszonym licznikiem wystąpień.
     * Po wystąpieniu odpowiednią liczbę razy zwraca kolejną regułę.
     *
     * Jeśli szukany znak oznaczony jest jako 'x' każdy znak jest przepuszczany, a reguła po prostu służy do określenia liczby wystąpień różnych znaków.
     *
     * @param c pojedynczy znak, który zostanie sprawdzony przez regułę.
     * @return kolejną regułę, samą siebie lub null
     */
    @Override
    public Rule next(char c) {
        if (Character.compare(c, character) == 0 || Character.compare('x', character) == 0) {
            counter++;
            if (counter < repeatNumber) {
                return this;
            } else {
                return nextRule;
            }
        }
        return null;
    }
}
