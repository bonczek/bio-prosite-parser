package pl.gda.eti.pg.prosite.rule;

public class ExactlyKTimesRule extends SingleCharacterRule {

    /**
     * Liczba powtórzeń podanego znaku.
     */
    private int repeatNumber;

    public ExactlyKTimesRule(Character character, Rule nextRule, int repeatNumer) {
        super(character, nextRule);
        this.repeatNumber = repeatNumer;
    }

    /**
     * Reguła sprawdza, czy podany znak występuje określoną liczbę razy.
     * Jeśli warunek jeszcze nie jest spełniony to reguła zwraca samą siebie ze zmniejszonym licznikiem wystąpień.
     * Po wystąpieniu odpowiednią liczbę razy zwraca kolejną regułę.
     *
     * @param c pojedynczy znak, który zostanie sprawdzony przez regułę.
     * @return kolejną regułę, samą siebie lub null
     */
    @Override
    public Rule next(char c) {
        if (Character.compare(c, character) == 0) {
            repeatNumber--;
            if (repeatNumber > 0) {
                return this;
            } else {
                return nextRule;
            }
        }
        return null;
    }
}
