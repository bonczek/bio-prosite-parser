package pl.gda.eti.pg.prosite.rule;

/**
 * Ostatnia reguła w schemacie, do której dotarcie oznacza, że schemat jest poprawny w danym ciągu znaków.
 */
public class FinalRule extends Rule {

    public FinalRule() {
        super(true);
    }

    @Override
    public Rule next(char c) {
        return null;
    }
}
