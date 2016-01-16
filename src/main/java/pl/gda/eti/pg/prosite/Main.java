package pl.gda.eti.pg.prosite;

import pl.gda.eti.pg.prosite.rule.Rule;
import pl.gda.eti.pg.prosite.rule.RuleBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
            switch (args.length) {
                case (0):
                    System.out.println("Nie podałeś żadnego parametru: pierwszy parametr do wzorzec, a drugi to sekwencja do przeszukania");
                    return;
                case (1):
                    System.out.println("Podałeś tylko pierwszy parametr, wymagane są dwa: pierwszy parametr do wzorzec, a drugi to sekwencja do przeszukania");
                    return;
                default:
                    System.out.println("Podałeś za dużo parametrów, wymagane są dwa: pierwszy parametr do wzorzec, a drugi to sekwencja do przeszukania");
                    return;
            }
        }
        String pattern = args[0];
        String sequence = args[1];

        PatternParser patternParser = new PatternParser();
        List<Rule> rulesChain = patternParser.parse(pattern);
        RuleBuilder ruleBuilder = new RuleBuilder(rulesChain);
        Set<SequenceFound> results = matchSingleSequence(sequence, ruleBuilder);
        results.stream().sorted((a, b) -> a.compareTo(b)).forEach(s -> System.out.println(s.report()));
    }

    /**
     * Sprawdza czy dane reguły występują w podanej sekwencji.
     * Przechodząc po kolejnych znakach w sekwencji tworzony jest nowy iterator wzorca,
     * który istnieje do czasu, gdy wzorzec jest spełniony rozpoczynając od rozpoczętego indeksu.
     *
     * @param sequence   sekwencja do przeszukania
     * @param rulesBuilder łańcuch kolejnych reguł, które sekwencja musi spełnić
     */
    private static Set<SequenceFound> matchSingleSequence(String sequence, RuleBuilder rulesBuilder) {
        Set<SequenceFound> results = new HashSet<>();
        List<PatternIterator> patternIterators = new ArrayList<>();
        for (int i = 0; i < sequence.length(); i++) {
            patternIterators.add(new PatternIterator(i, rulesBuilder.getCopiedRulesChain(0)));
            checkIteratorsAtPosition(sequence.charAt(i), patternIterators, rulesBuilder, results);
        }
        return results;
    }

    /**
     * Przechodzi po kolekcji iteratorów wzorca za pomocą iteratora.
     * Sprawdza czy podany znak w sekwencji zgadza się z istniejącymi wzorcami.
     * Jeśli się nie zgadza, to iterator wzorca jest usuwany.
     * Jeśli, któryś ze wzorców pomyślnie zakończył przeszukiwania to wypisuje na konsolę rezultat.
     *
     * @param seqCharacter kolejny znak przetwarzanej sekwencji
     * @param it           iterator do kolekcji przejść wzorców.
     */
    private static void checkIteratorsAtPosition(char seqCharacter, List<PatternIterator> patternIterators, RuleBuilder ruleBuilder, Set<SequenceFound> results) {
        Iterator<PatternIterator> it = patternIterators.iterator();
        List<PatternIterator> toAdd = new ArrayList<>();
        while (it.hasNext()) {
            PatternIterator patternIterator = it.next();
            if (!patternIterator.patternMatched(seqCharacter)) {
                it.remove();
            } else {
                if (patternIterator.getSecondRule() != null) {
                    int ruleIndex = patternIterator.getSecondRule().getIndex();
                    toAdd.add(new PatternIterator(patternIterator, ruleBuilder.getCopiedRulesChain(ruleIndex)));
                }
                if (patternIterator.finished()) {
                    //System.out.println(patternIterator.finishedReport());
                    results.add(patternIterator.getSequenceFound());
                }
            }
        }
        if (!toAdd.isEmpty()) {
            toAdd.forEach(patternIterators::add);
        }

    }
}
