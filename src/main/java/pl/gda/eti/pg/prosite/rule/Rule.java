package pl.gda.eti.pg.prosite.rule;

/**
 * Łańcuch reguł wzorca Prosite, który ma być spełniony w wyszukiwanym ciągu znaków.
 */
public abstract class Rule {

    private boolean isFinal = false;

    public Rule() {
    }

    public Rule(boolean isFinal) {
        this.isFinal = isFinal;
    }

    /**
     * Zwraca kolejną regułę jeśli taka istnieje oraz jeśli podany znak spełnia daną regułę.
     *
     * @param c pojedynczy znak, który zostanie sprawdzony przez regułę.
     * @return kolejna w kolejności reguła lub null
     */
    public abstract Rule next(char c);

    public boolean isFinal() {
        return isFinal;
    }

}
