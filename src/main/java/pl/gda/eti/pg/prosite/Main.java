package pl.gda.eti.pg.prosite;

import pl.gda.eti.pg.prosite.rule.Rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String pattern = "[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]";
        PatternParser patternParser = new PatternParser();

        Rule rulesChain = patternParser.parse(pattern);

        String lectureString1 = "SRSLKMRGQAFVIFKEVSSAT";
        String lectureString2 = "KLTGRPRGVAFVRYNKREEAQ";
        String lectureString3 = "VGCSVHKGFAFVQYVNERNAR";

        matchSingleSequence(lectureString1, rulesChain);
        matchSingleSequence(lectureString2, rulesChain);
        matchSingleSequence(lectureString3, rulesChain);
    }

    /**
     * Sprawdza czy dane reguły występują w podanej sekwencji.
     * Przechodząc po kolejnych znakach w sekwencji tworzony jest nowy iterator wzorca,
     * który istnieje do czasu, gdy wzorzec jest spełniony rozpoczynając od rozpoczętego indeksu.
     *
     * @param sequence   sekwencja do przeszukania
     * @param rulesChain łańcuch kolejnych reguł, które sekwencja musi spełnić
     */
    private static void matchSingleSequence(String sequence, Rule rulesChain) {
        List<PatternIterator> patternIterators = new ArrayList<>();
        for (int i = 0; i < sequence.length(); i++) {
            patternIterators.add(new PatternIterator(i, rulesChain));
            checkIteratorsAtPosition(sequence.charAt(i), patternIterators.iterator());
        }
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
    private static void checkIteratorsAtPosition(char seqCharacter, Iterator<PatternIterator> it) {
        while (it.hasNext()) {
            PatternIterator patternIterator = it.next();
            if (!patternIterator.patternMatched(seqCharacter)) {
                it.remove();
            } else {
                if (patternIterator.finished()) {
                    System.out.println(patternIterator.finishedReport());
                }
            }
        }
    }
}
