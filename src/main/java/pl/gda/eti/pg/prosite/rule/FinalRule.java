package pl.gda.eti.pg.prosite.rule;

/**
 * Ostatnia reguła w schemacie, do której dotarcie oznacza, że schemat jest poprawny w danym ciągu znaków.
 */
public class FinalRule extends Rule {

    public FinalRule(int index) {
        super(true, index);
    }

    /**
     * Konstruktor slużący do kopiowania reguł
     */
    public FinalRule(FinalRule rule) {
        super(true, rule.index);
    }

    @Override
    public Rule next(char c) {
        return null;
    }
}
