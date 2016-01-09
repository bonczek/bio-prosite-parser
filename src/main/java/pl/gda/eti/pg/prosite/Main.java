package pl.gda.eti.pg.prosite;

import pl.gda.eti.pg.prosite.rule.Rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String pattern = "[ABC]-[DEF]-[GHI]";
        PatternParser patternParser = new PatternParser();

        Rule patternStates = patternParser.parse(pattern);

        String exampleString = "DSAADOADI";

        List<PrositeIterator> iterators = new ArrayList<>();

        for (int i = 0; i < exampleString.length(); i++) {
            PrositeIterator iterator = new PrositeIterator(i, patternStates);
            iterators.add(iterator);

            Iterator<PrositeIterator> it = iterators.iterator();
            while (it.hasNext()) {

                PrositeIterator prositeSeq = it.next();
                if (!prositeSeq.patternMatched(exampleString.charAt(i))) {
                    it.remove();
                } else {
                    if (prositeSeq.getRule().isFinal()) {
                        System.out.println(String.format("Znaleziony wzorzec: %s indeks: %d", prositeSeq.getMatched(), prositeSeq.getIndex()));
                    }
                }


            }

        }

    }
}
