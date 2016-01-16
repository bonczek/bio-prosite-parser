package pl.gda.eti.pg.prosite.pattern;

/**
 * Znaleziony łańcuch znaków spełniający schemat podanych reguł.
 */
public class PatternFound implements Comparable {
    private final Integer beginIndex;
    private final Integer endIndex;
    private final String matchedSequence;

    public PatternFound(int beginIndex, int endIndex, String matchedSequence) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.matchedSequence = matchedSequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatternFound that = (PatternFound) o;

        if (!beginIndex.equals(that.beginIndex)) return false;
        if (!endIndex.equals(that.endIndex)) return false;
        if (!matchedSequence.equals(that.matchedSequence)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = beginIndex;
        result = 31 * result + endIndex;
        result = 31 * result + matchedSequence.hashCode();
        return result;
    }

    public String report() {
        return String.format("Indeks: (%d,%d) - znaleziony wzorzec: %s", beginIndex, endIndex, matchedSequence);
    }

    @Override
    public int compareTo(Object o) {
        PatternFound other = (PatternFound) o;
        int beginIndexDiff = this.beginIndex.compareTo(other.beginIndex);
        if (beginIndexDiff != 0) {
            return beginIndexDiff;
        } else {
            return this.endIndex.compareTo(other.endIndex);
        }
    }
}
