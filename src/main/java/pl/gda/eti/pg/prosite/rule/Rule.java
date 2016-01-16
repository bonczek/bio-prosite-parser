package pl.gda.eti.pg.prosite.rule;

/**
 * Łańcuch reguł wzorca Prosite, który ma być spełniony w wyszukiwanym ciągu znaków.
 */
public abstract class Rule {

    /**
     * Indeks reguły w schemacie - pomocny przy tworzeniu nowego łańcucha reguł dla PatternIterator
     */
    protected final int index;
    private boolean isFinal = false;

    public Rule(int index) {
        this.index = index;
    }

    public Rule(boolean isFinal, int index) {
        this.isFinal = isFinal;
        this.index = index;
    }

    public int getIndex() {
        return index;
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
