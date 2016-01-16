package pl.gda.eti.pg.prosite.pattern;

import pl.gda.eti.pg.prosite.rule.RuleBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Wyszukuje łańcucha znaków w sekwencji pasującego do schematu.
 */
public class PatternFinder {

    private final RuleBuilder rulesBuilder;

    private final List<PatternIterator> patternIterators;

    private final String sequence;

    private final Set<PatternFound> results;

    public PatternFinder(RuleBuilder rulesBuilder, String sequence) {
        this.rulesBuilder = rulesBuilder;
        this.sequence = sequence;
        this.patternIterators = new ArrayList<>();
        this.results = new HashSet<>();
    }


    /**
     * Szuka łańcucha znaków spełniającego schemat reguł w sekwencji.
     *
     * @return krótkie raporty o wszystkich znalezionych fragmentach sekwencji.
     */
    public List<String> findPattern() {
        //Przechodząc po kolejnych znakach w sekwencji tworzony jest nowy iterator wzorca,
        for (int i = 0; i < sequence.length(); i++) {
            patternIterators.add(new PatternIterator(i, rulesBuilder.getCopiedRulesChain(0)));
            checkIteratorsAtPosition(sequence.charAt(i));
        }
        return results.stream().sorted(PatternFound::compareTo)
                .map(PatternFound::report).collect(Collectors.toList());
    }

    /**
     * Przechodzi po kolekcji iteratorów wzorca i sprawdza czy podany znak w sekwencji zgadza się z istniejącymi regułami.
     *
     * @param seqCharacter kolejny znak przetwarzanej sekwencji
     */
    private void checkIteratorsAtPosition(char seqCharacter) {
        Iterator<PatternIterator> it = patternIterators.iterator();
        List<PatternIterator> toAdd = new ArrayList<>();
        while (it.hasNext()) {
            PatternIterator patternIterator = it.next();
            //Jeśli litera nie pasuje do wzorca, to iterator wzorca jest usuwany.
            if (!patternIterator.patternMatched(seqCharacter)) {
                it.remove();
            } else {
                //Szczególny przypadek występuje, przy regule BetweenKAndJRule - może przez pewien czas posiadać dwa stany,
                //dlatego do takich przypadków jest tworzony nowy iterator, który sprawdzi drugą możliwą opcję w łańcuchu reguł.
                if (patternIterator.getSecondRule() != null) {
                    int ruleIndex = patternIterator.getSecondRule().getIndex();
                    toAdd.add(new PatternIterator(patternIterator, rulesBuilder.getCopiedRulesChain(ruleIndex)));
                }
                //Jeśli, któryś ze wzorców pomyślnie zakończył przeszukiwania to znaleziona sekwencja jest zapisywana w zbiorze results.
                if (patternIterator.finished()) {
                    results.add(patternIterator.getSequenceFound());
                }
            }
        }
        //Jeśli są potrzebne nowe iteratory dla szczególnego przypadku to dodaje je do listy
        if (!toAdd.isEmpty()) {
            toAdd.forEach(patternIterators::add);
        }
    }
}
